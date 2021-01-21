package com.gabriel.ribeiro.newsapp.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.gabriel.ribeiro.newsapp.R
import com.gabriel.ribeiro.newsapp.api.DataSource
import com.gabriel.ribeiro.newsapp.api.NewsApi
import com.gabriel.ribeiro.newsapp.repositorys.HeadlineRepositoryImplemented
import com.gabriel.ribeiro.newsapp.ui.viewmodels.HeadlinesViewModel
import com.gabriel.ribeiro.newsapp.ui.viewmodels.factors.HeadlinesFactors
import com.gabriel.ribeiro.newsapp.utils.Result

class HeadlinesFragment : Fragment() {

    private lateinit var viewModel: HeadlinesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.headlines_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val viewModelFactory = HeadlinesFactors(HeadlineRepositoryImplemented(DataSource()))
        viewModel = ViewModelProvider(this,viewModelFactory).get(HeadlinesViewModel::class.java)

        viewModel.getAllArticles()
        viewModel.newResponses.observe(viewLifecycleOwner, Observer {
            when(it){
               is Result.Success -> {
                   Log.i("TESTE","${it.data?.articles}")

               }
                is Result.Failure ->{
                    Log.i("TESTE","${ it.exception?.message}")

                }
                is Result.Loading -> {
                    Log.i("TESTE","Carregando")

                }
            }
        })

    }

}