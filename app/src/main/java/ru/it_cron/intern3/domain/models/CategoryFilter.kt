package ru.it_cron.intern3.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryFilter(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Filters")
    val filters: List<Filter>
) : Parcelable
