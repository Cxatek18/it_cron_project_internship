package com.team.itcron.domain.models

import com.google.gson.annotations.SerializedName

data class CategoryFilter(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Filters")
    val filters: List<Filter>
)
