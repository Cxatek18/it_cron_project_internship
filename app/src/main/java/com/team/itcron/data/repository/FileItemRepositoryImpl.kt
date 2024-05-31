package com.team.itcron.data.repository

import com.team.itcron.domain.models.FileItem
import com.team.itcron.domain.repository.FileItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class FileItemRepositoryImpl : FileItemRepository {

    private val listFile = MutableStateFlow<List<FileItem>>(emptyList())

    override fun getListFileItem(): Flow<List<FileItem>> =
        flow<List<FileItem>> {
            listFile.collect { emit(it) }
        }

    override fun addFileItem(fileItem: FileItem) {
        listFile.update { listFile ->
            val newList = listFile.toMutableList()
            newList.add(fileItem)
            newList.toList()
        }
    }

    override fun deleteFileItem(fileItem: FileItem) {
        listFile.update { listFile ->
            val newList = listFile.toMutableList()
            newList.removeIf { it == fileItem }
            newList.toList()
        }
    }

    override fun clearListFileItem() {
        listFile.update {
            emptyList<FileItem>()
        }
    }
}