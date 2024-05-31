package com.team.itcron.presentation.adapter_delegation

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
            if (item.isActive) {
                textBudget.setBackgroundDrawable(
                    getDrawable(R.drawable.background_active_service_item)
                )
                textBudget.setTextColor(getColor(R.color.white))
            } else {
                textBudget.setBackgroundDrawable(
                    getDrawable(R.drawable.background_no_active_service_item)
                )
                textBudget.setTextColor(getColor(R.color.color_main))
            }
        }
    }
}