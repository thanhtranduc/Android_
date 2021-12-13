package com.tada.assignment.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tada.assignment.databinding.AddEditRecipeFragmentBinding
import com.tada.assignment.presentation.ui.model.RecipeModel
import com.tada.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class AddOrEditRecipeFragment : BaseFragment<AddEditRecipeFragmentBinding>() {
    private val viewModel: AddOrEditRecipeViewModel by viewModels()
    private val activityModel: HomeViewModel by activityViewModels()
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): AddEditRecipeFragmentBinding =
        AddEditRecipeFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isOnBack.observe(viewLifecycleOwner) {
            if (it) {
                activityModel.isUpdated.postValue(true)
                activity?.onBackPressed()
            }
        }
        binding.viewModel = viewModel
        binding.model = viewModel.recipeModel

        binding.btnSaveEdit.setOnClickListener {
            val recipeModel = RecipeModel(
                id = viewModel.recipeModel?.id ?: Random.nextInt(),
                title = binding.title.text.toString(),
                instruction = binding.content.text.toString(),
                type = binding.type.text.toString()
            )
            viewModel.onSave(recipeModel)
        }
    }

    companion object {
        fun newInstance(item: RecipeModel? = null): AddOrEditRecipeFragment {
            return AddOrEditRecipeFragment().apply {
                val bundle = bundleOf(RecipeDetailViewModel.RECIPE_DETAIL to item)
                arguments = bundle
            }
        }
    }
}