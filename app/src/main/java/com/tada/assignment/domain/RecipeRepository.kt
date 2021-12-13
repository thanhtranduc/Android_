package com.tada.assignment.domain

import com.tada.assignment.data.database.entities.RecipeItem
import com.tada.assignment.presentation.ui.model.RecipeModel

interface RecipeRepository {
    suspend fun getAllRecipeData(): ArrayList<RecipeModel>

    suspend fun saveRecipe(recipeItem: RecipeItem)

    suspend fun deleteRecipe(recipeItem: RecipeItem)

    suspend fun findById(id: Int?) : RecipeModel?

    suspend fun getAllType(): List<String>
}