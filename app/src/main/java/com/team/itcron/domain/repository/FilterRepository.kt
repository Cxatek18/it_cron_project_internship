package com.team.itcron.domain.repository

import com.team.itcron.domain.models.CategoryFilter
import com.team.itcron.domain.models.Filter
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    fun getFilterToCategoryList(): Flow<List<CategoryFilter>>

    suspend fun setSelectionFilter(filter: Filter)

    suspend fun clearFilterList()

    suspend fun formingListActiveFilters(): Flow<List<Filter>>
}