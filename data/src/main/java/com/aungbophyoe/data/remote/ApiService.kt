package com.aungbophyoe.data.remote

import com.aungbophyoe.data.model.AllNewsResponse
import retrofit2.Response
import retrofit2.http.Query
import retrofit2.http.GET

interface ApiService {
    @GET("everything")
    suspend fun  getNews(@Query("apiKey") apiKey : String,
                         @Query("page") page : Int,
                         @Query("pageSize") pageSize : Int,
                         @Query("q") searchKey : String ) : Response<AllNewsResponse>?
}