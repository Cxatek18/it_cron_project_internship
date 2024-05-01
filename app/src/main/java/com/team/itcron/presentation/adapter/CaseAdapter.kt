package com.team.itcron.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.team.itcron.databinding.CaseItemBinding
import com.team.itcron.domain.models.Case

class CaseAdapter : ListAdapter<Case, CaseViewHolder>(CaseDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseViewHolder {
        val binding = CaseItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CaseViewHolder, position: Int) {
        val case = getItem(position)
        holder.binding.titleCase.text = case.title
        Glide.with(holder.itemView)
            .load(case.image)
            .into(holder.binding.imageCase)
    }
}