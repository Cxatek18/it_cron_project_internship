package ru.it_cron.intern3.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import ru.it_cron.intern3.domain.models.FilterItem

class FilterItemDiffCallback : DiffUtil.ItemCallback<FilterItem>() {

    override fun areItemsTheSame(oldItem: FilterItem, newItem: FilterItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FilterItem, newItem: FilterItem): Boolean {
        return oldItem == newItem
    }
}