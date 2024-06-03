package ru.it_cron.intern3.domain.models

data class FormInfo(
    val services: String,
    val budget: String,
    val description: String,
    val contactName: String,
    val contactCompany: String,
    val contactEmail: String,
    val contactPhone: String,
    val requestFrom: String
)
