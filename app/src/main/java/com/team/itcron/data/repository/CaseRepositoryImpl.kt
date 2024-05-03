package com.team.itcron.data.repository

import com.team.itcron.data.network.ApiService
import com.team.itcron.domain.models.CaseToList
import com.team.itcron.domain.repository.CaseRepository

class CaseRepositoryImpl(val apiService: ApiService) : CaseRepository {

    override suspend fun getCaseToList(): CaseToList {
        return apiService.getCaseToList()
    }
}