package com.team.itcron.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import com.team.itcron.domain.models.Requisite

class RequisiteDiffCallback : DiffUtil.ItemCallback<Requisite>() {

    override fun areItemsTheSame(oldItem: Requisite, newItem: Requisite): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Requisite, newItem: Requisite): Boolean {
        return oldItem == newItem
    }
}