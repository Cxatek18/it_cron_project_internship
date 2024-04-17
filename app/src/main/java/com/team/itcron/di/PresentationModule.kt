package com.team.itcron.di

import com.team.itcron.presentation.view_models.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel<MainViewModel> {
        MainViewModel(getMenuUseCase = get())
    }
}