package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.BudgetInForm
import com.team.itcron.domain.repository.BudgetInFormRepository
import kotlinx.coroutines.flow.Flow

class GetActiveBudgetUseCase(private val repository: BudgetInFormRepository) {

    fun getActiveBudget(): Flow<BudgetInForm?> {
        return repository.getActiveBudget()
    }
}