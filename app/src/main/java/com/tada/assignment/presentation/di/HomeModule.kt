package com.tada.assignment.presentation.di

import com.tada.assignment.data.repository.RecipeRepositoryImpl
import com.tada.assignment.domain.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {
    @ViewModelScoped
    @Binds
    abstract fun provideRecipeRepository(recipeRepository: RecipeRepositoryImpl): RecipeRepository
}