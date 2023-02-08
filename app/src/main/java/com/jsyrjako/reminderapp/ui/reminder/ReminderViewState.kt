package com.jsyrjako.reminderapp.ui.reminder

import com.jsyrjako.core.domain.entity.Reminder

sealed interface ReminderViewState {
    object Loading: ReminderViewState
    data class Success(
        val data: List<Reminder>
    ): ReminderViewState
}
