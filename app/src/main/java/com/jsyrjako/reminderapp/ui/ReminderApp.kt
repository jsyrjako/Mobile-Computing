package com.jsyrjako.reminderapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jsyrjako.reminderapp.ReminderAppState
import com.jsyrjako.reminderapp.rememberReminderAppState
import com.jsyrjako.reminderapp.ui.home.Home
import com.jsyrjako.reminderapp.ui.login.LoginScreen
import com.jsyrjako.reminderapp.ui.mainscreen.MainScreen
import com.jsyrjako.reminderapp.ui.singUp.SingUpScreen


@Composable
fun ReminderApp (
    appState: ReminderAppState = rememberReminderAppState()
){
    NavHost(
        navController = appState.navController,
        startDestination = "mainScreen"
    ) {
        composable(route = "mainScreen") {
            MainScreen(modifier = Modifier.fillMaxSize(), navController = appState.navController)
        }
        composable(route = "login") {
            LoginScreen(modifier = Modifier.fillMaxSize(), navController = appState.navController)
        }
        composable(route = "SingUp") {
            SingUpScreen(modifier = Modifier.fillMaxSize(), navController = appState.navController)
        }
        composable(route = "home") {
            Home()
        }
    }
}

