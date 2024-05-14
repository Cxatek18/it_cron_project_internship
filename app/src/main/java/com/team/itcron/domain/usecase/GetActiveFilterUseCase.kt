package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.Filter
import com.team.itcron.domain.repository.CaseRepository
import kotlinx.coroutines.flow.Flow

class GetActiveFilterUseCase(private val repository: CaseRepository) {

    fun getActiveFilter(): Flow<List<Filter>> {
        return repository.getActiveFilter()
    }
}