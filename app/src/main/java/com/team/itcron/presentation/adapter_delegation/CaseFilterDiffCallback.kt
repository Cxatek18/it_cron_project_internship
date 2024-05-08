package com.team.itcron.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import com.team.itcron.domain.models.CategoryFilter

class CaseFilterDiffCallback : DiffUtil.ItemCallback<CategoryFilter>() {

    override fun areItemsTheSame(oldItem: CategoryFilter, newItem: CategoryFilter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryFilter, newItem: CategoryFilter): Boolean {
        return oldItem == newItem
    }
}