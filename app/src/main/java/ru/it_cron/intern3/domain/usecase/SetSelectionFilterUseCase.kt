package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.FilterItem
import ru.it_cron.intern3.domain.repository.FilterRepository

class SetSelectionFilterUseCase(private val repository: FilterRepository) {

    suspend fun setSelectionFilter(filter: FilterItem.Filter) {
        repository.setSelectionFilter(filter)
    }
}