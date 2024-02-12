package com.aungbophyoe.domain.repo

import com.aungbophyoe.data.BuildConfig
import com.aungbophyoe.data.model.NewsResponse
import com.aungbophyoe.data.remote.ApiService
import com.aungbophyoe.data.repo.NewsRepository
import com.aungbophyoe.data.utility.Either
import com.aungbophyoe.data.utility.RESULT
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val apiService: ApiService) : NewsRepository {
    override suspend fun getNews(
        page: Int,
        pageSize: Int,
        keyword: String
    ): RESULT<List<NewsResponse>> {
        return try {
            val response = apiService.getNews(
                BuildConfig.AUTH_TOKEN,
                page,
                pageSize,
                keyword
            )
            val data = response!!.body()!!.articles ?: emptyList()
            Either.SUCCESS(data)
        } catch (e: Throwable) {
            Either.FAILED(handleException(e))
        }
    }
}