package com.team.itcron.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import com.team.itcron.domain.models.Technology

class CaseTechnologyDiffCallback : DiffUtil.ItemCallback<Technology>() {

    override fun areItemsTheSame(oldItem: Technology, newItem: Technology): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Technology, newItem: Technology): Boolean {
        return oldItem == newItem
    }
}