package com.jsyrjako.core.data.datasource.reminder

import com.jsyrjako.core.database.dao.ReminderDao
import com.jsyrjako.core.database.entity.ReminderEntity
import com.jsyrjako.core.domain.entity.Category
import com.jsyrjako.core.domain.entity.Reminder
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReminderDataSourceImpl @Inject constructor(
    private val reminderDao : ReminderDao
) : ReminderDataSource {
    override suspend fun addRemainder(reminder: Reminder) {
        reminderDao.insertOrUpdate(reminder.toEntity())
    }

    override suspend fun loadRemindersFor(category: Category): Flow<List<Reminder>> {
        return reminderDao.findRemindersByCategory(category.categoryId)
            .map { reminderCategoryPair ->
                reminderCategoryPair.values.flatMap { reminderList ->
                    reminderList.map {
                        it.fromEntity()
                    }
                }
            }
    }

    private fun Reminder.toEntity() = ReminderEntity(
        reminderId = this.reminderId,
        title = this.title,
        categoryId = this.categoryId,
        dateNow = this.dateNow,
        setDate = this.setDate,
        text = this.text
    )

    private fun ReminderEntity.fromEntity() = Reminder(
        reminderId = this.reminderId,
        title = this.title,
        categoryId = this.categoryId,
        dateNow = this.dateNow,
        setDate = this.setDate,
        text = this.text
    )

}