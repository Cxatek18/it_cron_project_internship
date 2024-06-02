package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.FileItem
import com.team.itcron.domain.repository.FileItemRepository
import kotlinx.coroutines.flow.Flow

class GetListFileItemUseCase(private val repository: FileItemRepository) {

    fun getListFileItem(): Flow<List<FileItem>> {
        return repository.getListFileItem()
    }
}