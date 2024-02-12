package com.aungbophyoe.codingtestmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aungbophyoe.codingtestmvvm.databinding.LoadStateViewBinding
import com.aungbophyoe.core.utility.hide
import com.aungbophyoe.core.utility.show

class LoadStateAdapter(private val retryOnClickOnListener: RetryOnClickOnListener) : LoadStateAdapter<com.aungbophyoe.codingtestmvvm.adapter.LoadStateAdapter.LoadStateViewHolder>() {
    inner class LoadStateViewHolder(val binding: LoadStateViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LoadStateViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    interface RetryOnClickOnListener{
        fun retryOnClick()
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.binding.apply {
            when(loadState){
                is LoadState.Error -> {
                    progressBar.hide()
                    rlTryAgain.show()
//                    tvErrorMessage.text = loadState.error.message
                }
                is LoadState.Loading -> {
                    progressBar.show()
                    rlTryAgain.hide()
                }
                is  LoadState.NotLoading -> {
                    progressBar.hide()
                }
            }
            rlTryAgain.setOnClickListener {
                retryOnClickOnListener.retryOnClick()
            }
        }
    }
}