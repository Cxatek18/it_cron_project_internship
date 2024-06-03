package ru.it_cron.intern3.data.repository

import ru.it_cron.intern3.data.network.ApiService
import ru.it_cron.intern3.domain.models.Case
import ru.it_cron.intern3.domain.models.Filter
import ru.it_cron.intern3.domain.repository.CaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class CaseRepositoryImpl(val apiService: ApiService) : CaseRepository {

    private val caseToList = MutableStateFlow<List<Case>>(emptyList())

    private val filteringCaseToList = MutableStateFlow<List<Case>>(emptyList())

    private val listActiveFilter = MutableStateFlow<List<Filter>>(emptyList())

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

    override fun getActiveFilter(): Flow<List<Filter>> =
        flow<List<Filter>> {
            listActiveFilter.collect { emit(it) }
        }

    override suspend fun addFiltersToListActiveFilter(filters: List<Filter>) {
        listActiveFilter.value = filters
    }
}