package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.FilterToCategoryList
import com.team.itcron.domain.repository.FilterRepository

class GetFilterToCategoryListUseCase(private val repository: FilterRepository) {

    suspend fun getFilterToCategoryList(): FilterToCategoryList {
        return repository.getFilterToCategoryList()
    }
}