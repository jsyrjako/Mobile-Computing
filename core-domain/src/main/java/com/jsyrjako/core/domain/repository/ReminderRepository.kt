package com.jsyrjako.core.domain.repository

import com.jsyrjako.core.domain.entity.Reminder
import com.jsyrjako.core.domain.entity.Category
import kotlinx.coroutines.flow.Flow


interface ReminderRepository {
    suspend fun addReminder(reminder: Reminder)
    suspend fun loadRemindersFor(category: Category): Flow<List<Reminder>>
}