package com.ericg.neatflix.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ericg.neatflix.ui.theme.NeatFlixTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NeatFlixTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
