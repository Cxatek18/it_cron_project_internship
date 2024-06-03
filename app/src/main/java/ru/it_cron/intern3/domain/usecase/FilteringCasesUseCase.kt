package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.Case
import ru.it_cron.intern3.domain.models.Filter
import ru.it_cron.intern3.domain.repository.CaseRepository
import kotlinx.coroutines.flow.Flow

class FilteringCasesUseCase(private val repository: CaseRepository) {
    fun filteringCases(filters: List<Filter>): Flow<List<Case>> {
        return repository.filteringCases(filters)
    }
}