package com.aungbophyoe.codingtestmvvm.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aungbophyoe.domain.model.News
import com.aungbophyoe.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class NewsViewModel  @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel(){

    private val _keywordFlow = MutableStateFlow<String?>(null)
    val keywordFlow: StateFlow<String?> = _keywordFlow

    fun setKeyword(keyword: String?) {
        _keywordFlow.value = keyword ?: ""
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getNewsWithQuery(): Flow<PagingData<News>> = keywordFlow
        .flatMapLatest { keyword ->
            newsUseCase.searchAnything("$keyword")
        }.cachedIn(viewModelScope)
}