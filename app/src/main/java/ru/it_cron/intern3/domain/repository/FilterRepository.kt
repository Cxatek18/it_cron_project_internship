package ru.it_cron.intern3.domain.repository

import ru.it_cron.intern3.domain.models.CategoryFilter
import ru.it_cron.intern3.domain.models.Filter
import ru.it_cron.intern3.domain.models.FilterItem
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    fun getFilterToCategoryList(): Flow<List<CategoryFilter>>

    suspend fun setSelectionFilter(filter: FilterItem.Filter)

    suspend fun clearFilterList()

    fun formingListActiveFilters(): Flow<List<Filter>>
}