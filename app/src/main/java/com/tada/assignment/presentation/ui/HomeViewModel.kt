package com.tada.assignment.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tada.assignment.data.database.entities.RecipeItem
import com.tada.assignment.domain.RecipeRepository
import com.tada.assignment.presentation.ui.model.RecipeModel
import com.tada.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(val recipeRepository: RecipeRepository) : BaseViewModel() {

    private val _recipeItems = MutableLiveData<ArrayList<RecipeModel>>()
    val recipeItems: ArrayList<RecipeModel>?
        get() = _recipeItems.value

    private val _isFetchAll = MutableLiveData<Boolean>()
    val isFetchAll: LiveData<Boolean>
        get() = _isFetchAll

    val isUpdated = MutableLiveData<Boolean>()
    var allType = arrayListOf<String>()

    private val _isGetTypeDone = MutableLiveData<Boolean>()
    val isGetTypeDone: LiveData<Boolean>
        get() = _isGetTypeDone

    init {
        initData()
    }

    fun getAllRecipeData() {
        launchDataLoad {
            _recipeItems.value?.clear()
            _recipeItems.value = recipeRepository.getAllRecipeData()
            _isFetchAll.postValue(true)
        }
    }

    fun getAllType() {
        allType.clear()
        launchDataLoad {
            allType.add("All Type")
            allType.addAll(recipeRepository.getAllType())
            _isGetTypeDone.postValue(true)
        }
    }

    private fun initData() {
        val data = "Rinse the rice.\n" +
                "Use the right ratio of water. Add 2 parts water and 1 part rice to a large pot. For slightly firmer rice, use 1 part liquid to 2/3 parts rice.\n" +
                "Bring the water to a boil. Once it's boiling, add a big pinch of salt.\n" +
                "Maintain a simmer. Reduce heat to low, cover the pot with a tight fitting lid, and maintain a gentle simmer.\n" +
                "Cook without peeking or stirring. Cook until the water is absorbed, about 18 minutes. Try not to peek until the end of the cooking time so the steam doesn't escape. Whatever you do, don't mix the rice while it's cooking — this will lead to gummy rice.\n" +
                "Let the rice rest covered. Turn off the heat and let the rice sit, covered, for 10 minutes. During this time, the rice will steam for extra fluffy results.\n" +
                "Fluff the rice with a fork."
        launchDataLoad {
            for (i in 1..10) {
                val item =
                    RecipeItem(
                        id = i,
                        name = " Recipe Chick Rice " + i,
                        instruction = data,
                        "type " + i
                    )
                recipeRepository.saveRecipe(item)
            }
        }
    }
}