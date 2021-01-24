package com.gabriel.ribeiro.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.gabriel.ribeiro.newsapp.MainActivity
import com.gabriel.ribeiro.newsapp.R
import com.gabriel.ribeiro.newsapp.databinding.ArticuleFragmentBinding
import com.gabriel.ribeiro.newsapp.databinding.HeadlinesFragmentBinding
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.ui.HeadLinesAdapter
import com.gabriel.ribeiro.newsapp.ui.viewmodels.MainViewModel
import com.gabriel.ribeiro.newsapp.utils.Constants
import com.gabriel.ribeiro.newsapp.utils.Result

class HeadlinesFragment : Fragment(R.layout.headlines_fragment), HeadLinesAdapter.OnArticleClickListener {

    private lateinit var binding : HeadlinesFragmentBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var headLinesAdapter: HeadLinesAdapter

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
        headLinesAdapter = HeadLinesAdapter(this)
    }

    override fun onStart() {
        observer()
        super.onStart()
    }

    private fun observer(){
        binding.recyclerViewHeadlines.adapter = headLinesAdapter
        mainViewModel.headLines.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Loading -> {Log.i(Constants.TAG, "Loading...")}
                is Result.Failure -> {
                    Log.i(Constants.TAG, "Error: ${result.message}")}

                is Result.Success -> {
                    result.data?.let {
                        headLinesAdapter.differ.submitList(it.articles)
                    }
                }
            }



        })


    }

    override fun onDestroy() {

        super.onDestroy()
    }

    override fun onArticleClicked(article: Article) {

    }

}