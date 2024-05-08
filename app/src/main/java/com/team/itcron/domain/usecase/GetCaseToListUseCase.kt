package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.Case
import com.team.itcron.domain.repository.CaseRepository
import kotlinx.coroutines.flow.Flow

class GetCaseToListUseCase(private val repository: CaseRepository) {

    fun getCaseToList(): Flow<List<Case>> {
        return repository.getCaseToList()
    }
}