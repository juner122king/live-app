package com.example.liveapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.liveapp.core.ui.theme.LiveAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiveAppTheme {
                Surface(modifier = Modifier, color = MaterialTheme.colorScheme.background) {
                    LiveAppRoot()
                }
            }
        }
    }
}

@Composable
private fun LiveAppRoot() {
    val navController = rememberNavController()
    AppNavHost(navController = navController)
}
