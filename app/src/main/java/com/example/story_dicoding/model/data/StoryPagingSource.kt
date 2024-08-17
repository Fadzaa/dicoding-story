package com.example.story_dicoding.model.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.model.remote.response.Story

class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int, Story>() {

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllStory(position, params.loadSize, 0).body()
            val listStory = responseData.let { it?.listStory }

            LoadResult.Page(
                data = listStory ?: emptyList(),
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (listStory.isNullOrEmpty()) null else position + 1
            )

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}