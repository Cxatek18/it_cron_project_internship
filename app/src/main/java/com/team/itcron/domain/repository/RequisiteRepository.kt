package com.team.itcron.domain.repository

import com.team.itcron.domain.models.Requisite
import kotlinx.coroutines.flow.Flow

interface RequisiteRepository {
    fun getListRequisite(): Flow<List<Requisite>>
}