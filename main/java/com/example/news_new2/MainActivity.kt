package com.example.news_new2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.news_new2.repository.NewsRepository
import com.example.news_new2.repository.db.ArticleDatabase
import com.example.news_new2.viewModel.NewsViewModel
import com.example.news_new2.viewModel.NewsViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

     lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

     /*   val newsRepository = NewsRepository(ArticleDatabase(this))
        val mainViewModelProviderFactory = NewsViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this, mainViewModelProviderFactory).get(NewsViewModel::class.java)*/

      /*  private val viewModel: NewsViewModel by lazy {
            ViewModelProvider(this,viewModelProvider).get(NewsViewModel::class.java)
        }*/

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProvider = NewsViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProvider).get(NewsViewModel::class.java)

//        bottomNavigationView.setupWithNavController(newsFragment.findNavController())
        val navHostFragment= supportFragmentManager.findFragmentById(R.id.newsFragment) as NavHostFragment
        val navController= navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

    }
}