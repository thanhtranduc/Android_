package com.tada.assignment.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tada.assignment.databinding.RecipeItemBinding
import com.tada.assignment.presentation.ui.model.RecipeModel

class MainCategoryAdapter(
    val recipeItems: ArrayList<RecipeModel>,
    val listener: OnItemClickListener
) :
    RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )
    }

    override fun getItemCount(): Int {
        return recipeItems.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.onBind(recipeItems.get(position))
    }

    class RecipeViewHolder(val binding: RecipeItemBinding, val onClick: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: RecipeModel) {
            item.title?.let {
                binding.tvDishName.text = it
            }
            binding.item.setOnClickListener {
                onClick.onClicked(item)
            }
        }
    }

    interface OnItemClickListener {
        fun onClicked(item: RecipeModel)
    }
}