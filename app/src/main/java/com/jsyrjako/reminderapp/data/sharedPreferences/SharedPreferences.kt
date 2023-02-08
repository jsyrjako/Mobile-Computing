package com.jsyrjako.reminderapp.data.sharedPreferences


class SharedPreferences() {
    val username = "matti"
    val name = "Matti Manninen"
    val password = "123456"
}

fun getUsername(): String {
    return SharedPreferences().username
}


fun getPassword(): String {
    return SharedPreferences().password
}

fun getName(): String {
    return SharedPreferences().name
}

