package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.Case
import com.team.itcron.domain.models.Filter
import com.team.itcron.domain.repository.CaseRepository
import kotlinx.coroutines.flow.Flow

class FilteringCasesUseCase(private val repository: CaseRepository) {
    fun filteringCases(filters: List<Filter>): Flow<List<Case>> {
        return repository.filteringCases(filters)
    }
}