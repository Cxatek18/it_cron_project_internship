package com.team.itcron.domain.repository

import com.team.itcron.domain.models.Menu

interface MenuRepository {
    suspend fun getMenu(): Menu
}