package com.team.itcron.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import com.team.itcron.domain.models.Case

class CaseDiffCallback : DiffUtil.ItemCallback<Case>() {

    override fun areItemsTheSame(oldItem: Case, newItem: Case): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Case, newItem: Case): Boolean {
        return oldItem == newItem
    }
}