package com.team.itcron.data.repository

import com.team.itcron.data.network.ApiService
import com.team.itcron.domain.models.CategoryFilter
import com.team.itcron.domain.models.Filter
import com.team.itcron.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class FilterRepositoryImpl(val apiService: ApiService) : FilterRepository {

    private val filtersState = MutableStateFlow<List<CategoryFilter>>(emptyList())

    private val filtersActive = MutableStateFlow<List<Filter>>(emptyList())

    override fun getFilterToCategoryList(): Flow<List<CategoryFilter>> =
        flow<List<CategoryFilter>> {
            if (filtersState.value.isEmpty()) {
                filtersState.value = apiService.getFilterToCategoryList().data
            }
            filtersState.collect { emit(it) }
        }

    override suspend fun setSelectionFilter(filter: Filter) {
        filtersState.update { current ->
            val list = current.toMutableList()
            var indexCategoryFilter = 0
            var listNew = listOf<Filter>()
            for (filters in list) {
                val listFilters = filters.filters.toMutableList()
                val idx = listFilters.indexOfFirst {
                    it.id == filter.id
                }
                if (idx >= 0) {
                    listFilters[idx] = listFilters[idx].copy(isActive = !filter.isActive)
                    listNew = listFilters.toList()
                    break
                }
                indexCategoryFilter += 1
            }
            list[indexCategoryFilter] =
                list[indexCategoryFilter].copy(
                    filters = listNew
                )
            list
        }
    }

    override suspend fun clearFilterList() {
        filtersState.update { categoryFilters ->
            categoryFilters.map { categoryFilter ->
                categoryFilter.copy(filters = categoryFilter.filters.map { filter ->
                    filter.copy(isActive = false)
                })
            }
        }
    }

    override suspend fun formingListActiveFilters(): Flow<List<Filter>> =
        flow<List<Filter>> {
            val listFilterNew = mutableListOf<Filter>()
            filtersState.value.onEach { categoryFilter ->
                listFilterNew.addAll(
                    categoryFilter.filters.filter {
                        it.isActive
                    }
                )
            }
            filtersActive.value = listFilterNew.toList()
            filtersActive.collect { emit(it) }
        }
}