package ru.it_cron.intern3.domain.models

import com.google.gson.annotations.SerializedName

data class FormResponse(
    @SerializedName("Error")
    val error: ServerError,
    @SerializedName("Data")
    val data: String
)
