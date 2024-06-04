package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.Filter
import ru.it_cron.intern3.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow

class FormingListActiveFiltersUseCase(private val repository: FilterRepository) {

    fun formingListActiveFilters(): Flow<List<Filter>> {
        return repository.formingListActiveFilters()
    }
}