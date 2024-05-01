package com.team.itcron.domain.models

import com.google.gson.annotations.SerializedName

data class Case(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Image")
    val image: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Filters")
    val filters: List<Filter>
)
