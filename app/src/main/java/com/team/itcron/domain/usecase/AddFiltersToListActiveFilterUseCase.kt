package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.Filter
import com.team.itcron.domain.repository.CaseRepository

class AddFiltersToListActiveFilterUseCase(private val repository: CaseRepository) {

    suspend fun addFiltersToListActiveFilter(filters: List<Filter>) {
        repository.addFiltersToListActiveFilter(filters)
    }
}