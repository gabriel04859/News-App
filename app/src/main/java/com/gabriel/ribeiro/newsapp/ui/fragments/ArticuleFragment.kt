package com.gabriel.ribeiro.newsapp.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gabriel.ribeiro.newsapp.R
import com.gabriel.ribeiro.newsapp.ui.viewmodels.ArticuleViewModel

class ArticuleFragment : Fragment() {

    companion object {
        fun newInstance() = ArticuleFragment()
    }

    private lateinit var viewModel: ArticuleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.articule_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArticuleViewModel::class.java)
        // TODO: Use the ViewModel
    }

}