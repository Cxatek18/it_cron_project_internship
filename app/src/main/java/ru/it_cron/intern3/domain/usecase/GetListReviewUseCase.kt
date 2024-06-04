package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.ReviewInfo
import ru.it_cron.intern3.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow

class GetListReviewUseCase(private val repository: ReviewRepository) {
    fun getListReview(): Flow<ReviewInfo?> {
        return repository.getListReview()
    }
}