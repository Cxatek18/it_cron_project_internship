package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.repository.FileItemRepository

class ClearListFileItemUseCase(private val repository: FileItemRepository) {

    fun clearListFileItem() {
        repository.clearListFileItem()
    }
}