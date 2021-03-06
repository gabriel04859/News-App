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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gabriel.ribeiro.newsapp.R
import com.gabriel.ribeiro.newsapp.databinding.SavedFragmentBinding
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.ui.NewsAdapter
import com.gabriel.ribeiro.newsapp.ui.viewmodels.HeadlinesViewModel
import com.gabriel.ribeiro.newsapp.utils.Constants.ARTICLE_SERIALIZABLE_KEY
import com.gabriel.ribeiro.newsapp.utils.Constants.TAG
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment(R.layout.saved_fragment), NewsAdapter.OnArticleClickListener {

    private var _binding : SavedFragmentBinding? = null
    private val binding : SavedFragmentBinding get() = _binding!!

    private val headlinesViewModel : HeadlinesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SavedFragmentBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val newsAdapter = NewsAdapter(this)
        val itemDividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.recyclerSavedArticles.addItemDecoration(itemDividerItemDecoration)
        binding.recyclerSavedArticles.adapter = newsAdapter
        headlinesViewModel.getAllArticlesSaved().observe(viewLifecycleOwner, Observer {articles ->
            Log.d(TAG, "onViewCreated: List Saved: $articles")
            if(articles.isEmpty()){
                binding.textViewNoArticle.visibility = View.VISIBLE
            }
            newsAdapter.differ.submitList(articles)
        })


        val itemTouchHelperCallback = object  : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                headlinesViewModel.deleteArticle(article)
                Snackbar.make(view,getString(R.string.deletado_com_sucesso),Snackbar.LENGTH_SHORT).apply {
                    setAction("Retry"){
                        headlinesViewModel.saveArticle(article)
                    }
                    show()
                }
            }

        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerSavedArticles)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onArticleClicked(article: Article) {
        val bundle = Bundle().apply {
            putSerializable(ARTICLE_SERIALIZABLE_KEY,article)
        }
        findNavController().navigate(R.id.action_savedFragment_to_articuleFragment,bundle)

    }
}