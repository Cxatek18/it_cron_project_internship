package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.FileItem
import ru.it_cron.intern3.domain.repository.FileItemRepository

class DeleteFileItemUseCase(private val repository: FileItemRepository) {

    fun deleteFileItem(fileItem: FileItem) {
        repository.deleteFileItem(fileItem)
    }
}