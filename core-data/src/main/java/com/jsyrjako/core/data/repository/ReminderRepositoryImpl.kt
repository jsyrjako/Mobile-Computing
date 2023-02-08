package com.jsyrjako.core.data.repository

import com.jsyrjako.core.data.datasource.reminder.ReminderDataSource
import com.jsyrjako.core.domain.entity.Category
import com.jsyrjako.core.domain.entity.Reminder
import com.jsyrjako.core.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val paymentDataSource: ReminderDataSource
) : ReminderRepository {
    override suspend fun addReminder(reminder: Reminder) {
        paymentDataSource.addRemainder(reminder)
    }

    override suspend fun loadRemindersFor(category: Category): Flow<List<Reminder>> {
        return paymentDataSource.loadRemindersFor(category)
    }
}