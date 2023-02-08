package com.jsyrjako.core.database.dao

import androidx.room.*
import com.jsyrjako.core.database.entity.CategoryEntity
import com.jsyrjako.core.database.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate( reminder: ReminderEntity)

    @Query("SELECT * FROM reminders WHERE reminderId LIKE :reminderId")
    fun findOne(reminderId: Long): Flow<ReminderEntity>

    @Query("""SELECT * from categories JOIN reminders ON reminders.category_id = categories.categoryId WHERE categoryId LIKE :categoryId""")
    fun findRemindersByCategory(categoryId: Long): Flow<Map<CategoryEntity, List<ReminderEntity>>>

    @Delete
    suspend fun delete(reminder: ReminderEntity)
}