package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.BudgetInForm
import ru.it_cron.intern3.domain.repository.BudgetInFormRepository
import kotlinx.coroutines.flow.Flow

class CreateListBudgetsUseCase(private val repository: BudgetInFormRepository) {

    fun createListBudgets(): Flow<List<BudgetInForm>> {
        return repository.createListBudgets()
    }
}