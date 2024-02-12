package com.aungbophyoe.data.model

import com.google.gson.annotations.SerializedName

class NewsResponse(
    @SerializedName("author")
    val author : String?,
    @SerializedName("title")
    val title : String?,
    @SerializedName("urlToImage")
    val urlToImage : String?,
    @SerializedName("description")
    val description : String?,
    @SerializedName("publishedAt")
    val publishedAt : String?
)