package com.team.itcron.domain.models

sealed class FilterItem {
    abstract val id: String

    data class Category(
        override val id: String,
        val name: String
    ) : FilterItem()

    data class Filter(
        override val id: String,
        val name: String,
        val isActive: Boolean = false
    ) : FilterItem()
}
