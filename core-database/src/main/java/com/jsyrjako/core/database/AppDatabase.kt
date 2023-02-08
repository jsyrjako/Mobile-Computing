package com.jsyrjako.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jsyrjako.core.database.dao.CategoryDao
import com.jsyrjako.core.database.dao.ReminderDao
import com.jsyrjako.core.database.entity.CategoryEntity
import com.jsyrjako.core.database.entity.ReminderEntity
import com.jsyrjako.core.database.utils.LocalDateTimeConverter

@Database(
    entities = [ReminderEntity::class, CategoryEntity::class],
    version = 1
)

@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ReminderDao(): ReminderDao
    abstract fun categoryDao(): CategoryDao
}