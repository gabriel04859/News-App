package com.gabriel.ribeiro.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import com.gabriel.ribeiro.newsapp.R
import com.gabriel.ribeiro.newsapp.databinding.ArticuleFragmentBinding
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.ui.viewmodels.HeadlinesViewModel
import com.gabriel.ribeiro.newsapp.utils.Constants.ARTICLE_SERIALIZABLE_KEY
import com.gabriel.ribeiro.newsapp.utils.Constants.TAG
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.articule_fragment) {

    private var _binding : ArticuleFragmentBinding? = null
    private val binding : ArticuleFragmentBinding get() = _binding!!

    private val headlinesViewModel : HeadlinesViewModel by viewModels()
    private lateinit var article : Article

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArticuleFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireArguments().let {
            article = (it.getSerializable(ARTICLE_SERIALIZABLE_KEY) as Article?)!!
        }
        Log.d(TAG, "onViewCreated: Artigo: $article ")
        binding.webViewArticle.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        binding.floatingActionButtonSaveArticule.setOnClickListener {
            headlinesViewModel.saveArticle(article)
            Snackbar.make(view,getString(R.string.salvo_com_sucesso),Snackbar.LENGTH_SHORT).show()
            Log.d(TAG, "onViewCreated: Salvo com sucesso: $article ")

        }

    }




    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}