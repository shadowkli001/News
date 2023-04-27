package com.example.news_new2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news_new2.repository.NewsRepository

class NewsViewModelFactory(val newsRepository : NewsRepository) : ViewModelProvider.Factory {
    //det kan vara ViewModel?   
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}