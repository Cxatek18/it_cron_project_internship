package com.team.itcron.di

import com.team.itcron.domain.usecase.GetMenuUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetMenuUseCase> {
        GetMenuUseCase(repository = get())
    }
}