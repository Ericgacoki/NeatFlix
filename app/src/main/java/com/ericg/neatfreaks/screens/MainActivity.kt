package com.ericg.neatfreaks.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ericg.neatfreaks.ui.theme.NeatFreaksTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NeatFreaksTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
