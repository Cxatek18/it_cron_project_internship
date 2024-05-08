package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.CategoryFilter
import com.team.itcron.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow

class GetFilterToCategoryListUseCase(private val repository: FilterRepository) {

    fun getFilterToCategoryList(): Flow<List<CategoryFilter>> {
        return repository.getFilterToCategoryList()
    }
}