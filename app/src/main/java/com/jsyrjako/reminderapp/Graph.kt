package com.jsyrjako.reminderapp

import android.content.Context
import com.jsyrjako.core.domain.repository.ReminderRepository

object Graph {
    lateinit var appContext: Context

    fun provide(context: Context) {
        appContext = context
    }
}
