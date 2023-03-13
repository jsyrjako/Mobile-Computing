package com.jsyrjako.reminderapp.ui.reminder

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.jsyrjako.reminderapp.ui.home.Home
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationBuilderWithBuilderAccessor
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.jsyrjako.core.domain.entity.Category
import com.jsyrjako.core.domain.entity.Reminder
import com.jsyrjako.core.domain.repository.CategoryRepository
import com.jsyrjako.core.domain.repository.ReminderRepository
import com.jsyrjako.reminderapp.Graph
import com.jsyrjako.reminderapp.R
import com.jsyrjako.reminderapp.ui.category.CategoryViewState
import com.jsyrjako.reminderapp.ui.util.NotificationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private lateinit var setReminder: Reminder

@HiltViewModel
class ReminderViewModel @Inject constructor (
    private val reminderRepository: ReminderRepository,
    private val categoryRepository: CategoryRepository,
    ):ViewModel() {

    private val _reminderViewState = MutableStateFlow<ReminderViewState>(ReminderViewState.Loading)
    val uiState: StateFlow<ReminderViewState> = _reminderViewState

    private val _categoryList: MutableStateFlow<List<Category>> = MutableStateFlow(mutableListOf())
    val categories: StateFlow<List<Category>> = _categoryList

    private val _categoryViewState = MutableStateFlow<CategoryViewState>(CategoryViewState.Loading)
    val categoryState: StateFlow<CategoryViewState> = _categoryViewState

    private val _selectedCategory = MutableStateFlow<Category?>(null)

    private val _currentLocation = MutableStateFlow<LatLng?>(null)

    fun saveReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderRepository.addReminder(reminder)
            notifyUserOfReminder(reminder)
            println(reminder.reminder_time)
            if (reminder.reminder_time != "") {
                setOneTimeNotification(reminder)
            }
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderRepository.deleteReminder(reminder)
        }
    }


    fun editReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderRepository.editReminder(reminder)
            setOneTimeNotification(reminder)
        }
    }

    fun setReminder(reminder: Reminder) {
        setReminder = reminder
    }

    fun getReminder(): Reminder {
        return setReminder
    }

    fun onCategorySelected(category: Category) {
        _selectedCategory.value = category
    }

    fun notify(reminder: Reminder) {
        createSuccessNotification(reminder)
    }

    private fun notifyUserOfReminder(reminder: Reminder) {
        val notificationId = 10
        val builder = NotificationCompat.Builder(
            Graph.appContext,
            "channel_id"
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New reminder made")
            .setContentText("You have ${reminder.title} on ${reminder.reminder_time}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(Graph.appContext)) {
            if (ActivityCompat.checkSelfPermission(
                    Graph.appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(notificationId, builder.build())
        }
    }

    private fun setOneTimeNotification(reminder: Reminder) {

        try {
            val todayTime = Calendar.getInstance()
            // calculate the time when the notification should be shown
            // reminder_time.value is saved as "yyyy-MM-dd HH:mm"
            val reminderTime = reminder.reminder_time.split(" ")
            val reminderDate = reminderTime[0].split("-")
            val reminderTimeOnly = reminderTime[1].split(":")
            val year = reminderDate[0].toInt()
            val month =
                reminderDate[1].toInt() - 1 // month starts from 0 in Calendar but in reminder it starts from 1
            val day = reminderDate[2].toInt()
            val hour = reminderTimeOnly[0].toInt()
            val minute = reminderTimeOnly[1].toInt()

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)

            println("calendar date: ${calendar.time}")

            println("Reminder time: ${calendar.timeInMillis}")
            println("Today time: ${todayTime.timeInMillis}")

            val time = calendar.timeInMillis - todayTime.timeInMillis
            val timeInSec = time / 1000


            println("Next notification ${reminder.title} will be shown in ${timeInSec} seconds")

            val workManager = WorkManager.getInstance(Graph.appContext)
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(time, TimeUnit.MILLISECONDS)
                .setConstraints(constraints)
                .build()

            workManager.enqueue(notificationWorker)

            workManager.getWorkInfoByIdLiveData(notificationWorker.id)
                .observeForever { workInfo ->
                    if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                        createSuccessNotification(reminder)
                    } else {
                        notifyUserOfReminder(reminder)
                    }
                }
        } catch (e: Exception) {
            createFailureNotification(reminder)
        }
    }

    private fun createNotificationChannel() {
        val name = "NotificationChannel"
        val descriptionText = "NotificationChannelDescription"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("channel_id", name, importance).apply {
            description = descriptionText
        }
        val notificationManager = Graph.appContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createSuccessNotification(reminder: Reminder) {
        val notificationId = 10
        val builder = NotificationCompat.Builder(
            Graph.appContext,
            "channel_id"
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("You have reminder ${reminder.title}")
            .setContentText("${reminder.text}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(Graph.appContext)) {
            if (ActivityCompat.checkSelfPermission(
                    Graph.appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            notify(notificationId, builder.build())
        }
    }

    private fun createFailureNotification(reminder: Reminder) {
        val notificationId = 10
        val builder = NotificationCompat.Builder(
            Graph.appContext,
            "channel_id"
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Notification failed")
            .setContentText("Notification for ${reminder.title} failed")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(Graph.appContext)) {
            if (ActivityCompat.checkSelfPermission(
                    Graph.appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            notify(notificationId, builder.build())
        }
    }

    // returns list of all reminders
    suspend fun loadAllReminders(): List<Reminder> {
        return reminderRepository.loadAllReminders()
    }

    fun loadReminders() {
        viewModelScope.launch {
            val reminders = reminderRepository.loadAllReminders()
            _reminderViewState.value = ReminderViewState.Success(reminders)
        }
    }

    fun loadRemindersFor(category: Category?) {
        if (category != null) {
            viewModelScope.launch {
                val reminders = reminderRepository.loadAllReminders()
                _reminderViewState.value =
                    ReminderViewState.Success(
                        reminders.filter {
                            it.categoryId == category.categoryId
                        }
                    )
            }
        }
    }

    private suspend fun loadCategories() {
        combine(
            categoryRepository.loadCategories()
                .onEach { categories ->
                    if (categories.isNotEmpty() && _selectedCategory.value == null) {
                        _selectedCategory.value = categories.first()
                    }
                },
            _selectedCategory
        ) { categories, selectedCategory ->
            _categoryViewState.value = CategoryViewState.Success(selectedCategory, categories)
            _categoryList.value = categories
        }
            .catch { error -> CategoryViewState.Error(error) }
            .launchIn(viewModelScope)
    }

    init {
        createNotificationChannel()

        viewModelScope.launch {
            loadCategories()
        }
    }

    // updates current location for geofence
    fun updateCurrentLocation(location: LatLng) {
        _currentLocation.value = location
    }
}



