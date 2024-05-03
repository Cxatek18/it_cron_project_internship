package com.team.itcron.di

import com.team.itcron.domain.usecase.GetCaseToListUseCase
import com.team.itcron.domain.usecase.GetFilterToCategoryListUseCase
import com.team.itcron.domain.usecase.GetMenuUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetMenuUseCase> {
        GetMenuUseCase(repository = get())
    }

    factory<GetCaseToListUseCase> {
        GetCaseToListUseCase(repository = get())
    }

    factory<GetFilterToCategoryListUseCase> {
        GetFilterToCategoryListUseCase(repository = get())
    }
}