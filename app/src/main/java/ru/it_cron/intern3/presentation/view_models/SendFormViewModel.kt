package ru.it_cron.intern3.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.it_cron.intern3.domain.models.BudgetInForm
import ru.it_cron.intern3.domain.models.FileItem
import ru.it_cron.intern3.domain.models.FormInfo
import ru.it_cron.intern3.domain.models.FormResponse
import ru.it_cron.intern3.domain.models.PlaceRecognitionInForm
import ru.it_cron.intern3.domain.models.ServiceInForm
import ru.it_cron.intern3.domain.usecase.AddFileItemUseCase
import ru.it_cron.intern3.domain.usecase.ClearListFileItemUseCase
import ru.it_cron.intern3.domain.usecase.CreateListBudgetsUseCase
import ru.it_cron.intern3.domain.usecase.CreateListPlaceRecognitionUseCase
import ru.it_cron.intern3.domain.usecase.CreateListServicesUseCase
import ru.it_cron.intern3.domain.usecase.DeleteFileItemUseCase
import ru.it_cron.intern3.domain.usecase.FormingListActiveServiceUseCase
import ru.it_cron.intern3.domain.usecase.GetActiveBudgetUseCase
import ru.it_cron.intern3.domain.usecase.GetActivePlaceRecognitionUseCase
import ru.it_cron.intern3.domain.usecase.GetListFileItemUseCase
import ru.it_cron.intern3.domain.usecase.PostFormUseCase
import ru.it_cron.intern3.domain.usecase.PostFormWithFilesUseCase
import ru.it_cron.intern3.domain.usecase.SetSelectionBudgetUseCase
import ru.it_cron.intern3.domain.usecase.SetSelectionPlaceRecognitionUseCase
import ru.it_cron.intern3.domain.usecase.SetSelectionServiceUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class SendFormViewModel(
    val createListServicesUseCase: CreateListServicesUseCase,
    val setSelectionServiceUseCase: SetSelectionServiceUseCase,
    val formingListActiveServiceUseCase: FormingListActiveServiceUseCase,
    val createListBudgetsUseCase: CreateListBudgetsUseCase,
    val setSelectionBudgetUseCase: SetSelectionBudgetUseCase,
    val getActiveBudgetUseCase: GetActiveBudgetUseCase,
    val createListPlaceRecognitionUseCase: CreateListPlaceRecognitionUseCase,
    val setSelectionPlaceRecognitionUseCase: SetSelectionPlaceRecognitionUseCase,
    val getActivePlaceRecognitionUseCase: GetActivePlaceRecognitionUseCase,
    val getListFileItemUseCase: GetListFileItemUseCase,
    val addFileItemUseCase: AddFileItemUseCase,
    val deleteFileItemUseCase: DeleteFileItemUseCase,
    val postFormUseCase: PostFormUseCase,
    val postFormWithFilesUseCase: PostFormWithFilesUseCase,
    val clearListFileItemUseCase: ClearListFileItemUseCase
) : ViewModel() {

    private val _serviceListInForm = MutableStateFlow<List<ServiceInForm>>(emptyList())
    val serviceListInForm: StateFlow<List<ServiceInForm>> = _serviceListInForm.asStateFlow()

    private val _serviceActiveListInForm = MutableStateFlow<List<ServiceInForm>>(emptyList())
    val serviceActiveListInForm: StateFlow<List<ServiceInForm>> =
        _serviceActiveListInForm.asStateFlow()

    private val _budgetListInForm = MutableStateFlow<List<BudgetInForm>>(emptyList())
    val budgetListInForm: StateFlow<List<BudgetInForm>> = _budgetListInForm.asStateFlow()

    private val _activeBudget = MutableStateFlow<BudgetInForm?>(null)
    val activeBudget: StateFlow<BudgetInForm?> = _activeBudget.asStateFlow()

    private val _placeRecognitionListInForm =
        MutableStateFlow<List<PlaceRecognitionInForm>>(emptyList())
    val placeRecognitionListInForm: StateFlow<List<PlaceRecognitionInForm>> =
        _placeRecognitionListInForm.asStateFlow()

    private val _activePlaceRecognition = MutableStateFlow<PlaceRecognitionInForm?>(null)
    val activePlaceRecognition: StateFlow<PlaceRecognitionInForm?> =
        _activePlaceRecognition.asStateFlow()

    private val _listFile = MutableStateFlow<List<FileItem>>(emptyList())
    val listFile: StateFlow<List<FileItem>> = _listFile.asStateFlow()

    private val _formResponse = MutableStateFlow<FormResponse?>(null)
    val formResponse: StateFlow<FormResponse?> = _formResponse.asStateFlow()

    private val _isError = Channel<Boolean>(Channel.CONFLATED)
    val isError = _isError.receiveAsFlow()

    private val _formGoodSend = MutableStateFlow<Boolean>(false)
    val formGoodSend: StateFlow<Boolean> = _formGoodSend.asStateFlow()

    fun createListServices() {
        viewModelScope.launch {
            createListServicesUseCase.createListServices()
                .collect { listServicesInForm ->
                    _serviceListInForm.value = listServicesInForm
                }
        }
    }

    fun setSelectionService(serviceInForm: ServiceInForm) {
        setSelectionServiceUseCase.setSelectionService(serviceInForm)
    }

    fun formingListActiveService() {
        viewModelScope.launch {
            formingListActiveServiceUseCase.formingListActiveService()
                .collect { listActiveService ->
                    _serviceActiveListInForm.value = listActiveService
                }
        }
    }

    fun createListBudget() {
        viewModelScope.launch {
            createListBudgetsUseCase.createListBudgets()
                .collect { listBudgetInForm ->
                    _budgetListInForm.value = listBudgetInForm
                }
        }
    }

    fun setSelectionBudget(budgetInForm: BudgetInForm) {
        setSelectionBudgetUseCase.setSelectionBudget(budgetInForm)
    }

    fun getActiveBudget() {
        viewModelScope.launch {
            getActiveBudgetUseCase.getActiveBudget()
                .collect {
                    _activeBudget.value = it
                }
        }
    }

    fun createListPlaceRecognition() {
        viewModelScope.launch {
            createListPlaceRecognitionUseCase.createListPlaceRecognition()
                .collect { listPlaceRecognition ->
                    _placeRecognitionListInForm.value = listPlaceRecognition
                }
        }
    }

    fun setSelectionPlaceRecognition(placeRecognitionInForm: PlaceRecognitionInForm) {
        viewModelScope.launch {
            setSelectionPlaceRecognitionUseCase.setSelectionPlaceRecognition(
                placeRecognitionInForm
            )
        }
    }

    fun getActivePlaceRecognition() {
        viewModelScope.launch {
            getActivePlaceRecognitionUseCase.getActivePlaceRecognition()
                .collect {
                    _activePlaceRecognition.value = it
                }
        }
    }

    fun getListFileItem() {
        viewModelScope.launch {
            getListFileItemUseCase.getListFileItem()
                .collect {
                    _listFile.value = it
                }
        }
    }

    fun addFileItem(fileItem: FileItem) {
        addFileItemUseCase.addFileItem(fileItem)
    }

    fun deleteFileItem(fileItem: FileItem) {
        deleteFileItemUseCase.deleteFileItem(fileItem)
    }

    fun clearListFileItem() {
        clearListFileItemUseCase.clearListFileItem()
    }

    fun postForm(formInfo: FormInfo) {
        viewModelScope.launch {
            postFormUseCase.postForm(formInfo)
                .catch {
                    when (it) {
                        is Exception -> _isError.send(true)
                        else -> _isError.send(false)
                    }
                }
                .collect {
                    if (it?.error == null) {
                        _formResponse.value = it
                        _formGoodSend.value = true
                    } else {
                        _isError.send(true)
                    }
                }
        }
    }

    fun postFormWithFiles(formInfo: FormInfo, listFiles: List<MultipartBody.Part>) {
        viewModelScope.launch {
            postFormWithFilesUseCase.postFormWithFiles(formInfo, listFiles)
                .catch {
                    when (it) {
                        is Exception -> _isError.send(true)
                        else -> _isError.send(false)
                    }
                }
                .collect {
                    if (it?.error == null) {
                        _formResponse.value = it
                        _formGoodSend.value = true
                    } else {
                        _isError.send(true)
                    }
                }
        }
    }

    fun setFalseFormGoodSend() {
        viewModelScope.launch {
            _formGoodSend.value = false
        }
    }
}