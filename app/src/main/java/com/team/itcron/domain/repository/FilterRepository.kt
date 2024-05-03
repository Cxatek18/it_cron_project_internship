package com.team.itcron.domain.repository

import com.team.itcron.domain.models.FilterToCategoryList

interface FilterRepository {
    suspend fun getFilterToCategoryList(): FilterToCategoryList
}