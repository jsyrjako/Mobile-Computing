package com.jsyrjako.core.domain.entity

import java.sql.Date
import java.time.LocalDateTime

data class Reminder(
    val reminderId: Long = 0,
    val title: String,
    val categoryId: Long,
    val creation_time: LocalDateTime,
    val reminder_time: String,
    val text: String,
    val location_x: String,
    val location_y: String,
    val creator_id: Long = 0,
    val reminder_seen: Boolean
)
