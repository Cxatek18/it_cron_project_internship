package com.team.itcron.domain.repository

import com.team.itcron.domain.models.FileItem
import kotlinx.coroutines.flow.Flow

interface FileItemRepository {

    fun getListFileItem(): Flow<List<FileItem>>

    fun addFileItem(fileItem: FileItem)

    fun deleteFileItem(fileItem: FileItem)

    fun clearListFileItem()
}