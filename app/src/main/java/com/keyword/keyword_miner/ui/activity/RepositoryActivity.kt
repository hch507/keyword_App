package com.keyword.keyword_miner.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.keyword.keyword_miner.databinding.ActivityRepositoryBinding
import com.keyword.keyword_miner.RecyclerView.TRepositoryRecyclerViewAdapter
import com.keyword.keyword_miner.data.dto.KeywordSaveModel
import com.keyword.keyword_miner.ui.viewmodels.StorageViewModel
import com.keyword.keyword_miner.utils.MainUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityRepositoryBinding
    private val adapter by lazy { TRepositoryRecyclerViewAdapter(RepositoryHandler()) }

    var storeItemList = listOf<KeywordSaveModel>()
    val storageViewModel: StorageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.repositoryRecyclerview.adapter = adapter


        storageViewModel.getSavedData()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                storageViewModel.savedData.collectLatest {
                    when (it) {
                        is MainUiState.success -> {
                            storeItemList = it.data
                            binding.repositoryRecyclerview.apply {
                                layoutManager = LinearLayoutManager(
                                    this@RepositoryActivity,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            }
                            adapter.submitList(storeItemList)
                        }

                        is MainUiState.Error -> {
                            Toast.makeText(
                                this@RepositoryActivity,
                                "데이터를 가져오는데 실패하였습니다. 다시 시도해주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is MainUiState.Loading -> {}
                    }
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }
    inner class RepositoryHandler {
        fun delete(data : KeywordSaveModel){
            storageViewModel.requestDeleteData(data)
        }
    }
}