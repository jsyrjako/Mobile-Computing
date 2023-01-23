package com.jsyrjako.reminderapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jsyrjako.reminderapp.ReminderAppState
import com.jsyrjako.reminderapp.rememberReminderAppState
import com.jsyrjako.reminderapp.ui.login.LoginScreen

@Composable
fun ReminderApp (
    appState: ReminderAppState = rememberReminderAppState()
){
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        composable(route = "login") {
            LoginScreen(modifier = Modifier.fillMaxSize())
        }
    }
}

