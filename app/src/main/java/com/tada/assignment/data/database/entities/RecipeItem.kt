package com.tada.assignment.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.tada.assignment.presentation.ui.model.RecipeModel

@Entity(tableName = "recipe")
class RecipeItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "name")
    @Expose
    val name: String?,

    @ColumnInfo(name = "instruction")
    @Expose
    val instruction: String?,

    @ColumnInfo(name = "type")
    @Expose
    val type: String?
) {
    fun convertToModelUI(): RecipeModel {
        return RecipeModel(this.id, this.name, this.instruction, this.type)
    }
}