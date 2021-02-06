package com.gabriel.ribeiro.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.gabriel.ribeiro.newsapp.api.DataSource
import com.gabriel.ribeiro.newsapp.database.ArticleDatabase
import com.gabriel.ribeiro.newsapp.databinding.ActivityMainBinding
import com.gabriel.ribeiro.newsapp.repositorys.MainRepositoryImplemented
import com.gabriel.ribeiro.newsapp.ui.viewmodels.MainViewModel
import com.gabriel.ribeiro.newsapp.ui.viewmodels.factors.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding : ActivityMainBinding get() = _binding!!
    private lateinit var navController : NavController
    lateinit var mainViewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigationView.setupWithNavController(navController)

        val mainViewModelFactory = MainViewModelFactory(MainRepositoryImplemented(DataSource(ArticleDatabase(this))))
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)



    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}