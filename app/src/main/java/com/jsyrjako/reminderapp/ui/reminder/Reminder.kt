package com.jsyrjako.reminderapp.ui.reminder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Reminder(
    onBackPress: () -> Unit,
    navController: NavController
) {
    Surface {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(
                    onClick = onBackPress
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
                    value = "",
                    onValueChange = { },
                    label = { Text(text = "Title")},
                    shape = RoundedCornerShape(corner = CornerSize(50.dp))
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    onValueChange = { },
                    label = { Text(text = "Date")},
                    shape = RoundedCornerShape(corner = CornerSize(50.dp))
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().size(300.dp),
                    value = "",
                    onValueChange = { },
                    label = { Text(text = "Text")},
                    shape = RoundedCornerShape(corner = CornerSize(50.dp))
                )
                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(corner = CornerSize(50.dp))
                )
                {
                    Text(text = "Save Reminder")
                }

            }
        }
    }
}