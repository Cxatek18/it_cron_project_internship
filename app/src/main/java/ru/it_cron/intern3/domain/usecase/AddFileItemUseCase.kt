package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.FileItem
import ru.it_cron.intern3.domain.repository.FileItemRepository

class AddFileItemUseCase(private val repository: FileItemRepository) {

    fun addFileItem(fileItem: FileItem) {
        repository.addFileItem(fileItem)
    }
}