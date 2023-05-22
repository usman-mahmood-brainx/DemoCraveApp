package com.example.demo_instaforfood.Paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.demo_instaforfood.Api.ReviewImageAPI
import com.example.demo_instaforfood.TemporaryModels.Data


class ReviewImagesPagingSource(private val reviewImagesApi: ReviewImageAPI):
    PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try{
            val position = params.key ?: 1
            val response = reviewImagesApi.getAllUsers(position)

            return LoadResult.Page(
                data = response.data,
                prevKey = if(position == 1) null else position - 1,
                nextKey = if(position == response.totalPages) null else position + 1
            )
        }
        catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}