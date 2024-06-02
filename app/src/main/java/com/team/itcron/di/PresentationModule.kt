package com.team.itcron.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.team.itcron.presentation.activity.NetworkChecker
import com.team.itcron.presentation.view_models.CaseDetailViewModel
import com.team.itcron.presentation.view_models.CompanyViewModel
import com.team.itcron.presentation.view_models.ContactViewModel
import com.team.itcron.presentation.view_models.ListCaseViewModel
import com.team.itcron.presentation.view_models.ListFilterViewModel
import com.team.itcron.presentation.view_models.MainViewModel
import com.team.itcron.presentation.view_models.SendFormViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel<MainViewModel> {
        MainViewModel(getMenuUseCase = get())
    }

    viewModel<ListCaseViewModel> {
        ListCaseViewModel(
            getCaseToListUseCase = get(),
            filteringCasesUseCase = get(),
            getActiveFilterUseCase = get(),
            addFiltersToListActiveFilterUseCase = get()
        )
    }

    viewModel<ListFilterViewModel> {
        ListFilterViewModel(
            getFilterToCategoryListUseCase = get(),
            setSelectionFilterUseCase = get(),
            clearFilterListUseCase = get(),
            formingListActiveFiltersUseCase = get()
        )
    }

    viewModel<CaseDetailViewModel> {
        CaseDetailViewModel(
            getCaseDetailUseCase = get()
        )
    }

    viewModel<CompanyViewModel> {
        CompanyViewModel(
            getListReviewUseCase = get()
        )
    }

    viewModel<SendFormViewModel> {
        SendFormViewModel(
            createListServicesUseCase = get(),
            setSelectionServiceUseCase = get(),
            formingListActiveServiceUseCase = get(),
            createListBudgetsUseCase = get(),
            setSelectionBudgetUseCase = get(),
            getActiveBudgetUseCase = get(),
            createListPlaceRecognitionUseCase = get(),
            setSelectionPlaceRecognitionUseCase = get(),
            getActivePlaceRecognitionUseCase = get(),
            getListFileItemUseCase = get(),
            addFileItemUseCase = get(),
            deleteFileItemUseCase = get(),
            postFormUseCase = get(),
            postFormWithFilesUseCase = get(),
            clearListFileItemUseCase = get()
        )
    }

    viewModel<ContactViewModel> {
        ContactViewModel(
            getListRequisiteUseCase = get()
        )
    }

    single<Cicerone<Router>> {
        Cicerone.create()
    }

    single<NavigatorHolder> {
        get<Cicerone<Router>>().getNavigatorHolder()
    }

    single<Router> {
        get<Cicerone<Router>>().router
    }

    factory<NetworkChecker> {
        NetworkChecker(context = get())
    }
}