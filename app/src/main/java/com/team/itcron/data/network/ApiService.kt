package com.team.itcron.data.network

import com.team.itcron.domain.models.CaseDetail
import com.team.itcron.domain.models.CaseToList
import com.team.itcron.domain.models.FilterToCategoryList
import com.team.itcron.domain.models.Menu
import com.team.itcron.domain.models.ReviewInfo
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

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

    @GET("cases/{id}")
    @Headers(
        "Accept-Language: ru,en;q=0.9"
    )
    suspend fun getCaseDetail(
        @Path("id") id: String,
    ): CaseDetail

    @GET("testimonials")
    @Headers(
        "Accept-Language: ru,en;q=0.9"
    )
    suspend fun getReview(): ReviewInfo
}