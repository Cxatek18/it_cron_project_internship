package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.BudgetInForm
import ru.it_cron.intern3.domain.repository.BudgetInFormRepository

class SetSelectionBudgetUseCase(private val repository: BudgetInFormRepository) {

    fun setSelectionBudget(budgetInForm: BudgetInForm) {
        repository.setSelectionBudget(budgetInForm)
    }
}