package com.team.itcron.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import com.team.itcron.domain.models.FilterItem

class FilterItemDiffCallback : DiffUtil.ItemCallback<FilterItem>() {

    override fun areItemsTheSame(oldItem: FilterItem, newItem: FilterItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FilterItem, newItem: FilterItem): Boolean {
        return oldItem == newItem
    }
}