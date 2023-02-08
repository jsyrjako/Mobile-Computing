package com.jsyrjako.core.data.datasource.reminder

import com.jsyrjako.core.domain.entity.Category
import com.jsyrjako.core.domain.entity.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderDataSource {
    suspend fun addRemainder(reminder: Reminder)
    suspend fun loadRemindersFor(category: Category): Flow<List<Reminder>>

}