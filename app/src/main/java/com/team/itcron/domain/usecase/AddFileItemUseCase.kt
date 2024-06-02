package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.FileItem
import com.team.itcron.domain.repository.FileItemRepository

class AddFileItemUseCase(private val repository: FileItemRepository) {

    fun addFileItem(fileItem: FileItem) {
        repository.addFileItem(fileItem)
    }
}