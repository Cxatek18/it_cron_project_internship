package com.team.itcron.domain.models

import com.google.gson.annotations.SerializedName

data class CaseToList(
    @SerializedName("Error")
    val serverError: ServerError?,
    @SerializedName("Data")
    val data: List<Case>
)