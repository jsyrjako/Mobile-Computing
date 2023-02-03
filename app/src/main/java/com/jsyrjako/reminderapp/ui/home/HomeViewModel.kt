package com.jsyrjako.reminderapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsyrjako.reminderapp.data.entity.Category
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())
    private val _selectedCategory = MutableStateFlow<Category?>(null)

    val state: StateFlow<HomeViewState>
        get() = _state

    fun onCategorySelected(category: Category) {
        _selectedCategory.value = category
    }

    init {
        val categories = MutableStateFlow<List<Category>>(
            mutableListOf(
                Category(1, "Family"),
                Category(2, "School"),
                Category(3, "Work"),
                Category(4, "Hobbies"),
                Category(5, "Entertainment"),
                Category(6, "House"),
                Category(7, "Food"),
                Category(8, "Health"),
                Category(9, "Investment"),
            )
        )
        
        viewModelScope.launch { 
            combine(
                categories.onEach { category ->
                    if (categories.value.isNotEmpty() && _selectedCategory.value == null) {
                        _selectedCategory.value = category[0]
                    }
                },
                _selectedCategory
            ) { categories, selectedCategory ->
                HomeViewState(
                    categories = categories,
                    selectedCategory = selectedCategory
                )
            }.collect { _state.value = it}
        }
    }
}

data class HomeViewState(
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null
)