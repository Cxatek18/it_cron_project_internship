package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.CaseToList
import com.team.itcron.domain.repository.CaseRepository

class GetCaseToListUseCase(private val repository: CaseRepository) {

    suspend fun getCaseToList(): CaseToList {
        return repository.getCaseToList()
    }
}