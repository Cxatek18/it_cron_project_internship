package ru.it_cron.intern3.presentation.adapter_delegation

import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.it_cron.intern3.databinding.ScreenshotItemBinding

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