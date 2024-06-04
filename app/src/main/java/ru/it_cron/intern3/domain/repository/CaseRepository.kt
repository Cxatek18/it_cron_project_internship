package ru.it_cron.intern3.domain.repository

import ru.it_cron.intern3.domain.models.Case
import ru.it_cron.intern3.domain.models.Filter
import kotlinx.coroutines.flow.Flow

interface CaseRepository {
    fun getCaseToList(): Flow<List<Case>>

    fun filteringCases(filters: List<Filter>): Flow<List<Case>>

    fun getActiveFilter(): Flow<List<Filter>>

    suspend fun addFiltersToListActiveFilter(filters: List<Filter>)
}