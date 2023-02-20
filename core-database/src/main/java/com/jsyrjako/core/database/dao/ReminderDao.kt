package com.jsyrjako.core.database.dao

import androidx.room.*
import com.jsyrjako.core.database.entity.CategoryEntity
import com.jsyrjako.core.database.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate( reminder: ReminderEntity)

    @Query("SELECT * FROM reminders")
    suspend fun findAll(): List<ReminderEntity>

    @Query("SELECT * FROM reminders WHERE reminderId LIKE :reminderId")
    suspend fun findById(reminderId: Long): ReminderEntity

    @Query("SELECT * FROM reminders WHERE category_id LIKE :categoryId")
    fun findRemindersByCategory(categoryId: Long): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE category_id LIKE :categoryId")
    suspend fun findRemindersByCategorySync(categoryId: Long): List<ReminderEntity>

    @Delete
    suspend fun delete(reminder: ReminderEntity)
}