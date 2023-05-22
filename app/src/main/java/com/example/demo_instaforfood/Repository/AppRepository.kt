package com.example.demo_instaforfood.Repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.demo_instaforfood.Api.ReviewImageAPI
import com.example.demo_instaforfood.Paging.ReviewImagesPagingSource
import javax.inject.Inject


class AppRepository @Inject constructor(
    private val reviewImagesAPI: ReviewImageAPI,
) {
    fun getImages() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { ReviewImagesPagingSource(reviewImagesAPI) }
    ).liveData
    
}