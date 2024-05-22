package com.team.itcron.di

import com.team.itcron.domain.usecase.AddFiltersToListActiveFilterUseCase
import com.team.itcron.domain.usecase.ClearFilterListUseCase
import com.team.itcron.domain.usecase.FilteringCasesUseCase
import com.team.itcron.domain.usecase.FormingListActiveFiltersUseCase
import com.team.itcron.domain.usecase.GetActiveFilterUseCase
import com.team.itcron.domain.usecase.GetCaseDetailUseCase
import com.team.itcron.domain.usecase.GetCaseToListUseCase
import com.team.itcron.domain.usecase.GetFilterToCategoryListUseCase
import com.team.itcron.domain.usecase.GetListReviewUseCase
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

    factory<GetActiveFilterUseCase> {
        GetActiveFilterUseCase(repository = get())
    }

    factory<AddFiltersToListActiveFilterUseCase> {
        AddFiltersToListActiveFilterUseCase(repository = get())
    }

    factory<GetCaseDetailUseCase> {
        GetCaseDetailUseCase(repository = get())
    }

    factory<GetListReviewUseCase> {
        GetListReviewUseCase(repository = get())
    }
}