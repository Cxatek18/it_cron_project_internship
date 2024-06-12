package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.repository.ReviewListRepository

class GetMoreReviewUseCase(private val repository: ReviewListRepository) {

    fun getMoreReview() {
        repository.getMoreReview()
    }
}