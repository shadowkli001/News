package com.example.news_new2.repository.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.bumptech.glide.load.engine.Resource
import com.example.news_new2.model.Article
import com.example.news_new2.model.NewsResponse
import com.example.news_new2.repository.service.RetrofitClient
import com.example.news_new2.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ArticleDataSource(val scope: CoroutineScope) : PageKeyedDataSource<Int, Article>() {
    val breakingNews: MutableLiveData<MutableList<Article>> = MutableLiveData()
    var breakingPageNumber = 1
    var breakingNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchPageNumber = 1
    var searchNewsResponse: NewsResponse? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        scope.launch {
            try {
                val response = RetrofitClient.api.getBreakingNews("uz",1,Constants.API_KEY)
                when {
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            breakingNews.postValue(it)
                            callback.onResult(it,null,2)
                        }
                    }
                }
            } catch (exception: Exception) {
                Log.e("DataSource::", exception.message.toString())
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        try {
            scope.launch {
                val response = RetrofitClient.api.getBreakingNews("uz", params.requestedLoadSize, Constants.API_KEY)
                when {
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            callback.onResult(it, params.key + 1)
                        }
                    }
                }
            }
        }catch (exception: Exception) {
            Log.e("DataSource::", exception.message.toString())
        }
    }
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
    }
}