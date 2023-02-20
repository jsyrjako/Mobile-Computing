package com.jsyrjako.core.data.datasource.reminder

import com.jsyrjako.core.database.dao.ReminderDao
import com.jsyrjako.core.database.entity.ReminderEntity
import com.jsyrjako.core.domain.entity.Category
import com.jsyrjako.core.domain.entity.Reminder
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private lateinit var setReminder: Reminder

class ReminderDataSourceImpl @Inject constructor(
    private val reminderDao : ReminderDao,

) : ReminderDataSource {
    override suspend fun addRemainder(reminder: Reminder) {
        reminderDao.insertOrUpdate(reminder.toEntity())
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.delete(reminder.toEntity())
    }

    override suspend fun editReminder(reminder: Reminder) {
        reminderDao.insertOrUpdate(reminder.toEntity())
    }

    override suspend fun setReminder(reminder: Reminder) {
       setReminder = reminder
    }

    override suspend fun getReminder(): Reminder {
        return setReminder
    }

    override suspend fun loadRemindersFor(category: Category): Flow<List<Reminder>> {
        return reminderDao.findRemindersByCategory(category.categoryId).map { list ->
            list.map {
                it.fromEntity()
            }
        }
    }

    override suspend fun loadAllReminders(): List<Reminder> {
        return reminderDao.findAll().map {
            it.fromEntity()
        }
    }

    private fun Reminder.toEntity() = ReminderEntity(
        reminderId = this.reminderId,
        title = this.title,
        categoryId = this.categoryId,
        creation_time = this.creation_time,
        reminder_time = this.reminder_time,
        text = this.text,
        location_x = this.location_x,
        location_y = this.location_y,
        reminder_seen = this.reminder_seen
    )

    private fun ReminderEntity.fromEntity() = Reminder(
        reminderId = this.reminderId,
        title = this.title,
        categoryId = this.categoryId,
        creation_time = this.creation_time,
        reminder_time = this.reminder_time,
        text = this.text,
        location_x = this.location_x,
        location_y = this.location_y,
        reminder_seen = this.reminder_seen
    )
}