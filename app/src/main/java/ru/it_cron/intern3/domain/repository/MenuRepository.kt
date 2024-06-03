package ru.it_cron.intern3.domain.repository

import ru.it_cron.intern3.domain.models.Menu

interface MenuRepository {
    suspend fun getMenu(): Menu
}