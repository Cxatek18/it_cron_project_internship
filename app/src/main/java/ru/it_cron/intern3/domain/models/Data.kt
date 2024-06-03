package ru.it_cron.intern3.domain.models

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("IsCabinetAvailable")
    val isCabinetAvailable: Boolean
)
