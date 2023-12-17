package com.keyword.keyword_miner.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.keyword.keyword_miner.KeywordInfo
import com.keyword.keyword_miner.RecyclerView.RelKeywordRecyclerViewAdapter
import com.keyword.keyword_miner.databinding.FragmentRelBinding
import com.keyword.keyword_miner.domain.Model.relKeywordData.RelKeywordDataModel
import com.keyword.keyword_miner.ui.viewmodels.KeywordViewModel
import com.keyword.keyword_miner.utils.MainUiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RelFragment : Fragment() {

    lateinit var binding: FragmentRelBinding
    lateinit var keywordTestList: List<RelKeywordDataModel>
    val KeywordViewModel by activityViewModels<KeywordViewModel>()
    lateinit var keywordAdapter: RelKeywordRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRelBinding.inflate(layoutInflater)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                KeywordViewModel.currentRelData.collectLatest {
                    when (it) {
                        is MainUiState.success -> {
                            Log.d("hhhh", "RelFragment - onCreateView() - called ${it.data}")
                            keywordTestList = it.data
                            keywordAdapter = RelKeywordRecyclerViewAdapter(KeywordViewModel)
                            keywordAdapter.submit(keywordTestList)

                            binding.recyclerview.apply {
                                layoutManager = LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.VERTICAL, false
                                )
                                adapter = keywordAdapter
                            }
                        }

                        else -> {
                            Toast.makeText(getActivity(), "서버와 통신이 실패하였습니다", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
        return binding.root
    }

}