package com.team.itcron.presentation.adapter_delegation

import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.team.itcron.R
import com.team.itcron.databinding.FileItemBinding
import com.team.itcron.domain.models.FileItem

fun listFileDelegate(
    glide: RequestManager,
    clickDeleteBtn: (fileItem: FileItem) -> Unit
) = adapterDelegateViewBinding<FileItem, FileItem, FileItemBinding>(
    { layoutInflater, root -> FileItemBinding.inflate(layoutInflater, root, false) }
) {
    with(binding) {
        btnDeleteFile.setOnClickListener {
            clickDeleteBtn(item)
        }
    }

    bind {
        with(binding) {
            if (item.nameFile == null) {
                fileType.visibility = View.INVISIBLE
                fileName.visibility = View.INVISIBLE
                fileSize.visibility = View.INVISIBLE
                glide.load(item.bitmapFile).into(imageBackground)
            } else {
                fileType.text = item.typeFile
                fileName.text = item.nameFile
                fileSize.text = "${item.sizeFile} MB"
                when (item.typeFile) {
                    "doc" -> {
                        fileType.setBackgroundColor(
                            ContextCompat.getColor(fileType.context, R.color.color_doc_file)
                        )
                    }

                    "pdf" -> {
                        fileType.setBackgroundColor(
                            ContextCompat.getColor(fileType.context, R.color.color_pdf_file)
                        )
                    }

                    "xls" -> {
                        fileType.setBackgroundColor(
                            ContextCompat.getColor(fileType.context, R.color.color_xls_file)
                        )
                    }

                    "txt" -> {
                        fileType.setBackgroundColor(
                            ContextCompat.getColor(fileType.context, R.color.color_txt_file)
                        )
                    }

                    "zip" -> {
                        fileType.setBackgroundColor(
                            ContextCompat.getColor(fileType.context, R.color.color_zip_file)
                        )
                    }

                    "img" -> {
                        fileType.setBackgroundColor(
                            ContextCompat.getColor(fileType.context, R.color.color_img_file)
                        )
                    }

                    else -> {
                        fileType.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
}