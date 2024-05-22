package com.team.itcron.data.repository

import com.team.itcron.data.network.ApiService
import com.team.itcron.domain.models.ReviewInfo
import com.team.itcron.domain.repository.ReviewRepository
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