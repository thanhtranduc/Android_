package com.tada.assignment.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.tada.assignment.R
import com.tada.assignment.databinding.ActivityHomeBinding
import com.tada.base.activity.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<HomeViewModel>() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.isLoading.observe(this) {
            if (it) {
                binding.progressCircular.show()
            } else {
                binding.progressCircular.hide()
            }
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.mainView, RecipeListFragment())
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.getAllRecipeData()
    }
}