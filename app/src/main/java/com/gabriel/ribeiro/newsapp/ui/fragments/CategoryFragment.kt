package com.gabriel.ribeiro.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.gabriel.ribeiro.newsapp.MainActivity
import com.gabriel.ribeiro.newsapp.R
import com.gabriel.ribeiro.newsapp.databinding.FragmentCategoryBinding
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.ui.NewsAdapter
import com.gabriel.ribeiro.newsapp.ui.viewmodels.HeadlinesViewModel
import com.gabriel.ribeiro.newsapp.utils.Constants.ARTICLE_SERIALIZABLE_KEY
import com.gabriel.ribeiro.newsapp.utils.Constants.TAG
import com.gabriel.ribeiro.newsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category), NewsAdapter.OnArticleClickListener {
    
    private var _binding : FragmentCategoryBinding? = null
    private val binding : FragmentCategoryBinding get() = _binding!!

    private lateinit var headLinesViewModel : HeadlinesViewModel
    
    private lateinit var categoryName : String
    private val newsAdapter by lazy{
        NewsAdapter(this)
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentCategoryBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        headLinesViewModel = (activity as MainActivity).headlinesViewModel
        requireArguments().let {
            categoryName = it.getString("category")!!
            Log.d(TAG, "onViewCreated: Category: $categoryName")
        }
        setupRecyclerView()
        
        headLinesViewModel.getNewsOfCategory(categoryName).observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Failure -> {
                }
                is Resource.Success -> {
                    Log.d(TAG, "onViewCreated: Articles: ${it.data?.articles}")
                    it.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }

                }
            }
        })
    }

    private fun setupRecyclerView() {
        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.recyclerViewCategory.apply {
            addItemDecoration(dividerItemDecoration)
            adapter = newsAdapter
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onArticleClicked(article: Article) {
        val articleBundle = Bundle().apply {
            putSerializable(ARTICLE_SERIALIZABLE_KEY, article)
        }

        findNavController().navigate(R.id.action_categoryFragment_to_articuleFragment, articleBundle)
    }

}