package com.team.itcron.presentation.adapter_delegation

import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.team.itcron.databinding.ScreenshotItemBinding

fun screenImageDelegate(
    glide: RequestManager
) = adapterDelegateViewBinding<
        String, String, ScreenshotItemBinding
        >(
    { layoutInflater, root ->
        ScreenshotItemBinding.inflate(
            layoutInflater, root, false
        )
    }
) {
    bind {
        glide.load(item).into(binding.screenImage)
    }
}