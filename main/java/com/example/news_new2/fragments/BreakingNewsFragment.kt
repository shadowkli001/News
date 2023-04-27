package com.example.news_new2.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_new2.MainActivity
import com.example.news_new2.R
import com.example.news_new2.adapters.ArticleAdapter
import com.example.news_new2.repository.NewsRepository
import com.example.news_new2.repository.db.ArticleDatabase
import com.example.news_new2.utils.Resource
import com.example.news_new2.utils.shareNews
import com.example.news_new2.viewModel.NewsViewModel
import com.example.news_new2.viewModel.NewsViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlin.random.Random

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel : NewsViewModel
    lateinit var newsAdapter: ArticleAdapter
    val TAG = "BreakingNewsFragment"

    private fun setupRecyclerView() {
        newsAdapter = ArticleAdapter()
        rvbreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,bundle
            )
        }
        newsAdapter.onSaveClickListener {
            if(it.id == null) {
                it.id = Random.nextInt(0,1000)
            }
            viewModel.insertArticle(it)
            Snackbar.make(requireView(),"Saved",Snackbar.LENGTH_SHORT).show()
        }
        newsAdapter.onDeleteClickListener {
            viewModel.deleteArticle(it)
            Snackbar.make(requireView(),"Removed",Snackbar.LENGTH_SHORT).show()
        }
        //det kan vara newsAdapter.onShareNewsClick
        newsAdapter.onShareClickListener {
            shareNews(context,it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        setViewModelObserver()

    }


    private fun setViewModelObserver() {
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { newsResponse ->
            when (newsResponse) {
                is Resource.Success -> {
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.visibility = View.GONE
                    newsResponse.data?.let { news ->
                        rvbreakingNews.visibility = View.VISIBLE
                        newsAdapter.differ.submitList(news.articles)
                    }
                }
                is Resource.Error -> {
                    shimmerFrameLayout.visibility = View.GONE
                    newsResponse.message?.let { message ->
                        Log.e(TAG,"Error :: $message")

                    }
                }
                is Resource.Loading -> {
                    shimmerFrameLayout.startShimmer()
                }
            }
        })
    }

}