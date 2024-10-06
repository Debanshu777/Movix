package com.debanshudatta.movie1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.debanshudatta.movie1.navigation.MovieNavHost
import com.debanshudatta.movie1.ui.theme.Movie1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Movie1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MovieNavHost(rememberNavController())
                }
            }
        }
    }
}
