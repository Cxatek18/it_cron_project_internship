package ru.it_cron.intern3.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.it_cron.intern3.domain.models.Review

interface ReviewListRepository {
    fun getFirstReviews(allReviews: List<Review>): Flow<List<Review>>

    fun getMoreReview()
}