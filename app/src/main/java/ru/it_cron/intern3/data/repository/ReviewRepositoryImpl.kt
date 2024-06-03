package ru.it_cron.intern3.data.repository

import ru.it_cron.intern3.data.network.ApiService
import ru.it_cron.intern3.domain.models.ReviewInfo
import ru.it_cron.intern3.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class ReviewRepositoryImpl(val apiService: ApiService) : ReviewRepository {

    private val reviewState = MutableStateFlow<ReviewInfo?>(null)

    override fun getListReview(): Flow<ReviewInfo?> =
        flow<ReviewInfo?> {
            if (reviewState.value == null) {
                reviewState.value = apiService.getReview()
            }
            reviewState.collect { emit(it) }
        }
}