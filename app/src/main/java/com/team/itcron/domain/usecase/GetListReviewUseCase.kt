package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.ReviewInfo
import com.team.itcron.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow

class GetListReviewUseCase(private val repository: ReviewRepository) {
    fun getListReview(): Flow<ReviewInfo?> {
        return repository.getListReview()
    }
}