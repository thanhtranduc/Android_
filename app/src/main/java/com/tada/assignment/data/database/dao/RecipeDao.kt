package com.tada.assignment.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tada.assignment.data.database.entities.RecipeItem

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipe(): List<RecipeItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(recipeItem: RecipeItem)

    @Delete
    fun deleteRecipe(recipeItem: RecipeItem)

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun findById(id: Int?): RecipeItem

    @Query("select recipe.type from recipe")
    fun getAllType(): List<String>
}