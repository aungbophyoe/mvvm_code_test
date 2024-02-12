package com.aungbophyoe.domain.mapper

import com.aungbophyoe.data.model.NewsResponse
import com.aungbophyoe.domain.model.News
import java.util.UUID
import javax.inject.Inject

class NetworkMapper @Inject constructor() : EntityMapper<NewsResponse,News> {
    override fun mapFromEntity(entity: NewsResponse): News {
        return News(
            id = UUID.randomUUID().toString(),
            author = entity.author ?: "",
            title = entity.title ?: "",
            image = entity.urlToImage ?: "https://picsum.photos/200",
            description = entity.description ?: "",
            publishedAt = entity.publishedAt ?: ""
        )
    }

    override fun mapToEntity(domainModel: News): NewsResponse? {
        return null
    }

    fun mapFromEntityList(entities : List<NewsResponse>) : List<News>{
        val list = entities.map {
            mapFromEntity(it)
        }
        return list
    }

}