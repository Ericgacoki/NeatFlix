package com.ericg.neatflix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ericg.neatflix.screens.SplashScreen
import com.ericg.neatflix.ui.theme.NeatFlixTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NeatFlixTheme {
                SplashScreen()
            }
        }
    }
}