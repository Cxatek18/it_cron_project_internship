package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.Menu
import ru.it_cron.intern3.domain.repository.MenuRepository

class GetMenuUseCase(private val repository: MenuRepository) {

    suspend fun getMenu(): Menu {
        return repository.getMenu()
    }
}