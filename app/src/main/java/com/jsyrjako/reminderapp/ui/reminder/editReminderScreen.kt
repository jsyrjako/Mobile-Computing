package com.jsyrjako.reminderapp.ui.reminder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jsyrjako.core.domain.entity.Reminder
import java.time.LocalDateTime



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

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = reminder_time.value,
                    onValueChange = { reminder_time.value = it },
                    label = { Text(text = "Reminder time") }
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