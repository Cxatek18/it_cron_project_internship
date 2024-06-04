package ru.it_cron.intern3.presentation.adapter_delegation

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.it_cron.intern3.R
import ru.it_cron.intern3.databinding.BudgetFormItemBinding
import ru.it_cron.intern3.domain.models.BudgetInForm

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
            textBudget.isSelected = item.isActive
        }
    }
}