package com.gabriel.ribeiro.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabriel.ribeiro.newsapp.MainActivity
import com.gabriel.ribeiro.newsapp.R
import com.gabriel.ribeiro.newsapp.databinding.HeadlinesFragmentBinding
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.ui.NewsAdapter
import com.gabriel.ribeiro.newsapp.ui.viewmodels.MainViewModel
import com.gabriel.ribeiro.newsapp.utils.Constants
import com.gabriel.ribeiro.newsapp.utils.Constants.Companion.QUERY_PAGE_SIZE
import com.gabriel.ribeiro.newsapp.utils.Resource

class HeadlinesFragment : Fragment(R.layout.headlines_fragment), NewsAdapter.OnArticleClickListener {

    private lateinit var binding : HeadlinesFragmentBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HeadlinesFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (activity as MainActivity).mainViewModel
        newsAdapter = NewsAdapter(this)
        setupRecyclerView()

    }

    override fun onStart() {
        observer()
        super.onStart()
    }

    private fun setupRecyclerView(){
        binding.recyclerViewHeadlines.apply {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
            adapter = newsAdapter
            addOnScrollListener(this@HeadlinesFragment.scrollListener)

        }

    }
    private fun observer(){
        mainViewModel.headLines.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.d(Constants.TAG, "observer: Loading...")
                }
                is Resource.Failure -> {
                    Log.d(Constants.TAG, "observer: Error: ${resource.message} ")
                }

                is Resource.Success -> {
                    resource.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        val totalPages = it.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = mainViewModel.breakingNewsPage == totalPages
                    }
                }
            }

        })

    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    var scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isLastItem= firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate =isNotLoadingAndNotLastPage && isLastItem
                    && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate){
                mainViewModel.getHeadlines("br")
                isScrolling = false
            }else{
                binding.recyclerViewHeadlines.setPadding(0,0,0,0)
            }
        }
    }



    override fun onArticleClicked(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article",article)
        }
        findNavController().navigate(R.id.action_headlinesFragment_to_articuleFragment,bundle)

    }

}