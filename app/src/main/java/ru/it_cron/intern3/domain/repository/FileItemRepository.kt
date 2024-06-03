package ru.it_cron.intern3.domain.repository

import ru.it_cron.intern3.domain.models.FileItem
import kotlinx.coroutines.flow.Flow

interface FileItemRepository {
    fun getListFileItem(): Flow<List<FileItem>>

    fun addFileItem(fileItem: FileItem)

    fun deleteFileItem(fileItem: FileItem)

    fun clearListFileItem()
}