package ru.it_cron.intern3.presentation.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Editable
import android.text.Spanned
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import ru.it_cron.intern3.R
import ru.it_cron.intern3.databinding.FragmentSendFormBinding
import ru.it_cron.intern3.domain.models.BudgetInForm
import ru.it_cron.intern3.domain.models.Case
import ru.it_cron.intern3.domain.models.FileItem
import ru.it_cron.intern3.domain.models.FormInfo
import ru.it_cron.intern3.domain.models.PlaceRecognitionInForm
import ru.it_cron.intern3.domain.models.ServiceInForm
import ru.it_cron.intern3.presentation.adapter_delegation.BudgetInFormDiffCallback
import ru.it_cron.intern3.presentation.adapter_delegation.FileItemDiffCallback
import ru.it_cron.intern3.presentation.adapter_delegation.PlaceRecognitionDiffCallback
import ru.it_cron.intern3.presentation.adapter_delegation.ServiceInFormDiffCallback
import ru.it_cron.intern3.presentation.adapter_delegation.budgetInFormDelegate
import ru.it_cron.intern3.presentation.adapter_delegation.listFileDelegate
import ru.it_cron.intern3.presentation.adapter_delegation.placeRecognitionInFormDelegate
import ru.it_cron.intern3.presentation.adapter_delegation.serviceInFormDelegate
import ru.it_cron.intern3.presentation.navigate.NavigateHelper
import ru.it_cron.intern3.presentation.view_models.SendFormViewModel
import java.io.ByteArrayOutputStream
import java.io.InputStream
import kotlin.math.roundToInt

class SendFormFragment : Fragment(), KoinComponent {

    private var _binding: FragmentSendFormBinding? = null
    private val binding: FragmentSendFormBinding
        get() = _binding ?: throw RuntimeException(
            getString(R.string.text_is_null_binding_view_send_form_fragment)
        )

    private val navigateHelper: NavigateHelper by lazy {
        (requireActivity() as? NavigateHelper)
            ?: throw RuntimeException(
                getString(R.string.text_error_no_implements_interface)
            )
    }

    private val sendFormViewModel by viewModel<SendFormViewModel>()

    private val glide by lazy { Glide.with(this) }

    private val previousFragment by lazy {
        requireArguments().getString(EXTRA_PREVIOUS_NAME_FRAGMENT).toString()
    }

    private val adapterServices by lazy {
        AsyncListDifferDelegationAdapter(
            ServiceInFormDiffCallback(),
            serviceInFormDelegate {
                sendFormViewModel.setSelectionService(it)
            }
        )
    }

    private val adapterBudgets by lazy {
        AsyncListDifferDelegationAdapter(
            BudgetInFormDiffCallback(),
            budgetInFormDelegate {
                sendFormViewModel.setSelectionBudget(it)
            }
        )
    }

    private val adapterPlaceRecognition by lazy {
        AsyncListDifferDelegationAdapter(
            PlaceRecognitionDiffCallback(),
            placeRecognitionInFormDelegate {
                sendFormViewModel.setSelectionPlaceRecognition(it)
            }
        )
    }

    private val adapterFiles by lazy {
        AsyncListDifferDelegationAdapter(
            FileItemDiffCallback(),
            listFileDelegate(glide) {
                sendFormViewModel.deleteFileItem(it)
            }
        )
    }

    private lateinit var caseId: String
    private lateinit var caseImage: String
    private lateinit var listCase: List<Case>

    private lateinit var listFormActiveService: List<ServiceInForm>

    private var countActiveCheckBox = 0
    private var activeBudgetForm: BudgetInForm? = null
    private var activePlaceRecognitionForm: PlaceRecognitionInForm? = null

    private var isErrorPhoneField = false
    private var isErrorEmailField = false

    private var listFiles: List<FileItem> = emptyList()


    private val requestPermissionLauncherImage = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(
                requireContext(),
                TEXT_NO_PERMISSION_CAMERA,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private val cameraResultLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            saveImageToMediaStore(it)
        }
    }

    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                displayFileInfo(it)
            }
        }
    }

    // ****** lifecycle *****
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        listeningOnClickBtnBack()
        listeningOnBackPressed()
        listeningOnClickCompanyPhone()
        sendFormViewModel.createListServices()
        sendFormViewModel.createListBudget()
        sendFormViewModel.createListPlaceRecognition()
        binding.listServices.adapter = adapterServices
        binding.listBudgets.adapter = adapterBudgets
        binding.listFile.adapter = adapterFiles
        binding.listPlaceRecognition.adapter = adapterPlaceRecognition
        observeViewModel()
        listeningEditText(
            editText = binding.editTextTask,
            hintEditText = binding.hintEditTextTask,
            textHint = R.string.hint_task_in_form,
            textEmptyField = binding.textEmptyTask
        )
        listeningEditText(
            editText = binding.editTextName,
            hintEditText = binding.hintEditTextName,
            textHint = R.string.hint_name_in_form,
            textEmptyField = binding.textEmptyName
        )
        listeningEditText(
            editText = binding.editTextCompany,
            hintEditText = binding.hintEditTextCompany,
            textHint = R.string.hint_company_in_form,
            textEmptyField = binding.textEmptyCompany
        )
        listeningEditTextEmail()
        listeningEditPhone()
        listeningChangeEditPhone()
        listeningOnClickCheckBox(checkBox = binding.checkPersonalData)
        listeningOnClickCheckBox(checkBox = binding.checkPrivacyPolicy)
        changeTextPersonalData()
        changeTextPrivacyPolicy()
        listeningOnClickBtnDownloadFile()
        listeningOnClickBtnSendForm()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sendFormViewModel.clearListFileItem()
        _binding = null
    }
    // ****** lifecycle *****

    private fun observeViewModel() {
        lifecycleScope.launch {
            sendFormViewModel.serviceListInForm.flowWithLifecycle(lifecycle)
                .collect { listServiceInForm ->
                    adapterServices.items = listServiceInForm
                    sendFormViewModel.formingListActiveService()
                }
        }

        lifecycleScope.launch {
            sendFormViewModel.serviceActiveListInForm.flowWithLifecycle(lifecycle)
                .collect { listActiveService ->
                    listFormActiveService = listActiveService
                }
        }

        lifecycleScope.launch {
            sendFormViewModel.budgetListInForm.flowWithLifecycle(lifecycle)
                .collect { listBudgetInForm ->
                    adapterBudgets.items = listBudgetInForm
                    sendFormViewModel.getActiveBudget()
                }
        }

        lifecycleScope.launch {
            sendFormViewModel.activeBudget.flowWithLifecycle(lifecycle)
                .collect { activeBudget ->
                    activeBudgetForm = activeBudget
                }
        }

        lifecycleScope.launch {
            sendFormViewModel.placeRecognitionListInForm.flowWithLifecycle(lifecycle)
                .collect { listPlaceRecognitionInForm ->
                    adapterPlaceRecognition.items = listPlaceRecognitionInForm
                    sendFormViewModel.getActivePlaceRecognition()
                }
        }

        lifecycleScope.launch {
            sendFormViewModel.activePlaceRecognition.flowWithLifecycle(lifecycle)
                .collect { activePlaceRecognition ->
                    activePlaceRecognitionForm = activePlaceRecognition
                }
        }

        lifecycleScope.launch {
            sendFormViewModel.listFile.flowWithLifecycle(lifecycle)
                .collect { listFileItem ->
                    with(binding) {
                        if (listFileItem.isNotEmpty()) {
                            listFile.visibility = View.VISIBLE
                            adapterFiles.items = listFileItem
                            if (listFileItem.size == MAX_COUNT_FILE) {
                                buttonDownload.isClickable = false
                                buttonDownload.setBackgroundDrawable(
                                    ContextCompat.getDrawable(
                                        requireActivity(),
                                        R.drawable.background_btn_download_file_no_active
                                    )
                                )
                            } else {
                                buttonDownload.isClickable = true
                                buttonDownload.setBackgroundDrawable(
                                    ContextCompat.getDrawable(
                                        requireActivity(),
                                        R.drawable.background_btn_download_file
                                    )
                                )
                            }
                        } else {
                            listFile.visibility = View.GONE
                        }
                    }
                    listFiles = listFileItem
                }
        }

        lifecycleScope.launch {
            sendFormViewModel.formResponse.flowWithLifecycle(lifecycle)
                .collect { formResponse ->
                    Log.d("SendFormFragment", formResponse.toString())
                }
        }

        lifecycleScope.launch {
            sendFormViewModel.isError.flowWithLifecycle(lifecycle)
                .collect { error ->
                    if (error) {
                        openErrorFormDialog()
                    }
                    sendFormViewModel.setFalseFormGoodSend()
                }
        }

        lifecycleScope.launch {
            sendFormViewModel.formGoodSend.flowWithLifecycle(lifecycle)
                .collect { formSendGood ->
                    if (formSendGood) {
                        openGoodFormDialog()
                    }
                    sendFormViewModel.setFalseFormGoodSend()
                }
        }
    }

    private fun listeningOnClickBtnSendForm() {
        binding.btnSendForm.setOnClickListener {
            if (!isErrorPhoneField && !isErrorEmailField) {
                if (!checkingSampleBlocks() && !checkEditTextFields()) {
                    with(binding) {
                        if (listFiles.isEmpty()) {
                            sendFormViewModel.postForm(
                                FormInfo(
                                    services = formingStringServices(),
                                    budget = activeBudgetForm?.title ?: "none",
                                    description = binding.editTextTask.text.toString(),
                                    contactName = binding.editTextName.text.toString(),
                                    contactCompany = binding.editTextCompany.text.toString(),
                                    contactEmail = binding.editTextEmail.text.toString(),
                                    contactPhone = binding.editTextPhone.text.toString(),
                                    requestFrom = activePlaceRecognitionForm?.title ?: "none"
                                )
                            )
                        } else {
                            sendFormViewModel.postFormWithFiles(
                                FormInfo(
                                    services = formingStringServices(),
                                    budget = activeBudgetForm?.title ?: "none",
                                    description = binding.editTextTask.text.toString(),
                                    contactName = binding.editTextName.text.toString(),
                                    contactCompany = binding.editTextCompany.text.toString(),
                                    contactEmail = binding.editTextEmail.text.toString(),
                                    contactPhone = binding.editTextPhone.text.toString(),
                                    requestFrom = activePlaceRecognitionForm?.title ?: "none",
                                ),
                                convertListFilesToListMultipartBody(listFiles)
                            )
                        }
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    TEXT_ERROR_FIELD_PHONE_OR_EMAIL,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun convertListFilesToListMultipartBody(
        listFiles: List<FileItem>
    ): List<MultipartBody.Part> {
        val listMultipartBody = mutableListOf<MultipartBody.Part>()
        listFiles.forEach { fileItem ->
            if (fileItem.nameFile == null) {
                val stream = ByteArrayOutputStream()
                fileItem.bitmapFile?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray = stream.toByteArray()
                val requestBody = byteArray.toRequestBody("image/jpeg".toMediaType())
                listMultipartBody.add(
                    MultipartBody.Part.createFormData(
                        "file",
                        "filename.jpg",
                        requestBody
                    )
                )
            } else {
                val inputStream: InputStream? =
                    fileItem.uriFile?.let { uriToFile(it, requireActivity().contentResolver) }
                val mediaType = "multipart/form-data".toMediaType()
                val requestBody = inputStream?.readBytes()?.toRequestBody(mediaType)
                requestBody?.let {
                    listMultipartBody.add(
                        MultipartBody.Part.createFormData(
                            fileItem.nameFile.toString(),
                            fileItem.nameFile,
                            it
                        )
                    )
                }

            }
        }
        return listMultipartBody
    }

    fun uriToFile(uri: Uri, contentResolver: ContentResolver): InputStream? {
        return contentResolver.openInputStream(uri)
    }

    private fun openGoodFormDialog() {
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        val goodSendFormDialog = layoutInflater.inflate(
            R.layout.good_send_form_dialog, null
        )
        val dialog = BottomSheetDialog(requireContext())
        dialog.window?.apply {
            setContentView(goodSendFormDialog)
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            setBackgroundDrawable(
                ContextCompat.getDrawable(requireContext(), R.color.color_main)
            )
        }
        dialog.setCancelable(true)
        dialog.show()
        val btnOk = goodSendFormDialog.findViewById<AppCompatButton>(
            R.id.btn_ok
        )
        val phoneCompany = goodSendFormDialog
            .findViewById<AppCompatTextView>(
                R.id.phone_company
            )
        btnOk.setOnClickListener {
            dialog.dismiss()
        }
        phoneCompany.setOnClickListener {
            callPhoneCompany()
        }
    }

    private fun openErrorFormDialog() {
        val errorFormSendDialog = layoutInflater.inflate(
            R.layout.error_form_send_dialog, null
        )
        val dialog = BottomSheetDialog(requireContext())
        dialog.window?.apply {
            setContentView(errorFormSendDialog)
            setBackgroundDrawable(
                ContextCompat.getDrawable(requireContext(), R.color.color_transparent)
            )
        }
        dialog.setCancelable(true)
        dialog.show()
        val phoneCompany = errorFormSendDialog
            .findViewById<AppCompatTextView>(
                R.id.phone_company
            )
        val btnRepeat = errorFormSendDialog
            .findViewById<AppCompatButton>(
                R.id.btn_repeat
            )
        phoneCompany.setOnClickListener {
            callPhoneCompany()
        }
        btnRepeat.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun formingStringServices(): String {
        var resultString = ""
        listFormActiveService.forEach {
            resultString += "${it.title} "
        }
        return resultString.trim()
    }

    private fun checkingSampleBlocks(): Boolean {
        var isError = false
        if (listFormActiveService.isEmpty()) {
            isError = true
        }
        if (activeBudgetForm == null) {
            isError = true
        }
        if (activePlaceRecognitionForm == null) {
            isError = true
        }
        if (isError) {
            Toast.makeText(
                requireContext(),
                TEXT_SELECT_BLOCKS,
                Toast.LENGTH_LONG
            ).show()
        }
        return isError
    }

    private fun checkEditTextFields(): Boolean {
        with(binding) {
            val mapTextFields = mapOf<AppCompatEditText, AppCompatTextView>(
                editTextTask to textEmptyTask,
                editTextName to textEmptyName,
                editTextCompany to textEmptyCompany,
                editTextEmail to textEmptyEmail,
                editTextPhone to textEmptyPhone
            )
            var isError = false
            for ((editText, textView) in mapTextFields) {
                if (editText.text?.isEmpty() == true) {
                    textView.visibility = View.VISIBLE
                    editText.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.color_text_error_form
                        )
                    )
                    isError = true
                }
            }
            return isError
        }
    }

    private fun listeningOnClickCheckBox(checkBox: CheckBox) {
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                countActiveCheckBox += 1
            } else {
                countActiveCheckBox -= 1
            }
            changeActiveBtnSendForm()
        }
    }

    private fun changeActiveBtnSendForm() {
        with(binding) {
            if (
                countActiveCheckBox == COUNT_ACTIVE_CHECK_BOX
            ) {
                btnSendForm.isClickable = true
                btnSendForm.isEnabled = true
                btnSendForm.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.background_btn_send_request_case_detail
                    )
                )
                btnSendForm.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.white
                    )
                )
            } else {
                btnSendForm.isClickable = false
                btnSendForm.isEnabled = false
                btnSendForm.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.background_btn_send_request_no_active
                    )
                )
                btnSendForm.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.color_text_btn_no_active
                    )
                )
            }
        }
    }

    private fun listeningOnClickBtnDownloadFile() {
        binding.buttonDownload.setOnClickListener {
            val downloadFileDialog = layoutInflater.inflate(
                R.layout.download_file_dialog, null
            )
            val dialog = BottomSheetDialog(requireContext())
            dialog.window?.apply {
                setContentView(downloadFileDialog)
                setBackgroundDrawable(
                    ContextCompat.getDrawable(requireContext(), R.color.color_transparent)
                )
            }
            dialog.setCancelable(true)
            dialog.show()
            val btnCancel = downloadFileDialog
                .findViewById<AppCompatButton>(
                    R.id.btn_cancel
                )
            val btnDownloadImage = downloadFileDialog
                .findViewById<AppCompatButton>(
                    R.id.btn_download_photo
                )
            val btnDownloadDocument = downloadFileDialog
                .findViewById<AppCompatButton>(
                    R.id.btn_download_document
                )
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnDownloadImage.setOnClickListener {
                requestPermissionLauncherImage.launch(Manifest.permission.CAMERA)
                dialog.dismiss()
            }
            btnDownloadDocument.setOnClickListener {
                openFilePicker()
                dialog.dismiss()
            }
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        filePickerLauncher.launch(intent)
    }

    private fun displayFileInfo(uri: Uri) {
        val cursor = requireActivity().contentResolver.query(
            uri, null, null, null, null
        )
        cursor?.use {
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val displayName = if (nameIndex != -1) {
                    cursor.getString(nameIndex)
                } else {
                    TEXT_NO_NAME_FILE
                }
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                val size = if (sizeIndex != -1) {
                    cursor.getLong(sizeIndex)
                } else {
                    0L
                }
                val type = requireActivity()
                    .contentResolver.getType(uri) ?: TEXT_NO_TYPE_FILE
                val fileSizeMb = (((size / 1024) / 1024) * 100.0).roundToInt() / 100.0
                if (fileSizeMb > 15) {
                    openDialogErrorMaxSizeFile()
                } else {
                    sendFormViewModel.addFileItem(
                        FileItem(
                            nameFile = displayName,
                            sizeFile = fileSizeMb.toString(),
                            typeFile = type.split("/")[1],
                            uriFile = uri
                        )
                    )
                }
            }
        }
        sendFormViewModel.getListFileItem()
    }

    private fun openDialogErrorMaxSizeFile() {
        val errorSizeFileDialog = layoutInflater.inflate(
            R.layout.max_size_file_dialog, null
        )
        val dialog = BottomSheetDialog(requireContext())
        dialog.window?.apply {
            setContentView(errorSizeFileDialog)
            setBackgroundDrawable(
                ContextCompat.getDrawable(requireContext(), R.color.color_transparent)
            )
        }
        dialog.setCancelable(true)
        dialog.show()
        val btnRepeatDownloadFile = errorSizeFileDialog
            .findViewById<AppCompatButton>(
                R.id.btn_repeat_download_file
            )
        btnRepeatDownloadFile.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun openCamera() {
        cameraResultLauncher.launch(null)
    }

    private fun saveImageToMediaStore(bitmap: Bitmap) {
        try {
            val values = ContentValues().apply {
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    "image_it_cron_${System.currentTimeMillis()}.jpg"
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }
            requireContext()
                .contentResolver
                .insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
                )?.also { newImageUri ->
                    requireContext().contentResolver
                        .openOutputStream(newImageUri)?.use { outputStream ->
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        }
                }
            sendFormViewModel.addFileItem(
                FileItem(
                    bitmapFile = bitmap
                )
            )
            sendFormViewModel.getListFileItem()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun changeTextPrivacyPolicy() {
        val textView = binding.textPrivacyPolicy
        textView.movementMethod = LinkMovementMethod.getInstance()
        val link = getString(R.string.text_privacy_policy_link)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                createDialogWindowPrivacyPolicy(
                    R.layout.privacy_policy_dialog,
                    R.id.btn_privacy_policy
                )
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                val typeface = ResourcesCompat.getFont(
                    requireContext(), R.font.gothampro_medium
                )
                ds.typeface = typeface
                ds.isUnderlineText = false
                ds.textSize = SIZE_SPANNABLE_TEXT
                ds.color = ContextCompat.getColor(requireActivity(), R.color.color_main_second)
            }
        }
        val spannable = buildSpannedString {
            append(getString(R.string.text_privacy_policy))
            append(" ")
            append(link, clickableSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        textView.text = spannable
    }

    private fun changeTextPersonalData() {
        val textView = binding.textPersonalData
        textView.movementMethod = LinkMovementMethod.getInstance()
        val link = getString(R.string.text_personal_data_link)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                createDialogWindowPrivacyPolicy(
                    R.layout.personal_data_dialog,
                    R.id.btn_personal_data
                )
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                val typeface = ResourcesCompat.getFont(
                    requireContext(), R.font.gothampro_medium
                )
                ds.typeface = typeface
                ds.isUnderlineText = false
                ds.textSize = SIZE_SPANNABLE_TEXT
                ds.color = ContextCompat.getColor(requireActivity(), R.color.color_main_second)
            }
        }
        val spannable = buildSpannedString {
            append(getString(R.string.text_personal_data))
            append(" ")
            append(link, clickableSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        textView.text = spannable
    }

    private fun createDialogWindowPrivacyPolicy(dialogLayout: Int, btnOkLayout: Int) {
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        val privacyPolicyDialog = layoutInflater.inflate(
            dialogLayout, null
        )
        val dialog = BottomSheetDialog(requireContext())
        dialog.window?.apply {
            setContentView(privacyPolicyDialog)
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            setBackgroundDrawable(
                ContextCompat.getDrawable(requireContext(), R.color.color_main)
            )
        }
        dialog.setCancelable(true)
        dialog.show()
        val btnOk = privacyPolicyDialog.findViewById<AppCompatButton>(
            btnOkLayout
        )
        btnOk.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun callPhoneCompany() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CODE_CALL
            )
        } else {
            val intent = Intent(Intent.ACTION_CALL)
            val number = getString(R.string.text_company_phone)
            intent.data = Uri.parse("tel:$number")
            startActivity(intent)
        }
    }

    private fun listeningOnClickCompanyPhone() {
        binding.textPhoneCompany.setOnClickListener {
            callPhoneCompany()
        }
    }

    private fun listeningOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback {
            definingFragmentSwitching()
        }
    }

    private fun listeningOnClickBtnBack() {
        binding.btnBack.setOnClickListener {
            definingFragmentSwitching()
        }
    }

    private fun definingFragmentSwitching() {
        when (previousFragment) {
            MainFragment.NAME_FRAGMENT -> navigateHelper.navigateTo(
                MainFragment.newInstance()
            )

            CompanyFragment.NAME_FRAGMENT -> navigateHelper.navigateTo(
                CompanyFragment.newInstance()
            )

            CaseDetailFragment.NAME_FRAGMENT -> navigateHelper.navigateTo(
                CaseDetailFragment.newInstance(
                    caseId,
                    caseImage,
                    ArrayList(listCase)
                )
            )

            ContactFragment.NAME_FRAGMENT -> navigateHelper.navigateTo(
                ContactFragment.newInstance()
            )

            ReviewListFragment.NAME_FRAGMENT -> navigateHelper.navigateTo(
                ReviewListFragment.newInstance()
            )
        }
    }

    private fun listeningEditText(
        editText: AppCompatEditText,
        hintEditText: AppCompatTextView,
        textHint: Int,
        textEmptyField: AppCompatTextView
    ) {
        editText.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                hintEditText.visibility = View.VISIBLE
                editText.hint = ""
                editText.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.color_main_second)
                )
            } else {
                if (editText.text?.isEmpty() == true) {
                    hintEditText.visibility = View.GONE
                    editText.hint = getString(
                        textHint
                    )
                } else {
                    textEmptyField.visibility = View.GONE
                }
                editText.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.color_text_hint)
                )
            }
        }
    }

    private fun listeningChangeEditPhone() {
        with(binding) {
            editTextPhone.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (editTextPhone.text?.toString()?.length != GOOD_LENGTH_PHONE) {
                        editTextPhone.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.color_text_error_form
                            )
                        )
                        textEmptyPhone.visibility = View.VISIBLE
                        textEmptyPhone.text = getString(
                            R.string.text_error_length_phone
                        )
                        editTextPhone.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.error_icon_phone,
                            ),
                            null
                        )
                        isErrorPhoneField = true
                    } else {
                        textEmptyPhone.visibility = View.GONE
                        editTextPhone.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(requireContext(), R.color.color_text_hint)
                        )
                        editTextPhone.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ok_icon_phone,
                            ),
                            null
                        )
                        isErrorPhoneField = false
                    }
                }
            })
        }
    }

    private fun listeningEditPhone() {
        with(binding) {
            editTextPhone.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    hintEditTextPhone.visibility = View.VISIBLE
                    editTextPhone.hint = ""
                    editTextPhone.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(requireContext(), R.color.color_main_second)
                    )
                    textPhoneCompany.visibility = View.GONE
                    textPhoneCompany.text = getString(
                        R.string.text_empty_task
                    )
                } else {
                    if (editTextPhone.text?.isEmpty() == true) {
                        hintEditTextPhone.visibility = View.GONE
                        editTextPhone.hint = getString(
                            R.string.hint_phone_in_form
                        )
                        textEmptyPhone.visibility = View.GONE
                        editTextPhone.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(requireContext(), R.color.color_text_hint)
                        )
                        editTextPhone.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            null,
                            null
                        )
                    } else {
                        if (editTextPhone.text?.toString()?.length == GOOD_LENGTH_PHONE) {
                            textEmptyPhone.visibility = View.GONE
                            editTextPhone.backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.color_text_hint)
                            )
                            editTextPhone.setCompoundDrawablesWithIntrinsicBounds(
                                null,
                                null,
                                null,
                                null
                            )
                        }
                    }
                }
            }
        }
    }

    private fun checkEditTextEmail(
        editTextEmail: AppCompatEditText,
        textEmptyEmail: AppCompatTextView
    ) {
        val countDogInEmail = editTextEmail.text.toString().count {
            it == '@'
        }
        if (countDogInEmail == 0 || countDogInEmail > 1) {
            textEmptyEmail.visibility = View.VISIBLE
            textEmptyEmail.text = getString(
                R.string.text_error_email_in_one_dog
            )
            editTextEmail.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color_text_error_form
                )
            )
            isErrorEmailField = true
        } else {
            editTextEmail.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.color_text_hint)
            )
            textEmptyEmail.visibility = View.GONE
            textEmptyEmail.text = getString(
                R.string.text_empty_task
            )
            isErrorEmailField = false
        }
    }

    private fun listeningEditTextEmail() {
        with(binding) {
            editTextEmail.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    hintEditTextEmail.visibility = View.VISIBLE
                    editTextEmail.hint = ""
                    editTextEmail.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(requireContext(), R.color.color_main_second)
                    )
                    textEmptyEmail.visibility = View.GONE
                    textEmptyEmail.text = getString(
                        R.string.text_empty_task
                    )
                } else {
                    if (editTextEmail.text?.isEmpty() == true) {
                        hintEditTextEmail.visibility = View.GONE
                        editTextEmail.hint = getString(
                            R.string.hint_email_in_form
                        )
                        editTextEmail.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(requireContext(), R.color.color_text_hint)
                        )
                    } else if (editTextEmail.text?.isNotEmpty() == true) {
                        checkEditTextEmail(editTextEmail, textEmptyEmail)
                    }
                }
            }
        }
    }

    private fun parseArgs() {
        val args = requireArguments()
        caseId = args.getString(EXTRA_CASE_ID).toString()
        caseImage = args.getString(EXTRA_CASE_IMAGE).toString()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            args.getParcelableArrayList(
                EXTRA_CASES,
                Case::class.java
            )?.let {
                listCase = it.toList()
            }
        } else {
            args.getParcelableArrayList<Case>(
                EXTRA_CASES
            )?.let {
                listCase = it.toList()
            }
        }
    }

    companion object {
        private const val EXTRA_PREVIOUS_NAME_FRAGMENT = "name_page"
        private const val EXTRA_CASE_ID = "case_id"
        private const val EXTRA_CASE_IMAGE = "case_image"
        private const val EXTRA_CASES = "cases"
        private const val REQUEST_CODE_CALL = 10
        private const val GOOD_LENGTH_PHONE = 17
        private const val TEXT_NO_PERMISSION_CAMERA = "Нету разрешения на использованиe камеры"
        private const val TEXT_NO_NAME_FILE = "Неизвестное имя"
        private const val TEXT_NO_TYPE_FILE = "Неизвестный тип"
        private const val SIZE_SPANNABLE_TEXT = 50F
        private const val COUNT_ACTIVE_CHECK_BOX = 2
        private const val TEXT_SELECT_BLOCKS = "Разделы - Услуги, " +
                "Бюджет, Откуда узнали, должны быть выбраны."
        private const val TEXT_ERROR_FIELD_PHONE_OR_EMAIL = "Ошибки в полях - Телефон или Email."
        private const val MAX_COUNT_FILE = 5

        fun newInstance(
            fragmentName: String,
            caseId: String,
            caseImage: String,
            cases: ArrayList<Case>
        ): SendFormFragment {
            return SendFormFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_PREVIOUS_NAME_FRAGMENT, fragmentName)
                    putString(EXTRA_CASE_ID, caseId)
                    putString(EXTRA_CASE_IMAGE, caseImage)
                    putParcelableArrayList(EXTRA_CASES, cases)
                }
            }
        }

        fun newInstance(fragmentName: String): SendFormFragment {
            return SendFormFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_PREVIOUS_NAME_FRAGMENT, fragmentName)
                }
            }
        }
    }
}