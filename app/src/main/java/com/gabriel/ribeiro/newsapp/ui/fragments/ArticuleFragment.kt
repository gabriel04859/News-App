package com.gabriel.ribeiro.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gabriel.ribeiro.newsapp.MainActivity
import com.gabriel.ribeiro.newsapp.R
import com.gabriel.ribeiro.newsapp.databinding.ArticuleFragmentBinding
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.ui.viewmodels.MainViewModel
import com.gabriel.ribeiro.newsapp.utils.Constants.Companion.TAG
import com.google.android.material.snackbar.Snackbar

class ArticuleFragment : Fragment(R.layout.articule_fragment) {
    private var _binding : ArticuleFragmentBinding? = null
    private val binding : ArticuleFragmentBinding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var article : Article
    private val args : ArticuleFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArticuleFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (activity as MainActivity).mainViewModel
        article = args.article
        Log.d(TAG, "onViewCreated: Artigo: $article ")
        binding.webViewArticle.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        binding.floatingActionButtonSaveArticule.setOnClickListener {
            mainViewModel.saveArticle(article)
            Snackbar.make(view,getString(R.string.salvo_com_sucesso),Snackbar.LENGTH_SHORT).show()
            Log.d(TAG, "onViewCreated: Salvo com sucesso: $article ")

        }

    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}