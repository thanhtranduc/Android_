package com.tada.assignment.data.repository

import com.tada.assignment.data.database.dao.RecipeDao
import com.tada.assignment.data.database.entities.RecipeItem
import com.tada.assignment.domain.RecipeRepository
import com.tada.assignment.presentation.ui.model.RecipeModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val defaultDispatcher: CoroutineDispatcher
) : RecipeRepository {
    override suspend fun getAllRecipeData(): ArrayList<RecipeModel> =
        withContext(defaultDispatcher) {
            val items = ArrayList<RecipeModel>()
            recipeDao.getAllRecipe().forEach { recipe ->
                items.add(recipe.convertToModelUI())
            }
            return@withContext items
        }

    override suspend fun saveRecipe(recipeItem: RecipeItem) {
        withContext(defaultDispatcher) {
            recipeDao.insertCategory(recipeItem)
        }
    }

    override suspend fun deleteRecipe(recipeItem: RecipeItem) {
        withContext(defaultDispatcher) {
            recipeDao.deleteRecipe(recipeItem)
        }
    }

    override suspend fun findById(id: Int?): RecipeModel =
        withContext(defaultDispatcher) {
            return@withContext recipeDao.findById(id).convertToModelUI()
        }

    override suspend fun getAllType(): List<String> =
        withContext(defaultDispatcher){
            return@withContext recipeDao.getAllType()
        }
}