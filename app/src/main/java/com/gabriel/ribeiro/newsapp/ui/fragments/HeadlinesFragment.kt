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
import com.gabriel.ribeiro.newsapp.databinding.HeadlinesFragmentBinding
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.ui.NewsAdapter
import com.gabriel.ribeiro.newsapp.ui.viewmodels.MainViewModel
import com.gabriel.ribeiro.newsapp.utils.Constants
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

    }

    override fun onStart() {
        observer()
        super.onStart()
    }

    private fun observer(){
        binding.recyclerViewHeadlines.apply {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
            adapter = newsAdapter
        }
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
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
            }


        })


    }



    override fun onArticleClicked(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article",article)
        }
        findNavController().navigate(R.id.action_headlinesFragment_to_articuleFragment,bundle)

    }

}