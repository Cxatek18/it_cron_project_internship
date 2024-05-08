package com.team.itcron.di

import com.team.itcron.domain.usecase.ClearFilterListUseCase
import com.team.itcron.domain.usecase.FilteringCasesUseCase
import com.team.itcron.domain.usecase.FormingListActiveFiltersUseCase
import com.team.itcron.domain.usecase.GetCaseToListUseCase
import com.team.itcron.domain.usecase.GetFilterToCategoryListUseCase
import com.team.itcron.domain.usecase.GetMenuUseCase
import com.team.itcron.domain.usecase.SetSelectionFilterUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetMenuUseCase> {
        GetMenuUseCase(repository = get())
    }

    factory<GetCaseToListUseCase> {
        GetCaseToListUseCase(repository = get())
    }

    factory<FilteringCasesUseCase> {
        FilteringCasesUseCase(repository = get())
    }

    factory<GetFilterToCategoryListUseCase> {
        GetFilterToCategoryListUseCase(repository = get())
    }

    factory<SetSelectionFilterUseCase> {
        SetSelectionFilterUseCase(repository = get())
    }

    factory<ClearFilterListUseCase> {
        ClearFilterListUseCase(repository = get())
    }

    factory<FormingListActiveFiltersUseCase> {
        FormingListActiveFiltersUseCase(repository = get())
    }
}