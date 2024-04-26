package com.team.itcron.data.repository

import com.team.itcron.data.network.ApiService
import com.team.itcron.domain.models.Menu
import com.team.itcron.domain.repository.MenuRepository

class MenuRepositoryImpl(val apiService: ApiService) : MenuRepository {

    override suspend fun getMenu(): Menu {
        return apiService.getMenu()
    }
}