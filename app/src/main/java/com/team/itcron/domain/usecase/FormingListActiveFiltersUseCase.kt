package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.Filter
import com.team.itcron.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow

class FormingListActiveFiltersUseCase(private val repository: FilterRepository) {

    suspend fun formingListActiveFilters(): Flow<List<Filter>> {
        return repository.formingListActiveFilters()
    }
}