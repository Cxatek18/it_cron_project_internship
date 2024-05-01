package com.team.itcron.domain.models

import com.google.gson.annotations.SerializedName

data class ServerError(
    @SerializedName("Message")
    val message: String,
    @SerializedName("Code")
    val code: Int
)
