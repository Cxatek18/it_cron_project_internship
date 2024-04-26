package com.team.itcron.data.network

import com.team.itcron.domain.models.Menu
import retrofit2.http.GET

interface ApiService {

    @GET("cabinet/menu")
    suspend fun getMenu(): Menu
}