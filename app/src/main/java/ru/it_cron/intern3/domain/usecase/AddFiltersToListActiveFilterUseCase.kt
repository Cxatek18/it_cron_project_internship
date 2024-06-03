package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.Filter
import ru.it_cron.intern3.domain.repository.CaseRepository

class AddFiltersToListActiveFilterUseCase(private val repository: CaseRepository) {

    suspend fun addFiltersToListActiveFilter(filters: List<Filter>) {
        repository.addFiltersToListActiveFilter(filters)
    }
}