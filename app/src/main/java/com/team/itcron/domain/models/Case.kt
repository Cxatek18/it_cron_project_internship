package com.team.itcron.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Case(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Image")
    val image: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Filters")
    val filters: List<Filter>
) : Parcelable
