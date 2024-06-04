package ru.it_cron.intern3.data.repository

import ru.it_cron.intern3.data.network.ApiService
import ru.it_cron.intern3.domain.models.Menu
import ru.it_cron.intern3.domain.repository.MenuRepository

class MenuRepositoryImpl(val apiService: ApiService) : MenuRepository {

    override suspend fun getMenu(): Menu {
        return apiService.getMenu()
    }
}