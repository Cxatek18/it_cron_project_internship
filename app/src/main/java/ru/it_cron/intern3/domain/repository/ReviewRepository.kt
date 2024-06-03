package ru.it_cron.intern3.domain.repository

import ru.it_cron.intern3.domain.models.ReviewInfo
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    fun getListReview(): Flow<ReviewInfo?>
}