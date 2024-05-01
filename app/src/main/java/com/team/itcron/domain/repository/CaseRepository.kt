package com.team.itcron.domain.repository

import com.team.itcron.domain.models.CaseToList

interface CaseRepository {
    suspend fun getCaseToList(): CaseToList
}