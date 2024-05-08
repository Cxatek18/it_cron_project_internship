package com.team.itcron.data.network

import com.team.itcron.domain.models.CaseToList
import com.team.itcron.domain.models.FilterToCategoryList
import com.team.itcron.domain.models.Menu
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @GET("cabinet/menu")
    suspend fun getMenu(): Menu

    @GET("cases")
    @Headers(
        "Accept-Language: ru,en;q=0.9"
    )
    suspend fun getCaseToList(): CaseToList

    @GET("filters")
    @Headers(
        "Accept-Language: ru,en;q=0.9"
    )
    suspend fun getFilterToCategoryList(): FilterToCategoryList
}