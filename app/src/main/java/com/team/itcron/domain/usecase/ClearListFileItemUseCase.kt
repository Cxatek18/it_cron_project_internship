package com.team.itcron.domain.usecase

import com.team.itcron.domain.repository.FileItemRepository

class ClearListFileItemUseCase(private val repository: FileItemRepository) {

    fun clearListFileItem() {
        repository.clearListFileItem()
    }
}