package com.gabriel.ribeiro.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.gabriel.ribeiro.newsapp.MainActivity
import com.gabriel.ribeiro.newsapp.R
import com.gabriel.ribeiro.newsapp.databinding.HeadlinesFragmentBinding
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.ui.NewsAdapter
import com.gabriel.ribeiro.newsapp.ui.viewmodels.HeadlinesViewModel
import com.gabriel.ribeiro.newsapp.utils.Constants.ARTICLE_SERIALIZABLE_KEY
import com.gabriel.ribeiro.newsapp.utils.Constants.TAG
import com.gabriel.ribeiro.newsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeadlinesFragment : Fragment(R.layout.headlines_fragment), NewsAdapter.OnArticleClickListener, View.OnClickListener {

    private lateinit var binding : HeadlinesFragmentBinding
    private lateinit var newsAdapter: NewsAdapter

    private lateinit var headLinesViewModel : HeadlinesViewModel

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
        headLinesViewModel = (activity as MainActivity).headlinesViewModel
        setupRecyclerView()

        initButtonsCategory()


    }

    override fun onStart() {
        observerArticles()
        super.onStart()
    }

    private fun observerArticles() {
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


    private fun initButtonsCategory() {
        binding.includeButtonCategory.buttonBusiness.setOnClickListener(this)
        binding.includeButtonCategory.buttonEntertainment.setOnClickListener(this)
        binding.includeButtonCategory.buttonHealth.setOnClickListener(this)
        binding.includeButtonCategory.buttonScience.setOnClickListener(this)
        binding.includeButtonCategory.buttonSports.setOnClickListener(this)
        binding.includeButtonCategory.buttonTechnology.setOnClickListener(this)

    }

    override fun onArticleClicked(article: Article) {
        val bundle = Bundle().apply {
            putSerializable(ARTICLE_SERIALIZABLE_KEY,article)
        }
        findNavController().navigate(R.id.action_headlinesFragment_to_articuleFragment,bundle)
    }

    override fun onClick(v: View?) {
        var category : String = ""
        when(v?.id){
            R.id.buttonBusiness -> category = "business"
            R.id.buttonEntertainment -> category = "entertainment"
            R.id.buttonHealth -> category = "health"
            R.id.buttonScience -> category = "science"
            R.id.buttonSports -> category = "sports"
            R.id.buttonTechnology -> category = "technology"

        }
        val categoryBundle = Bundle().apply { putString("category", category) }
        findNavController().navigate(R.id.action_headlinesFragment_to_categoryFragment, categoryBundle )
    }

}