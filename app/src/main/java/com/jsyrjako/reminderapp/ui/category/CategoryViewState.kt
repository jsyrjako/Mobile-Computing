package com.jsyrjako.reminderapp.ui.category


import com.jsyrjako.core.domain.entity.Category
import com.jsyrjako.core.domain.entity.Reminder
import com.jsyrjako.reminderapp.ui.reminder.ReminderViewModel


sealed interface CategoryViewState {
    object Loading : CategoryViewState
    data class Error(val throwable: Throwable) : CategoryViewState
    data class Success(
        val selectedCategory: Category?,
        val data: List<Category>
    ) : CategoryViewState
}