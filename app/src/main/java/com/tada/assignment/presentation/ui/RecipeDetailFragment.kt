package com.tada.assignment.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tada.assignment.R
import com.tada.assignment.databinding.RecipeDetailBinding
import com.tada.assignment.presentation.ui.RecipeDetailViewModel.Companion.RECIPE_DETAIL
import com.tada.assignment.presentation.ui.model.RecipeModel
import com.tada.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailFragment : BaseFragment<RecipeDetailBinding>() {

    private val viewModel: RecipeDetailViewModel by viewModels()
    private val activityModel: HomeViewModel by activityViewModels()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): RecipeDetailBinding = RecipeDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isOnBack.observe(viewLifecycleOwner) {
            if (it) {
                activity?.onBackPressed()
            }
        }
        viewModel.isOnEdit.observe(viewLifecycleOwner) {
            if (it) {
                val transaction = parentFragmentManager.beginTransaction()
                transaction.add(
                    R.id.mainView,
                    AddOrEditRecipeFragment.newInstance(viewModel.recipeModel)
                )
                transaction.addToBackStack("recipe add or edit")
                transaction.commit()
            }
        }
        activityModel.isUpdated.observe(viewLifecycleOwner) {
            viewModel.getItemUpdated()
        }
        viewModel.isOnUpdated.observe(viewLifecycleOwner) {
            binding.model = viewModel.recipeModel
        }
        binding.viewModel = viewModel
        binding.model = viewModel.recipeModel

    }

    companion object {
        fun newInstance(item: RecipeModel): RecipeDetailFragment {
            return RecipeDetailFragment().apply {
                val bundle = bundleOf(RECIPE_DETAIL to item)
                arguments = bundle
            }
        }
    }
}