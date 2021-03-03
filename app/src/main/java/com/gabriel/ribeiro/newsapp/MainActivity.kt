package com.gabriel.ribeiro.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.gabriel.ribeiro.newsapp.databinding.ActivityMainBinding
import com.gabriel.ribeiro.newsapp.models.Article
import com.gabriel.ribeiro.newsapp.ui.NewsAdapter
import com.gabriel.ribeiro.newsapp.ui.viewmodels.HeadlinesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding : ActivityMainBinding get() = _binding!!
    private lateinit var navController : NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    val headlinesViewModel : HeadlinesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.setOnNavigationItemReselectedListener{ /* NO-OP */ }
        NavigationUI.setupActionBarWithNavController(this,navController)
    }

    override fun onSupportNavigateUp(): Boolean = NavigationUI.navigateUp(navController, appBarConfiguration)

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}