package com.jsyrjako.core.data.repository

import com.jsyrjako.core.data.datasource.reminder.ReminderDataSource
import com.jsyrjako.core.domain.entity.Category
import com.jsyrjako.core.domain.entity.Reminder
import com.jsyrjako.core.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private lateinit var setReminder: Reminder

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDataSource: ReminderDataSource,

) : ReminderRepository {
    override suspend fun addReminder(reminder: Reminder) {
        reminderDataSource.addRemainder(reminder)
    }
    
    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDataSource.deleteReminder(reminder)
    }
    override suspend fun editReminder(reminder: Reminder) {
        reminderDataSource.editReminder(reminder)
    }

    override suspend fun setReminder(reminder: Reminder) {
        setReminder = reminder
    }

    override suspend fun getReminder(): Reminder {
        return setReminder
    }

    override suspend fun loadRemindersFor(category: Category): Flow<List<Reminder>> {
        return reminderDataSource.loadRemindersFor(category)
    }

    override suspend fun loadAllReminders(): List<Reminder> {
        return reminderDataSource.loadAllReminders()
    }
}