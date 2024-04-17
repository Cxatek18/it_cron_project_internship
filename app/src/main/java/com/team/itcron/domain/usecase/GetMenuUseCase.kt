package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.Menu
import com.team.itcron.domain.repository.MenuRepository

class GetMenuUseCase(private val repository: MenuRepository) {

    suspend fun getMenu(): Menu {
        return repository.getMenu()
    }
}