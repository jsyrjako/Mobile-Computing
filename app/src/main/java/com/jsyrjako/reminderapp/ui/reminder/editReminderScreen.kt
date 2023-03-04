package com.jsyrjako.reminderapp.ui.reminder

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.jsyrjako.core.domain.entity.Reminder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun editReminderScreen(
    navController: NavController,
    viewModel: ReminderViewModel = hiltViewModel(),
) {
    // find the reminder to edit
    val reminder = viewModel.getReminder()
    val reminderId = reminder.reminderId
    val title = remember { mutableStateOf(reminder.title) }
    val reminder_time = remember { mutableStateOf(reminder.reminder_time) }
    val categoryId = remember { mutableStateOf(reminder.categoryId) }
    val text = remember { mutableStateOf(reminder.text) }
    val location_x = remember { mutableStateOf(reminder.location_x) }
    val location_y = remember { mutableStateOf(reminder.location_y) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {}
    )
    val context = LocalContext.current

    val latLng = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<LatLng>("location_data")
        ?.value

    Surface {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(text = "Edit Reminder")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(10.dp)

            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title.value,
                    onValueChange = { title.value = it },
                    label = { Text(text = "Title") }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // OutlinedTextField(
                //     modifier = Modifier.fillMaxWidth(),
                //     value = reminder_time.value,
                //     onValueChange = { reminder_time.value = it },
                //     label = { Text(text = "Reminder time") }
                // )

                editDateTimePicker(
                    modifier = Modifier.fillMaxWidth(),
                    reminder_time = reminder_time
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    value = text.value,
                    onValueChange = { text.value = it },
                    label = { Text(text = "Text") }
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (location_x.value == "0.0" && location_y.value == "0.0") {
                    OutlinedButton(
                        onClick = {
                            requestPermission(
                                context = context,
                                permission = Manifest.permission.ACCESS_FINE_LOCATION,
                                requestPermission = { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }
                            ).apply {
                                navController.navigate("Location")
                            }
                        },
                        modifier = Modifier.height(55.dp)
                    ) {
                        Text(text = "Reminder location")
                    }
                } else {
                    TextButton(
                        onClick = {
                            requestPermission(
                                context = context,
                                permission = Manifest.permission.ACCESS_FINE_LOCATION,
                                requestPermission = { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }
                            ).apply {
                                navController.navigate("Location")
                            }
                        },
                        modifier = Modifier.height(70.dp)
                    ) {
                        Text(text = "Reminder location \nLat: ${location_x.value}, \nLng ${location_y.value}")

                        if (latLng != null) {
                            location_x.value = latLng.latitude.toString()
                            location_y.value = latLng.longitude.toString()
                        }
                    }
                }

                Button(
                    onClick = {
                        viewModel.editReminder(
                            Reminder(
                                reminderId = reminderId,
                                title = title.value,
                                reminder_time = reminder_time.value,
                                categoryId = categoryId.value,
                                creation_time = LocalDateTime.now(),
                                text = text.value,
                                location_x = location_x.value,
                                location_y = location_y.value,
                                reminder_seen = false
                            )
                        )
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}
private fun requestPermission(
    context: Context,
    permission: String,
    requestPermission: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(
            context,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        requestPermission()
    }
}


 @Composable
fun editDateTimePicker(
    modifier: Modifier = Modifier,
    reminder_time: MutableState<String>
) {
     val calendar = Calendar.getInstance()
     val context = LocalContext.current

     val date = remember { mutableStateOf("") }
     val time = remember { mutableStateOf("") }
     val dateAndTime = remember { mutableStateOf(reminder_time.value) }

     val year: Int
     val month: Int
     val day: Int
     val hour: Int
     val minute: Int

     println(reminder_time.value)

     if (reminder_time.value == "") {
         year = calendar[Calendar.YEAR]
         month = calendar[Calendar.MONTH]
         day = calendar[Calendar.DAY_OF_MONTH]
         hour = calendar[Calendar.HOUR_OF_DAY]
         minute = calendar[Calendar.MINUTE]
     } else {
         val date = reminder_time.value.split(" ")[0]
         val time = reminder_time.value.split(" ")[1]

         year = date.split("-")[0].toInt()
         month = date.split("-")[1].toInt() - 1
         day = date.split("-")[2].toInt()
         hour = time.split(":")[0].toInt()
         minute = time.split(":")[1].toInt()

     }


     val DatePickerDialog = DatePickerDialog(
         context,
          { _: DatePicker, year: Int, month: Int, day: Int ->
             date.value = "$year-${month+1}-$day"
         }, year, month, day
     )

     val TimePickerDialog = TimePickerDialog(
         context,
         { _: TimePicker, hour: Int, minute: Int ->
             time.value = "$hour:$minute"
         }, hour, minute, true
     )
     
     dateAndTime.value = "${date.value} ${time.value}"

     if (date.value.isNotEmpty() && time.value.isNotEmpty()){
         reminder_time.value = dateAndTime.value
         println("Set reminder_time.value to ${reminder_time.value}")
     }
     if (reminder_time.value != "") {
         dateAndTime.value = reminder_time.value
     }
     println("dateAndTime.value: ${dateAndTime.value}")
     println("reminder_time.value: ${reminder_time.value}")




     OutlinedTextField(
         modifier = modifier,
         value = dateAndTime.value,
         onValueChange = { dateAndTime.value = it },
         label = { Text(text = "Reminder time")},
         readOnly = true,
         trailingIcon = {
             Icon(
                 imageVector = Icons.Default.CalendarToday,
                 contentDescription = null,
                 modifier = Modifier.clickable {
                     TimePickerDialog.show()
                     DatePickerDialog.show()
                 }
             )
         }
     )
}
