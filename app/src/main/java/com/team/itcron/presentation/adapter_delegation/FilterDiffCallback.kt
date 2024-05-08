package com.team.itcron.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import com.team.itcron.domain.models.Filter

class FilterDiffCallback : DiffUtil.ItemCallback<Filter>() {

    override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        return oldItem == newItem
    }
}