package ru.it_cron.intern3.di

import ru.it_cron.intern3.domain.usecase.AddFileItemUseCase
import ru.it_cron.intern3.domain.usecase.AddFiltersToListActiveFilterUseCase
import ru.it_cron.intern3.domain.usecase.ClearFilterListUseCase
import ru.it_cron.intern3.domain.usecase.ClearListFileItemUseCase
import ru.it_cron.intern3.domain.usecase.CreateListBudgetsUseCase
import ru.it_cron.intern3.domain.usecase.CreateListPlaceRecognitionUseCase
import ru.it_cron.intern3.domain.usecase.CreateListServicesUseCase
import ru.it_cron.intern3.domain.usecase.DeleteFileItemUseCase
import ru.it_cron.intern3.domain.usecase.FilteringCasesUseCase
import ru.it_cron.intern3.domain.usecase.FormingListActiveFiltersUseCase
import ru.it_cron.intern3.domain.usecase.FormingListActiveServiceUseCase
import ru.it_cron.intern3.domain.usecase.GetActiveBudgetUseCase
import ru.it_cron.intern3.domain.usecase.GetActiveFilterUseCase
import ru.it_cron.intern3.domain.usecase.GetActivePlaceRecognitionUseCase
import ru.it_cron.intern3.domain.usecase.GetCaseDetailUseCase
import ru.it_cron.intern3.domain.usecase.GetCaseToListUseCase
import ru.it_cron.intern3.domain.usecase.GetFilterToCategoryListUseCase
import ru.it_cron.intern3.domain.usecase.GetListFileItemUseCase
import ru.it_cron.intern3.domain.usecase.GetListRequisiteUseCase
import ru.it_cron.intern3.domain.usecase.GetListReviewUseCase
import ru.it_cron.intern3.domain.usecase.GetMenuUseCase
import ru.it_cron.intern3.domain.usecase.PostFormUseCase
import ru.it_cron.intern3.domain.usecase.PostFormWithFilesUseCase
import ru.it_cron.intern3.domain.usecase.SetSelectionBudgetUseCase
import ru.it_cron.intern3.domain.usecase.SetSelectionFilterUseCase
import ru.it_cron.intern3.domain.usecase.SetSelectionPlaceRecognitionUseCase
import ru.it_cron.intern3.domain.usecase.SetSelectionServiceUseCase
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