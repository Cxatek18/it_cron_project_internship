package com.team.itcron.domain.models

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Icon")
    val icon: String,
    @SerializedName("Company")
    val company: String,
    @SerializedName("CustomerName")
    val customerName: String,
    @SerializedName("Text")
    val text: String,
    @SerializedName("CaseId")
    val caseId: String
)
