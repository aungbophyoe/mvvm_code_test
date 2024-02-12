package com.aungbophyoe.data.repo

import com.aungbophyoe.data.model.NewsResponse
import com.aungbophyoe.data.utility.RESULT

interface NewsRepository : CleanRepository {
    suspend fun getNews(page : Int, pageSize:Int, keyword: String) : RESULT<List<NewsResponse>>
}