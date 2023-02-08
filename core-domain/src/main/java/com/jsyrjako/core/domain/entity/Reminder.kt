package com.jsyrjako.core.domain.entity

import java.time.LocalDateTime

data class Reminder(
    val reminderId: Long = 0,
    val title: String,
    val categoryId: Long,
    val dateNow: LocalDateTime,
    val setDate: String,
    val text: String
)
