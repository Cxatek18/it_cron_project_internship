package ru.it_cron.intern3.domain.models

import android.graphics.Bitmap
import android.net.Uri

data class FileItem(
    var nameFile: String? = null,
    var sizeFile: String? = null,
    var typeFile: String? = null,
    var bitmapFile: Bitmap? = null,
    var uriFile: Uri? = null
)
