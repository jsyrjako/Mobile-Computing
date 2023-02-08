package com.jsyrjako.core.data.repository

import com.jsyrjako.core.domain.entity.Category
import com.jsyrjako.core.domain.repository.CategoryRepository
import com.jsyrjako.core.data.datasource.category.CategoryDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dataSource: CategoryDataSource
) : CategoryRepository {
    override suspend fun addCategory(category: Category): Long = dataSource.addCategory(category)

    override suspend fun loadCategories(): Flow<List<Category>> {
         return dataSource.loadCategories()
    }

}