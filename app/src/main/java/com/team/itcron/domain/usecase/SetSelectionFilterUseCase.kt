package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.Filter
import com.team.itcron.domain.repository.FilterRepository

class SetSelectionFilterUseCase(private val repository: FilterRepository) {

    suspend fun setSelectionFilter(filter: Filter) {
        repository.setSelectionFilter(filter)
    }
}