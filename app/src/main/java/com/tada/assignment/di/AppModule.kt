package com.tada.assignment.di

import android.app.Application
import androidx.room.Room
import com.tada.assignment.data.database.RecipeDatabase
import com.tada.assignment.data.database.dao.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Application): RecipeDatabase {
        return Room.databaseBuilder(context, RecipeDatabase::class.java, "app_database").build()
    }

    @Provides
    @Singleton
    internal fun provideSingleThreadDispatcher(): CoroutineDispatcher {
        return Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    }

    @Provides
    @Named("DefaultDispatcher")
    internal fun provideDefaultCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Named("IoDispatcher")
    internal fun provideIoCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Named("MainDispatcher")
    internal fun provideMainCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Singleton
    internal fun provideDao(db: RecipeDatabase): RecipeDao {
        return db.recipeDao()
    }
}