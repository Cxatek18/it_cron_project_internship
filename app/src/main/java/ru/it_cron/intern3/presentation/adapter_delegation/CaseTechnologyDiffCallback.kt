package ru.it_cron.intern3.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import ru.it_cron.intern3.domain.models.Technology

class CaseTechnologyDiffCallback : DiffUtil.ItemCallback<Technology>() {

    override fun areItemsTheSame(oldItem: Technology, newItem: Technology): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Technology, newItem: Technology): Boolean {
        return oldItem == newItem
    }
}