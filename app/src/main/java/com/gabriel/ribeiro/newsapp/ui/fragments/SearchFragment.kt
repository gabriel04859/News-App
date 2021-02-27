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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabriel.ribeiro.newsapp.MainActivity
import com.gabriel.ribeiro.newsapp.R
import com.gabriel.ribeiro.newsapp.api.DataSource
import com.gabriel.ribeiro.newsapp.database.ArticleDatabase
import com.gabriel.ribeiro.newsapp.databinding.SearchFragmentBinding
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.repositorys.MainRepositoryImplemented
import com.gabriel.ribeiro.newsapp.ui.NewsAdapter
import com.gabriel.ribeiro.newsapp.ui.viewmodels.HeadlinesViewModel
import com.gabriel.ribeiro.newsapp.ui.viewmodels.SearchViewModel
import com.gabriel.ribeiro.newsapp.utils.Constants
import com.gabriel.ribeiro.newsapp.utils.Resource

class SearchFragment : Fragment(R.layout.search_fragment), NewsAdapter.OnArticleClickListener {
    private lateinit var binding : SearchFragmentBinding

    private val newsAdapter by lazy {
        NewsAdapter(this)
    }

    private lateinit var searchViewModel : SearchViewModel

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

        val factory = SearchViewModel.SearchViewModelFactory(MainRepositoryImplemented(
                DataSource(ArticleDatabase(requireContext()))))
        searchViewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)


        binding.searchViewNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchViewModel.searchForNews(query)

                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean { return false }

        })


        observerNews()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSearch.apply {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
            adapter = newsAdapter

        }
    }

    private fun observerNews(){
        searchViewModel.searchNews.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Failure -> Log.d(Constants.TAG, "onViewCreated: Erro na search: ${it.message}")
                is Resource.Success -> {Log.d(Constants.TAG, "onViewCreated: Articles: ${it.data?.articles} ")
                    it.data?.let{newResponse ->
                        newsAdapter.differ.submitList(newResponse.articles)
                    }
                }


            }
        })


    }

    override fun onArticleClicked(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article",article)
        }
        findNavController().navigate(R.id.action_searchFragment_to_articuleFragment,bundle)
    }

}