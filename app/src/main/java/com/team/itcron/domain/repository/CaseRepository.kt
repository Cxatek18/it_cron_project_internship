package com.team.itcron.domain.repository

import com.team.itcron.domain.models.Case
import com.team.itcron.domain.models.Filter
import kotlinx.coroutines.flow.Flow

interface CaseRepository {
    fun getCaseToList(): Flow<List<Case>>

    fun filteringCases(filters: List<Filter>): Flow<List<Case>>

    fun getActiveFilter(): Flow<List<Filter>>

    suspend fun addFiltersToListActiveFilter(filters: List<Filter>)
}