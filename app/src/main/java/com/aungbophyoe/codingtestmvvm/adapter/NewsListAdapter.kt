package com.aungbophyoe.codingtestmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aungbophyoe.codingtestmvvm.databinding.GridNewsItemBinding
import com.aungbophyoe.codingtestmvvm.databinding.RowNewsItemBinding
import com.aungbophyoe.domain.model.News

class NewsListAdapter constructor(
    private val context: Context
) : PagingDataAdapter<News,RecyclerView.ViewHolder>(DiffUtils) {


    private val inflater: LayoutInflater = LayoutInflater.from(context)

    interface ItemCardOnClickListener{
        fun itemCardOnClick(id:String)
    }

    private var isGridView = false

    companion object {
        private const val VIEW_TYPE_VERTICAL = 1
        private const val VIEW_TYPE_GRID = 2
    }

    fun setGridView(isGridView: Boolean) {
        this.isGridView = isGridView
        notifyDataSetChanged()
    }

    inner class VerticalViewHolder(private val binding: RowNewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindVertical(news: News) {
            // Bind data for vertical layout
            binding.executePendingBindings()
            binding.apply {
                model = news
            }
        }
    }

    inner class GridViewHolder(private val binding: GridNewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindGrid(news: News) {
            // Bind data for grid layout
            binding.executePendingBindings()
            binding.apply {
                model = news
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        if(data!=null) {
            if (holder is VerticalViewHolder) {
                holder.bindVertical(data)
            } else if (holder is GridViewHolder) {
                holder.bindGrid(data)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGridView) VIEW_TYPE_GRID else VIEW_TYPE_VERTICAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_VERTICAL -> {
                val binding = RowNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VerticalViewHolder(binding)
            }
            VIEW_TYPE_GRID -> {
                val binding = GridNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    object DiffUtils : DiffUtil.ItemCallback<News>(){
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

    }
}