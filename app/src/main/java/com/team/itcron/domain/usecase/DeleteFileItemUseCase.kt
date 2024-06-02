package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.FileItem
import com.team.itcron.domain.repository.FileItemRepository

class DeleteFileItemUseCase(private val repository: FileItemRepository) {

    fun deleteFileItem(fileItem: FileItem) {
        repository.deleteFileItem(fileItem)
    }
}