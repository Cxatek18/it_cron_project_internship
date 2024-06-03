package ru.it_cron.intern3.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import ru.it_cron.intern3.domain.models.ServiceInForm

class ServiceInFormDiffCallback : DiffUtil.ItemCallback<ServiceInForm>() {

    override fun areItemsTheSame(oldItem: ServiceInForm, newItem: ServiceInForm): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: ServiceInForm, newItem: ServiceInForm): Boolean {
        return oldItem == newItem
    }
}