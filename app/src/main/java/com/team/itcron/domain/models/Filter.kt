package com.team.itcron.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Name")
    val name: String,
    var isActive: Boolean = false
) : Parcelable
