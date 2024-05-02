package com.team.itcron.presentation.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.team.itcron.databinding.CaseItemBinding
import com.team.itcron.domain.models.Case

class CaseViewHolder(val binding: CaseItemBinding) : ViewHolder(binding.root) {
    fun bind(case: Case, glide: RequestManager) {
        binding.titleCase.text = case.title
        glide.load(case.image).into(binding.imageCase)
    }
}