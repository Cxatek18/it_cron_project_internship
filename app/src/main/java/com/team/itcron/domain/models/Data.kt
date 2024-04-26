package com.team.itcron.domain.models

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("IsCabinetAvailable")
    val isCabinetAvailable: Boolean
)
