package com.team.itcron.domain.repository

import com.team.itcron.domain.models.ReviewInfo
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    fun getListReview(): Flow<ReviewInfo?>
}