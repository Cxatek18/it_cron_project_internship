package ru.it_cron.intern3.domain.repository

import ru.it_cron.intern3.domain.models.Requisite
import kotlinx.coroutines.flow.Flow

interface RequisiteRepository {
    fun getListRequisite(): Flow<List<Requisite>>
}