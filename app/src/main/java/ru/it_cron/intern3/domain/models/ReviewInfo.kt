package ru.it_cron.intern3.domain.models

import com.google.gson.annotations.SerializedName

data class ReviewInfo(
    @SerializedName("Error")
    val serverError: ServerError?,
    @SerializedName("Data")
    val data: List<Review>
)
