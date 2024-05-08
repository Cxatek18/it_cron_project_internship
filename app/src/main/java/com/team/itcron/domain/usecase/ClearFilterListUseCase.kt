package com.team.itcron.domain.usecase

import com.team.itcron.domain.repository.FilterRepository

class ClearFilterListUseCase(private val repository: FilterRepository) {

    suspend fun clearFilterList() {
        repository.clearFilterList()
    }
}