package ru.it_cron.intern3.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil

class CaseOpportunityDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}