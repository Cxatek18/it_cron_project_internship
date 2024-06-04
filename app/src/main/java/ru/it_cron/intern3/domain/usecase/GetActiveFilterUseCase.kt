package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.Filter
import ru.it_cron.intern3.domain.repository.CaseRepository
import kotlinx.coroutines.flow.Flow

class GetActiveFilterUseCase(private val repository: CaseRepository) {

    fun getActiveFilter(): Flow<List<Filter>> {
        return repository.getActiveFilter()
    }
}