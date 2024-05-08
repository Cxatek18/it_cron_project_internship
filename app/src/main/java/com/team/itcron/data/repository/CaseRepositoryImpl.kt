package com.team.itcron.data.repository

import com.team.itcron.data.network.ApiService
import com.team.itcron.domain.models.Case
import com.team.itcron.domain.models.Filter
import com.team.itcron.domain.repository.CaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class CaseRepositoryImpl(val apiService: ApiService) : CaseRepository {

    private val caseToList = MutableStateFlow<List<Case>>(emptyList())

    private val filteringCaseToList = MutableStateFlow<List<Case>>(emptyList())

    override fun getCaseToList(): Flow<List<Case>> =
        flow<List<Case>> {
            if (caseToList.value.isEmpty()) {
                caseToList.value = apiService.getCaseToList().data
            }
            caseToList.collect { emit(it) }
        }

    override fun filteringCases(filters: List<Filter>): Flow<List<Case>> =
        flow<List<Case>> {
            if (caseToList.value.isNotEmpty()) {
                val filtersIds = filters.mapTo(mutableSetOf<String>()) { it.id }
                val filteringCaseList = caseToList.value.filter { case ->
                    case.filters.any { it.id in filtersIds }
                }
                filteringCaseToList.value = filteringCaseList
            }
            filteringCaseToList.collect { emit(it) }
        }
}