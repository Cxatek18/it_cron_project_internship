package com.team.itcron.data.repository

import com.team.itcron.data.network.ApiService
import com.team.itcron.domain.models.FilterToCategoryList
import com.team.itcron.domain.repository.FilterRepository

class FilterRepositoryImpl(val apiService: ApiService): FilterRepository {

    override suspend fun getFilterToCategoryList(): FilterToCategoryList {
        return apiService.getFilterToCategoryList()
    }
}