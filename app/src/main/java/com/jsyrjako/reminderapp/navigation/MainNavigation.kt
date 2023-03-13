package com.jsyrjako.reminderapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jsyrjako.reminderapp.ui.home.Home
import com.jsyrjako.reminderapp.ui.login.LoginScreen
import com.jsyrjako.reminderapp.ui.mainscreen.MainScreen
import com.jsyrjako.reminderapp.ui.maps.ReminderLocation
import com.jsyrjako.reminderapp.ui.profile.Profile
import com.jsyrjako.reminderapp.ui.reminder.NearbyRemindersScreen
import com.jsyrjako.reminderapp.ui.reminder.ReminderScreen
import com.jsyrjako.reminderapp.ui.reminder.editReminderScreen
import com.jsyrjako.reminderapp.ui.singUp.SingUpScreen


@Composable
fun MainNavigation (){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "mainScreen"
    ) {
        composable(route = "mainScreen") {
            MainScreen(modifier = Modifier.fillMaxSize(), navController =navController)
        }
        composable(route = "login") {
            LoginScreen(modifier = Modifier.fillMaxSize(), navController = navController)
        }
        composable(route = "SignUp") {
            SingUpScreen(modifier = Modifier.fillMaxSize(), navController = navController)
        }
        composable(route = "home") {
            Home(navController = navController)
        }
        composable(route = "Reminder") {
            ReminderScreen(navController = navController)
        }
        composable(route = "ReminderEdit") {
            editReminderScreen(navController = navController)
        }
        composable(route = "Profile") {
            Profile(navController = navController)
        }
        composable(route = "Location") {
            ReminderLocation(navController = navController)
        }
        composable(route = "Nearby") {
            NearbyRemindersScreen(navController = navController)
        }
    }
}