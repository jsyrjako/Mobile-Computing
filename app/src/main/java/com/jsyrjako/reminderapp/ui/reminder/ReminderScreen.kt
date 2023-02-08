package com.jsyrjako.reminderapp.ui.reminder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import java.time.LocalDateTime
import com.jsyrjako.core.domain.entity.Reminder


@Composable
fun ReminderScreen(
    //onBackPress: () -> Unit,
    navController: NavController,
    viewModel: ReminderViewModel = hiltViewModel()
) {
    val title = remember { mutableStateOf("") }
    val setDate = remember { mutableStateOf("") }
    val text = remember { mutableStateOf("") }

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
                    label = { Text(text = "Title")},
                    shape = RoundedCornerShape(corner = CornerSize(50.dp))
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = setDate.value,
                        onValueChange = { setDate.value = it },
                        label = { Text(text = "Set Date") },
                        modifier = Modifier.fillMaxWidth(fraction = 0.5f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(300.dp),
                    value = text.value,
                    onValueChange = { text.value = it },
                    label = { Text(text = "Text")},
                    shape = RoundedCornerShape(corner = CornerSize(50.dp))
                )
                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        println(LocalDateTime.now())
                        viewModel.saveReminder(
                            Reminder(
                                title = title.value,
                                categoryId = 1,
                                dateNow = LocalDateTime.now(),
                                setDate = setDate.value,
                                text = text.value
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