package com.team.itcron.di

import com.team.itcron.data.network.ApiService
import com.team.itcron.data.repository.CaseDetailRepositoryImpl
import com.team.itcron.data.repository.CaseRepositoryImpl
import com.team.itcron.data.repository.FilterRepositoryImpl
import com.team.itcron.data.repository.MenuRepositoryImpl
import com.team.itcron.data.repository.ReviewRepositoryImpl
import com.team.itcron.domain.repository.CaseDetailRepository
import com.team.itcron.domain.repository.CaseRepository
import com.team.itcron.domain.repository.FilterRepository
import com.team.itcron.domain.repository.MenuRepository
import com.team.itcron.domain.repository.ReviewRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<MenuRepository> {
        MenuRepositoryImpl(apiService = get())
    }

    single<CaseRepository> {
        CaseRepositoryImpl(apiService = get())
    }

    single<FilterRepository> {
        FilterRepositoryImpl(apiService = get())
    }

    single<CaseDetailRepository> {
        CaseDetailRepositoryImpl(apiService = get())
    }

    single<ReviewRepository> {
        ReviewRepositoryImpl(apiService = get())
    }

    single<ApiService> {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://services.it-cron.ru/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}