package com.aungbophyoe.codingtestmvvm.ui

import com.aungbophyoe.core.ViewBindingFragment
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aungbophyoe.codingtestmvvm.R
import com.aungbophyoe.codingtestmvvm.adapter.LoadStateAdapter
import com.aungbophyoe.codingtestmvvm.adapter.NewsListAdapter
import com.aungbophyoe.codingtestmvvm.databinding.FragmentNewsMainBinding
import com.aungbophyoe.core.utility.hide
import com.aungbophyoe.core.utility.setSafeClickListener
import com.aungbophyoe.core.utility.show
import com.aungbophyoe.core.utility.textChanges
import com.aungbophyoe.data.utility.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsMainFragment : ViewBindingFragment<FragmentNewsMainBinding>(),LoadStateAdapter.RetryOnClickOnListener {
    override val layoutRes: Int = R.layout.fragment_news_main

    private val loadStateAdapter : LoadStateAdapter by lazy {
        LoadStateAdapter(this)
    }

    private val newsListAdapter : NewsListAdapter by lazy {
        NewsListAdapter(requireContext())
    }

    val viewModel : NewsViewModel by viewModels()
    private var isGridView = false

    private val recyclerLayoutManager by lazy {
        GridLayoutManager(requireContext(),1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            intiRecyclerView()
            observeData()
            rlTryAgain.setSafeClickListener {
                if(isNetworkAvailable(requireContext())){
                    if(newsListAdapter.itemCount==0){
                        observeData()
                    }else{
                        newsListAdapter.retry()
                    }
                    rlTryAgain.hide()
                }
            }

            ivChangeGrid.setSafeClickListener {
                toggleLayout()
            }

            edtSearchBox.textChanges()
                .debounce(500)
                .onEach { query ->
                    if (!query?.isNotEmpty()!!) {
                        ivChangeGrid.hide()
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.setKeyword(null) // Pass null to use default keyword or remove parameter
                        }
                    } else {
                        ivChangeGrid.show()
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.setKeyword(query.toString()) // Pass null to use default keyword or remove parameter
                        }
                    }
                }
                .launchIn(lifecycleScope)
        }
    }

    @SuppressLint("RepeatOnLifecycleWrongUsage")
    private fun observeData(){
        viewBinding.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    /*viewModel.setKeyword(null)*/
                    try {
                        viewModel.getNewsWithQuery().collectLatest { response->
                            newsListAdapter.submitData(response)
                        }
                    }catch (e:Exception){
                        viewModel.getNewsWithQuery().cancellable()
                    }
                }
            }
        }
    }


    private fun intiRecyclerView() {
        viewBinding.apply {
            rvNews.apply {
                setHasFixedSize(true)
                layoutManager = recyclerLayoutManager
                adapter = newsListAdapter.withLoadStateFooter(loadStateAdapter)
            }
        }
    }

    private fun toggleLayout() {
        viewBinding.apply {
            rvNews.apply {
                isGridView = !isGridView
                newsListAdapter.setGridView(isGridView)
                if (isGridView) {
                    recyclerLayoutManager.spanCount = 2 // Change to the number of columns you desire
                } else {
                    recyclerLayoutManager.spanCount = 1
                }
                newsListAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewBinding.apply {
            if(isNetworkAvailable(requireContext())){
                rlTryAgain.hide()
                observeData()
            }else{
                rlTryAgain.show()
            }
        }
    }


    override fun retryOnClick() {
        if(isNetworkAvailable(requireContext())){
            newsListAdapter.retry()
        }
    }
}