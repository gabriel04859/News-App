package com.gabriel.ribeiro.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabriel.ribeiro.newsapp.MainActivity
import com.gabriel.ribeiro.newsapp.R
import com.gabriel.ribeiro.newsapp.databinding.SearchFragmentBinding
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.ui.NewsAdapter
import com.gabriel.ribeiro.newsapp.utils.Constants
import com.gabriel.ribeiro.newsapp.utils.Resource

class SearchFragment : Fragment(R.layout.search_fragment), NewsAdapter.OnArticleClickListener {
    private lateinit var binding : SearchFragmentBinding
    private val newsAdapter by lazy {
        NewsAdapter(this)
    }
    private val mainViewModel by lazy {
        (activity as MainActivity).mainViewModel
    }
    private lateinit var searchQuery : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.searchViewNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    mainViewModel.searchForNews(query)
                    searchQuery = query
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        observerNews()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSearch.apply {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
            adapter = newsAdapter
            addOnScrollListener(this@SearchFragment.scrollListener)
        }
    }

    private fun observerNews(){

        mainViewModel.searchNews.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    Log.i(Constants.TAG, "Loading...")}
                is Resource.Failure -> {
                    Log.i(Constants.TAG, "Error: ${result.message}")}

                is Resource.Success -> {
                    result.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        val totalPages = it.totalResults / Constants.QUERY_PAGE_SIZE + 2
                        isLastPage = mainViewModel.searchNewsPage == totalPages
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
                mainViewModel.searchForNews(searchQuery)
                isScrolling = false
            }else{
                binding.recyclerViewSearch.setPadding(0,0,0,0)
            }
        }
    }

    override fun onArticleClicked(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article",article)
        }
        findNavController().navigate(R.id.action_searchFragment_to_articuleFragment,bundle)
    }

}