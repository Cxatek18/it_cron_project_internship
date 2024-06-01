package com.team.itcron.presentation.adapter_delegation

import androidx.core.content.ContextCompat
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.team.itcron.R
import com.team.itcron.databinding.BudgetFormItemBinding
import com.team.itcron.domain.models.BudgetInForm

fun budgetInFormDelegate(
    setSelectionBudget: (budgetInForm: BudgetInForm) -> Unit
) = adapterDelegateViewBinding<BudgetInForm, BudgetInForm, BudgetFormItemBinding>(
    { layoutInflater, root ->
        BudgetFormItemBinding.inflate(
            layoutInflater, root, false
        )
    }
) {
    with(binding) {
        textBudget.setOnClickListener {
            setSelectionBudget(item)
        }
    }

    bind {
        with(binding) {
            textBudget.text = item.title
            textBudget.setBackgroundDrawable(
                getDrawable(R.drawable.state_form_item)
            )
            textBudget.setTextColor(
                if (item.isActive)
                    ContextCompat.getColor(context, R.color.white)
                else
                    ContextCompat.getColor(context, R.color.color_main)
            )
            textBudget.isSelected = item.isActive
        }
    }
}