package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.BudgetInForm
import com.team.itcron.domain.repository.BudgetInFormRepository

class SetSelectionBudgetUseCase(private val repository: BudgetInFormRepository) {

    fun setSelectionBudget(budgetInForm: BudgetInForm) {
        repository.setSelectionBudget(budgetInForm)
    }
}