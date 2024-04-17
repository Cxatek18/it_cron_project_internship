package com.team.itcron.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.itcron.domain.models.Menu
import com.team.itcron.domain.usecase.GetMenuUseCase
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel(val getMenuUseCase: GetMenuUseCase) : ViewModel() {

    private val _isLoadSplash = MutableSharedFlow<Boolean>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val isLoadSplash: SharedFlow<Boolean>
        get() = _isLoadSplash.asSharedFlow()

    private val _menu = MutableSharedFlow<Menu>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val menu: SharedFlow<Menu>
        get() = _menu.asSharedFlow()

    fun getMenu() {
        viewModelScope.launch(CoroutineName("loadMenu")) {
            val requestMenu = getMenuUseCase.getMenu()
            if (requestMenu.isError == null) {
                _isLoadSplash.emit(false)
                _menu.emit(requestMenu)
            } else {
                _isLoadSplash.emit(true)
            }
        }
    }
}