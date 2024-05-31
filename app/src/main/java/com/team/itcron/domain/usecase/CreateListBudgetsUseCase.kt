package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.BudgetInForm
import com.team.itcron.domain.repository.BudgetInFormRepository
import kotlinx.coroutines.flow.Flow

class CreateListBudgetsUseCase(private val repository: BudgetInFormRepository) {

    fun createListBudgets(): Flow<List<BudgetInForm>> {
        return repository.createListBudgets()
    }
}