package com.team.itcron.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.itcron.domain.models.Menu
import com.team.itcron.domain.usecase.GetMenuUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(val getMenuUseCase: GetMenuUseCase) : ViewModel() {

    private val _isLoadSplash = MutableStateFlow<Boolean>(true)
    val isLoadSplash: StateFlow<Boolean> = _isLoadSplash.asStateFlow()

    private val _menu = MutableStateFlow<Menu?>(null)
    val menu: StateFlow<Menu?> = _menu.asStateFlow()

    fun getMenu() {
        viewModelScope.launch {
            val requestMenu = getMenuUseCase.getMenu()
            if (requestMenu.isError == null) {
                _isLoadSplash.value = false
                _menu.value = requestMenu
            } else {
                _isLoadSplash.value = true
            }
        }
    }
}