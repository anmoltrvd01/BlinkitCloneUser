package com.example.blinkitcloneuser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blinkitcloneuser.databinding.ItemViewProductCategoryBinding
import com.example.blinkitcloneuser.models.Category

class AdapterCategory (
    val categoryList: ArrayList<Category>
): RecyclerView.Adapter<AdapterCategory.CategoryViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        return CategoryViewHolder(ItemViewProductCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        holder.binding.apply {
            val category = categoryList[position]
            ivCategoryImage.setImageResource(category.image!!)
            tvCategoryTitle.text = category.title
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class CategoryViewHolder(val binding: ItemViewProductCategoryBinding): RecyclerView.ViewHolder(binding.root)
}