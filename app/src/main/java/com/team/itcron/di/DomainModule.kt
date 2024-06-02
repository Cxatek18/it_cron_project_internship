package com.team.itcron.di

import com.team.itcron.domain.usecase.AddFileItemUseCase
import com.team.itcron.domain.usecase.AddFiltersToListActiveFilterUseCase
import com.team.itcron.domain.usecase.ClearFilterListUseCase
import com.team.itcron.domain.usecase.ClearListFileItemUseCase
import com.team.itcron.domain.usecase.CreateListBudgetsUseCase
import com.team.itcron.domain.usecase.CreateListPlaceRecognitionUseCase
import com.team.itcron.domain.usecase.CreateListServicesUseCase
import com.team.itcron.domain.usecase.DeleteFileItemUseCase
import com.team.itcron.domain.usecase.FilteringCasesUseCase
import com.team.itcron.domain.usecase.FormingListActiveFiltersUseCase
import com.team.itcron.domain.usecase.FormingListActiveServiceUseCase
import com.team.itcron.domain.usecase.GetActiveBudgetUseCase
import com.team.itcron.domain.usecase.GetActiveFilterUseCase
import com.team.itcron.domain.usecase.GetActivePlaceRecognitionUseCase
import com.team.itcron.domain.usecase.GetCaseDetailUseCase
import com.team.itcron.domain.usecase.GetCaseToListUseCase
import com.team.itcron.domain.usecase.GetFilterToCategoryListUseCase
import com.team.itcron.domain.usecase.GetListFileItemUseCase
import com.team.itcron.domain.usecase.GetListRequisiteUseCase
import com.team.itcron.domain.usecase.GetListReviewUseCase
import com.team.itcron.domain.usecase.GetMenuUseCase
import com.team.itcron.domain.usecase.PostFormUseCase
import com.team.itcron.domain.usecase.PostFormWithFilesUseCase
import com.team.itcron.domain.usecase.SetSelectionBudgetUseCase
import com.team.itcron.domain.usecase.SetSelectionFilterUseCase
import com.team.itcron.domain.usecase.SetSelectionPlaceRecognitionUseCase
import com.team.itcron.domain.usecase.SetSelectionServiceUseCase
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

    factory<CreateListServicesUseCase> {
        CreateListServicesUseCase(repository = get())
    }

    factory<SetSelectionServiceUseCase> {
        SetSelectionServiceUseCase(repository = get())
    }

    factory<FormingListActiveServiceUseCase> {
        FormingListActiveServiceUseCase(repository = get())
    }

    factory<CreateListBudgetsUseCase> {
        CreateListBudgetsUseCase(repository = get())
    }

    factory<SetSelectionBudgetUseCase> {
        SetSelectionBudgetUseCase(repository = get())
    }

    factory<GetActiveBudgetUseCase> {
        GetActiveBudgetUseCase(repository = get())
    }

    factory<CreateListPlaceRecognitionUseCase> {
        CreateListPlaceRecognitionUseCase(repository = get())
    }

    factory<SetSelectionPlaceRecognitionUseCase> {
        SetSelectionPlaceRecognitionUseCase(repository = get())
    }

    factory<GetActivePlaceRecognitionUseCase> {
        GetActivePlaceRecognitionUseCase(repository = get())
    }

    factory<GetListFileItemUseCase> {
        GetListFileItemUseCase(repository = get())
    }

    factory<AddFileItemUseCase> {
        AddFileItemUseCase(repository = get())
    }

    factory<DeleteFileItemUseCase> {
        DeleteFileItemUseCase(repository = get())
    }

    factory<PostFormUseCase> {
        PostFormUseCase(repository = get())
    }

    factory<PostFormWithFilesUseCase> {
        PostFormWithFilesUseCase(repository = get())
    }

    factory<ClearListFileItemUseCase> {
        ClearListFileItemUseCase(repository = get())
    }

    factory<GetListRequisiteUseCase> {
        GetListRequisiteUseCase(repository = get())
    }
}