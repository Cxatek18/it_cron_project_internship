package ru.it_cron.intern3.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import ru.it_cron.intern3.domain.models.Case

class CaseDiffCallback : DiffUtil.ItemCallback<Case>() {

    override fun areItemsTheSame(oldItem: Case, newItem: Case): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Case, newItem: Case): Boolean {
        return oldItem == newItem
    }
}