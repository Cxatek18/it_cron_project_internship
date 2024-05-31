package com.team.itcron.data.network

import com.team.itcron.domain.models.CaseDetail
import com.team.itcron.domain.models.CaseToList
import com.team.itcron.domain.models.FilterToCategoryList
import com.team.itcron.domain.models.FormResponse
import com.team.itcron.domain.models.Menu
import com.team.itcron.domain.models.ReviewInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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


    @Multipart
    @POST("request")
    @Headers(
        "Accept-Language: ru,en;q=0.9"
    )
    suspend fun postForm(
        @Part("Services") services: RequestBody,
        @Part("Budget") budget: RequestBody,
        @Part("Description") description: RequestBody,
        @Part("ContactName") contactName: RequestBody,
        @Part("ContactCompany") contactCompany: RequestBody,
        @Part("ContactEmail") contactEmail: RequestBody,
        @Part("ContactPhone") contactPhone: RequestBody,
        @Part("RequestFrom") requestFrom: RequestBody,
    ): Response<FormResponse>

    @Multipart
    @POST("request")
    @Headers(
        "Accept-Language: ru,en;q=0.9"
    )
    suspend fun postFormWithFiles(
        @Part("Services") services: RequestBody,
        @Part("Budget") budget: RequestBody,
        @Part("Description") description: RequestBody,
        @Part("ContactName") contactName: RequestBody,
        @Part("ContactCompany") contactCompany: RequestBody,
        @Part("ContactEmail") contactEmail: RequestBody,
        @Part("ContactPhone") contactPhone: RequestBody,
        @Part("RequestFrom") requestFrom: RequestBody,
        @Part files: List<MultipartBody.Part>? = null,
    ): Response<FormResponse>
}