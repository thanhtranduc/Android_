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
class AddOrEditRecipeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository
) : BaseViewModel() {
    var recipeModel: RecipeModel? = null
        private set

    private val _isOnSave = MutableLiveData<Boolean>()
    val isOnSave: LiveData<Boolean>
        get() = _isOnSave

    private val _isOnBack = MutableLiveData<Boolean>()
    val isOnBack: LiveData<Boolean>
        get() = _isOnBack

    init {
        recipeModel = savedStateHandle.get<RecipeModel>(RecipeDetailViewModel.RECIPE_DETAIL)
    }

    fun onSave(item: RecipeModel) {
        launchDataLoad {
            recipeRepository.saveRecipe(item.convertTo())
            _isOnBack.postValue(true)
        }
    }

    fun onBackPress() {
        _isOnBack.postValue(true)
    }
}