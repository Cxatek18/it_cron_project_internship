package com.team.itcron.domain.repository

import com.team.itcron.domain.models.CategoryFilter
import com.team.itcron.domain.models.Filter
import com.team.itcron.domain.models.FilterItem
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    fun getFilterToCategoryList(): Flow<List<CategoryFilter>>

    suspend fun setSelectionFilter(filter: FilterItem.Filter)

    suspend fun clearFilterList()

    fun formingListActiveFilters(): Flow<List<Filter>>
}