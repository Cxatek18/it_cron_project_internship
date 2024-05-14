package com.team.itcron.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import com.team.itcron.domain.models.Platform

class CasePlatformDiffCallback : DiffUtil.ItemCallback<Platform>() {

    override fun areItemsTheSame(oldItem: Platform, newItem: Platform): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Platform, newItem: Platform): Boolean {
        return oldItem == newItem
    }
}