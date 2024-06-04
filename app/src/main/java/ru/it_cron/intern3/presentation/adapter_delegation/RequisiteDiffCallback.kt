package ru.it_cron.intern3.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import ru.it_cron.intern3.domain.models.Requisite

class RequisiteDiffCallback : DiffUtil.ItemCallback<Requisite>() {

    override fun areItemsTheSame(oldItem: Requisite, newItem: Requisite): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Requisite, newItem: Requisite): Boolean {
        return oldItem == newItem
    }
}