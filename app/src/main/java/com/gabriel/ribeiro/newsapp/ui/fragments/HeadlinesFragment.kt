package com.gabriel.ribeiro.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
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
import com.gabriel.ribeiro.newsapp.databinding.HeadlinesFragmentBinding
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.repositorys.MainRepositoryImplemented
import com.gabriel.ribeiro.newsapp.ui.NewsAdapter
import com.gabriel.ribeiro.newsapp.ui.viewmodels.HeadlinesViewModel
import com.gabriel.ribeiro.newsapp.ui.viewmodels.MainViewModel
import com.gabriel.ribeiro.newsapp.utils.Constants
import com.gabriel.ribeiro.newsapp.utils.Constants.QUERY_PAGE_SIZE
import com.gabriel.ribeiro.newsapp.utils.Constants.TAG
import com.gabriel.ribeiro.newsapp.utils.Resource

class HeadlinesFragment : Fragment(R.layout.headlines_fragment), NewsAdapter.OnArticleClickListener {

    private lateinit var binding : HeadlinesFragmentBinding
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
        newsAdapter = NewsAdapter(this)
        setupRecyclerView()


    }

    override fun onStart() {
        observerArticles()
        super.onStart()
    }

    private fun observerArticles() {
        val factory = HeadlinesViewModel.HeadlinesViewModelFactory(MainRepositoryImplemented(
            DataSource(ArticleDatabase(requireContext()))))
        val headLinesViewModel = ViewModelProvider(this, factory).get(HeadlinesViewModel::class.java)

        headLinesViewModel.setHeadlines().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Failure -> Log.d(TAG, "onViewCreated: Erro: ${it.message}")
                is Resource.Loading -> Log.d(TAG, "onViewCreated: Loading...")
                is Resource.Success -> {Log.d(TAG, "onViewCreated: Articles: ${it.data?.articles}")
                    it.data?.let { newResponse ->
                        newsAdapter.differ.submitList(newResponse.articles)}
                    }

            }
        })
    }

    private fun setupRecyclerView(){
        binding.recyclerViewHeadlines.apply {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
            adapter = newsAdapter

        }

    }


    override fun onArticleClicked(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article",article)
        }
        findNavController().navigate(R.id.action_headlinesFragment_to_articuleFragment,bundle)

    }

}