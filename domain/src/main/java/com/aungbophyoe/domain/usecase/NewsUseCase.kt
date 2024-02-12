package com.aungbophyoe.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aungbophyoe.data.repo.NewsRepository
import com.aungbophyoe.domain.mapper.NetworkMapper
import com.aungbophyoe.domain.model.News
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val repository: NewsRepository, private val networkMapper: NetworkMapper) {
    fun searchAnything(keyword : String) : Flow<PagingData<News>> {
        return Pager(
            PagingConfig(pageSize = 50)
        ) {
            NewsPagingSource(
                keyword, repository, networkMapper
            )
        }.flow
    }
}