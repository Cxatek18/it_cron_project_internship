package com.team.itcron.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import com.team.itcron.domain.models.ServiceInForm

class ServiceInFormDiffCallback : DiffUtil.ItemCallback<ServiceInForm>() {

    override fun areItemsTheSame(oldItem: ServiceInForm, newItem: ServiceInForm): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: ServiceInForm, newItem: ServiceInForm): Boolean {
        return oldItem == newItem
    }
}