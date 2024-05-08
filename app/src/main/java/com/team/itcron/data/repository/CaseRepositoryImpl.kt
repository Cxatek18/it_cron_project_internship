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
                val filteringCaseList = mutableListOf<Case>()
                filters.forEach { filter ->
                    caseToList.value.forEach { case ->
                        case.filters.forEach { filterCase ->
                            if (filter.id == filterCase.id) {
                                filteringCaseList.add(case)
                            }
                        }
                    }
                }
                filteringCaseList.toList()
                filteringCaseToList.value = filteringCaseList.toList()
            }
            filteringCaseToList.collect { emit(it) }
        }
}