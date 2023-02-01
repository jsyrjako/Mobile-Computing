package com.jsyrjako.reminderapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Home(
    navController: NavController,
) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Text(text = "Here is the home screen")

        Spacer(modifier = Modifier.height(500.dp))

        Button(
            onClick = { navController.navigate("Reminder") },
            shape = CircleShape,
            modifier = Modifier.fillMaxWidth(),

            )
        {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }

    }


}