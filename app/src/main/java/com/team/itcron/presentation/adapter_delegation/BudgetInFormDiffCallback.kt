package com.team.itcron.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import com.team.itcron.domain.models.BudgetInForm

class BudgetInFormDiffCallback : DiffUtil.ItemCallback<BudgetInForm>() {

    override fun areItemsTheSame(oldItem: BudgetInForm, newItem: BudgetInForm): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: BudgetInForm, newItem: BudgetInForm): Boolean {
        return oldItem == newItem
    }
}