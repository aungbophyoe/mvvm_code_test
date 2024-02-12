package com.aungbophyoe.data.model

import com.google.gson.annotations.SerializedName

data class AllNewsResponse(
    @SerializedName("articles")
    val articles : List<NewsResponse>?
)