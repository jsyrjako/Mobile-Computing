package com.jsyrjako.core.database.entity

import androidx.room.*
import java.time.LocalDateTime

@Entity(
    tableName = "reminders",
    indices = [
        Index("reminderId", unique = true),
        Index("category_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["categoryId"],
            childColumns = ["category_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]

)
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val reminderId: Long = 0,
    val title: String,
    @ColumnInfo(name = "category_id")
    val categoryId: Long,
    val creation_time: LocalDateTime,
    val reminder_time: String,
    val text: String,
    val location_x: String,
    val location_y: String,
    val creator_id: Long = 0,
    val reminder_seen: Boolean
)
