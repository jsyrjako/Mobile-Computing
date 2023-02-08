package com.mobicomp.core.data.datasource.category

import com.jsyrjako.core.data.datasource.category.CategoryDataSource
import com.jsyrjako.core.database.dao.CategoryDao
import com.jsyrjako.core.database.entity.CategoryEntity
import com.jsyrjako.core.domain.entity.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryDataSource {
    override suspend fun addCategory(category: Category): Long {
        return categoryDao.insertOrUpdate(category.toEntity())
    }

    override suspend fun loadCategories(): Flow<List<Category>> = flow {
        emit(
            categoryDao.findAll().map {
                it.fromEntity()
            }
        )
//        val data = fakeData
//        emit(data)
    }

    private fun Category.toEntity() = CategoryEntity(
        categoryId = this.categoryId,
        name = this.name
    )

    private fun CategoryEntity.fromEntity() = Category(
        categoryId = this.categoryId,
        name = this.name
    )

//    private val fakeData = listOf(
//        Category(name = "Faith"),
//        Category(name = "Education"),
//        Category(name = "Sports"),
//        Category(name = "Health")
//    )
}