package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.FilterItem
import com.team.itcron.domain.repository.FilterRepository

class SetSelectionFilterUseCase(private val repository: FilterRepository) {

    suspend fun setSelectionFilter(filter: FilterItem.Filter) {
        repository.setSelectionFilter(filter)
    }
}