package com.jsyrjako.core.data.datasource.category

import com.jsyrjako.core.domain.entity.Category
import kotlinx.coroutines.flow.Flow

interface CategoryDataSource {
    suspend fun addCategory(category: Category): Long
    suspend fun loadCategories(): Flow<List<Category>>
}