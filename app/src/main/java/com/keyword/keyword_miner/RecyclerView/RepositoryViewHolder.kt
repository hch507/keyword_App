package com.keyword.keyword_miner.RecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.keyword.keyword_miner.Repository.RepositoryItem
import com.keyword.keyword_miner.databinding.ActivityRepositoryViewBinding
import java.util.*

class RepositoryViewHolder(val binding : ActivityRepositoryViewBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RepositoryItem){
        binding.keyword.text=item.keyword
        binding.monthCnt.text= item.monthCnt.toString()
        binding.blogCnt.text=item.blogCnt
        binding.datetime.text=item.datetime

    }
    val btn = binding.itemClear
}