package ru.it_cron.intern3.domain.models

import com.google.gson.annotations.SerializedName

data class Platform(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Name")
    val name: String
)
