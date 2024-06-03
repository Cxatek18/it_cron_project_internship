package ru.it_cron.intern3.domain.models

import com.google.gson.annotations.SerializedName

data class CaseDetailInfo(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Images")
    val images: List<String>,
    @SerializedName("Task")
    val task: String,
    @SerializedName("Technologies")
    val technologies: List<Technology>,
    @SerializedName("Platforms")
    val platforms: List<Platform>,
    @SerializedName("FeaturesTitle")
    val featuresTitle: String?,
    @SerializedName("FeaturesDone")
    val featuresDone: List<String>?,
    @SerializedName("TestimonialId")
    val testimonialId: String,
    @SerializedName("CaseColor")
    val caseColor: String?,
    @SerializedName("iOSUrl")
    val iosUrl: String?,
    @SerializedName("AndroidUrl")
    val androidUrl: String?,
    @SerializedName("WebUrl")
    val webUrl: String?
)
