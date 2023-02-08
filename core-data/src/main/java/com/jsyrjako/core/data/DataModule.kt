package com.jsyrjako.core.data

import com.jsyrjako.core.data.datasource.category.CategoryDataSource
import com.jsyrjako.core.data.datasource.reminder.ReminderDataSource
import com.jsyrjako.core.data.datasource.reminder.ReminderDataSourceImpl
import com.jsyrjako.core.data.repository.CategoryRepositoryImpl
import com.jsyrjako.core.data.repository.ReminderRepositoryImpl
import com.jsyrjako.core.domain.repository.CategoryRepository
import com.jsyrjako.core.domain.repository.ReminderRepository
import com.mobicomp.core.data.datasource.category.CategoryDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindReminderDataSource(
        reminderDataSource: ReminderDataSourceImpl
    ): ReminderDataSource

    @Singleton
    @Binds
    fun bindReminderRepository(
        reminderRepository: ReminderRepositoryImpl
    ): ReminderRepository

    @Singleton
    @Binds
    fun bindCategoryDataSource(
        categoryDataSource: CategoryDataSourceImpl
    ): CategoryDataSource

    @Singleton
    @Binds
    fun bindCategoryRepository(
        categoryRepository: CategoryRepositoryImpl
    ): CategoryRepository
}