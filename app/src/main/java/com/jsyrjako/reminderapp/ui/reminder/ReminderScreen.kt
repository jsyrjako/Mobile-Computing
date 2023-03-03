package com.jsyrjako.reminderapp.ui.reminder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDateTime
import com.jsyrjako.core.domain.entity.Reminder
import java.sql.Date


@Composable
fun ReminderScreen(
    //onBackPress: () -> Unit,
    navController: NavController,
    viewModel: ReminderViewModel = hiltViewModel()
) {
    val title = remember { mutableStateOf("") }
    val reminder_time = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val text = remember { mutableStateOf("") }
    val location_x = remember { mutableStateOf("") }
    val location_y = remember { mutableStateOf("") }
    val creator_id = 1234324567890
    val reminder_seen = false

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
                Text(text = "Reminder")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)

            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title.value,
                    onValueChange = { title.value = it },
                    label = { Text(text = "Reminder title")},
                )

                Spacer(modifier = Modifier.height(10.dp))

                CategoryListDropdown(
                    viewModel = viewModel,
                    category = category
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = reminder_time.value,
                    onValueChange = { reminder_time.value = it },
                    label = { Text(text = "Reminder time")}
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    value = text.value,
                    onValueChange = { text.value = it },
                    label = { Text(text = "Text")},
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (latLng == null) {
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
                    Text(
                        text = "Lat: ${latLng.latitude}, \nLng: ${latLng.longitude}"
                    )
                    location_x.value = latLng.latitude.toString()
                    location_y.value = latLng.longitude.toString()
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        println(LocalDateTime.now())
                        viewModel.saveReminder(
                            Reminder(
                                title = title.value,
                                categoryId = getCategoryId(viewModel, category.value),
                                creation_time = LocalDateTime.now(),
                                reminder_time = reminder_time.value,
                                text = text.value,
                                location_x = location_x.value,
                                location_y = location_y.value,
                                creator_id = creator_id,
                                reminder_seen = reminder_seen
                            )
                        )
                        navController.popBackStack()
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(corner = CornerSize(50.dp))
                )
                {
                    Text(text = "Save Reminder")
                }

            }
        }
    }
}

private fun getCategoryId(viewModel: ReminderViewModel, categoryName: String): Long {
    return viewModel.categories.value.first { it.name.lowercase() == categoryName.lowercase() }.categoryId
}

@Composable
private fun CategoryListDropdown(
    viewModel: ReminderViewModel,
    category: MutableState<String>
) {
    val categoryState = viewModel.categories.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded) {
        Icons.Filled.ArrowDropUp // requires androidx.compose.material:material-icons-extended dependency
    } else {
        Icons.Filled.ArrowDropDown
    }

    Column {
        OutlinedTextField(
            value = category.value,
            onValueChange = { category.value = it},
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Category") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            categoryState.value.forEach { dropDownOption ->
                DropdownMenuItem(
                    onClick = {
                        category.value = dropDownOption.name
                        expanded = false
                    }
                ) {
                    Text(text = dropDownOption.name)
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

