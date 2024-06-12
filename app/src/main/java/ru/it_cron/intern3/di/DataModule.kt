package ru.it_cron.intern3.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.it_cron.intern3.data.network.ApiService
import ru.it_cron.intern3.data.repository.BudgetInFormRepositoryImpl
import ru.it_cron.intern3.data.repository.CaseDetailRepositoryImpl
import ru.it_cron.intern3.data.repository.CaseRepositoryImpl
import ru.it_cron.intern3.data.repository.FileItemRepositoryImpl
import ru.it_cron.intern3.data.repository.FilterRepositoryImpl
import ru.it_cron.intern3.data.repository.FormInfoRepositoryImpl
import ru.it_cron.intern3.data.repository.MenuRepositoryImpl
import ru.it_cron.intern3.data.repository.PlaceRecognitionInFormRepositoryImpl
import ru.it_cron.intern3.data.repository.RequisiteRepositoryImpl
import ru.it_cron.intern3.data.repository.ReviewListRepositoryImpl
import ru.it_cron.intern3.data.repository.ReviewRepositoryImpl
import ru.it_cron.intern3.data.repository.ServiceInFormRepositoryImpl
import ru.it_cron.intern3.domain.repository.BudgetInFormRepository
import ru.it_cron.intern3.domain.repository.CaseDetailRepository
import ru.it_cron.intern3.domain.repository.CaseRepository
import ru.it_cron.intern3.domain.repository.FileItemRepository
import ru.it_cron.intern3.domain.repository.FilterRepository
import ru.it_cron.intern3.domain.repository.FormInfoRepository
import ru.it_cron.intern3.domain.repository.MenuRepository
import ru.it_cron.intern3.domain.repository.PlaceRecognitionInFormRepository
import ru.it_cron.intern3.domain.repository.RequisiteRepository
import ru.it_cron.intern3.domain.repository.ReviewListRepository
import ru.it_cron.intern3.domain.repository.ReviewRepository
import ru.it_cron.intern3.domain.repository.ServiceInFormRepository

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

    single<ServiceInFormRepository> {
        ServiceInFormRepositoryImpl(context = get())
    }

    single<BudgetInFormRepository> {
        BudgetInFormRepositoryImpl(context = get())
    }

    single<PlaceRecognitionInFormRepository> {
        PlaceRecognitionInFormRepositoryImpl(context = get())
    }

    single<FileItemRepository> {
        FileItemRepositoryImpl()
    }

    single<FormInfoRepository> {
        FormInfoRepositoryImpl(apiService = get())
    }

    single<RequisiteRepository> {
        RequisiteRepositoryImpl(context = get())
    }

    single<ReviewListRepository> {
        ReviewListRepositoryImpl()
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