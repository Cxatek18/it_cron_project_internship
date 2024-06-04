package ru.it_cron.intern3.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import ru.it_cron.intern3.domain.models.Review

class ReviewDiffCallback : DiffUtil.ItemCallback<Review>() {

    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}