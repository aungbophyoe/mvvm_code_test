package com.aungbophyoe.domain.usecase

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aungbophyoe.data.repo.NewsRepository
import com.aungbophyoe.data.utility.Either
import com.aungbophyoe.domain.mapper.NetworkMapper
import com.aungbophyoe.domain.model.News
import retrofit2.HttpException

class NewsPagingSource constructor(
    val keyword : String,
    private val repository: NewsRepository,
    private val networkMapper: NetworkMapper
) : PagingSource<Int, News>()  {
    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = repository.getNews(
                page = nextPageNumber,
                pageSize = 50,
                keyword = "$keyword"
            )
            if(response is Either.SUCCESS) {
                val data  = networkMapper.mapFromEntityList(response.DATA)
                return LoadResult.Page(
                    data = data,
                    prevKey = if(nextPageNumber == 1) null else -1,
                    nextKey = if(nextPageNumber < 18) nextPageNumber.plus(1) else null// response.nextKey or total page no from response
                )
            } else {
                val error = response as? Either.FAILED
                return LoadResult.Error(error!!.ERROR)
            }

        } catch (e: Exception) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }
}