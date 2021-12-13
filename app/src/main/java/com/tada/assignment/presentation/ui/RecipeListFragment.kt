package com.tada.assignment.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tada.assignment.R
import com.tada.assignment.databinding.RecipeListFragmentBinding
import com.tada.assignment.presentation.ui.adapter.MainCategoryAdapter
import com.tada.assignment.presentation.ui.model.RecipeModel
import com.tada.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecipeListFragment : BaseFragment<RecipeListFragmentBinding>(),
    MainCategoryAdapter.OnItemClickListener {

    private val viewModel: HomeViewModel by activityViewModels()
    private var mainCategoryAdapter = MainCategoryAdapter(arrayListOf(), this)


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): RecipeListFragmentBinding = RecipeListFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMainCategory.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        binding.rvMainCategory.adapter = mainCategoryAdapter

        viewModel.isFetchAll.observe(viewLifecycleOwner) {
            viewModel.recipeItems?.let {
                mainCategoryAdapter.recipeItems.clear()
                mainCategoryAdapter.recipeItems.addAll(it)
                mainCategoryAdapter.notifyDataSetChanged()
            }
        }
        binding.btnAdd.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.add(R.id.mainView, AddOrEditRecipeFragment.newInstance())
            transaction.addToBackStack("recipe add or edit")
            transaction.commit()
        }

        viewModel.isGetTypeDone.observe(viewLifecycleOwner) {
            if (it) {
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    viewModel.allType
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerEmployee.setAdapter(adapter)
            }
        }
        viewModel.getAllRecipeData()
        viewModel.getAllType()

        binding.spinnerEmployee.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    context,
                    "This function is coming soon",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    override fun onClicked(item: RecipeModel) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.mainView, RecipeDetailFragment.newInstance(item))
        transaction.addToBackStack("recipe detail")
        transaction.commit()
    }
}