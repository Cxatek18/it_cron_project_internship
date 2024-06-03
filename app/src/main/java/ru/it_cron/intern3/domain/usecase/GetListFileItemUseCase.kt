package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.FileItem
import ru.it_cron.intern3.domain.repository.FileItemRepository
import kotlinx.coroutines.flow.Flow

class GetListFileItemUseCase(private val repository: FileItemRepository) {

    fun getListFileItem(): Flow<List<FileItem>> {
        return repository.getListFileItem()
    }
}