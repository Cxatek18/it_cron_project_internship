package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.BudgetInForm
import ru.it_cron.intern3.domain.repository.BudgetInFormRepository
import kotlinx.coroutines.flow.Flow

class GetActiveBudgetUseCase(private val repository: BudgetInFormRepository) {

    fun getActiveBudget(): Flow<BudgetInForm?> {
        return repository.getActiveBudget()
    }
}