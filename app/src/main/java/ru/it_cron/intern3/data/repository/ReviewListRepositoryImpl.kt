package ru.it_cron.intern3.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import ru.it_cron.intern3.domain.models.Review
import ru.it_cron.intern3.domain.repository.ReviewListRepository
import kotlin.math.abs

class ReviewListRepositoryImpl : ReviewListRepository {

    private val reviews = MutableStateFlow<List<Review>>(emptyList())
    private val visibleReviews = MutableStateFlow<List<Review>>(emptyList())
    private val indexElement = MutableStateFlow<Int>(0)
    private val countTakeReview = 3

    override fun getFirstReviews(allReviews: List<Review>): Flow<List<Review>> =
        flow<List<Review>> {
            reviews.value = allReviews
            val listReviews = reviews.value
            if (listReviews.size <= countTakeReview) {
                visibleReviews.value = listReviews
            } else {
                visibleReviews.value = listReviews.take(countTakeReview)
                indexElement.value = 2
            }
            visibleReviews.collect { emit(it) }
        }


    override fun getMoreReview() {
        visibleReviews.update {
            val newList: List<Review>
            val difference = abs(reviews.value.size - countTakeReview)
            if (difference < 3) {
                newList = reviews.value.toMutableList()
            } else {
                indexElement.value = indexElement.value + countTakeReview
                newList = reviews.value.slice(0..indexElement.value)
            }
            newList.toList()
        }
    }
}