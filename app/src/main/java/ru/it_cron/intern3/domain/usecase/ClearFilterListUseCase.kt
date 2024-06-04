package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.repository.FilterRepository

class ClearFilterListUseCase(private val repository: FilterRepository) {

    suspend fun clearFilterList() {
        repository.clearFilterList()
    }
}