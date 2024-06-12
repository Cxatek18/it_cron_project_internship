package ru.it_cron.intern3.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.it_cron.intern3.domain.models.Review
import ru.it_cron.intern3.domain.repository.ReviewListRepository

class GetFirstReviewsUseCase(private val repository: ReviewListRepository) {

    fun getFirstReviews(allReviews: List<Review>): Flow<List<Review>> {
        return repository.getFirstReviews(allReviews)
    }
}