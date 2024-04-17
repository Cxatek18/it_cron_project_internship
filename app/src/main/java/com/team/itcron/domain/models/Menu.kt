package com.team.itcron.domain.models

import com.google.gson.annotations.SerializedName

data class Menu(
    @SerializedName("Error")
    val isError: String?,
    @SerializedName("Data")
    val data: Data
)
