package com.tada.assignment.presentation.ui.model

import android.os.Parcelable
import com.tada.assignment.data.database.entities.RecipeItem
import kotlinx.parcelize.Parcelize

@Parcelize
class RecipeModel(val id: Int, val title: String?, val instruction: String?, val type: String?) : Parcelable {
    fun convertTo(): RecipeItem {
        return RecipeItem(this.id, this.title, this.instruction, this.type)
    }
}