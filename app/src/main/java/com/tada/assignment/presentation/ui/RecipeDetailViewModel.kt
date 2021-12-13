package com.tada.assignment.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.tada.assignment.domain.RecipeRepository
import com.tada.assignment.presentation.ui.model.RecipeModel
import com.tada.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository
) : BaseViewModel() {

    var recipeModel: RecipeModel? = null
        private set

    private val _isOnBack = MutableLiveData<Boolean>()
    val isOnBack: LiveData<Boolean>
        get() = _isOnBack

    private val _isOnEdit = MutableLiveData<Boolean>()
    val isOnEdit: LiveData<Boolean>
        get() = _isOnEdit

    private val _isOnUpdated = MutableLiveData<Boolean>()
    val isOnUpdated: LiveData<Boolean>
        get() = _isOnUpdated
    init {
        recipeModel = savedStateHandle.get<RecipeModel>(RECIPE_DETAIL)
    }

    companion object {
        const val RECIPE_DETAIL = "RECIPE_DETAIL"
    }

    fun onEdit(item: RecipeModel) {
        _isOnEdit.postValue(true)
    }

    fun onDelete(item: RecipeModel) {
        launchDataLoad {
            recipeRepository.deleteRecipe(item.convertTo())
            _isOnBack.postValue(true)
        }
    }

    fun getItemUpdated() {
        launchDataLoad {
            recipeModel = recipeRepository.findById(recipeModel?.id)
            _isOnUpdated.postValue(true)
        }
    }

    fun onBackPress() {
        _isOnBack.postValue(true)
    }
}