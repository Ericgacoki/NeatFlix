package com.ericg.neatflix.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ericg.neatflix.R
import com.ericg.neatflix.screens.destinations.HomeDestination
import com.ericg.neatflix.sharedComposables.LottieLoader
import com.ericg.neatflix.ui.theme.AppPrimaryColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import timber.log.Timber

@OptIn(ExperimentalAnimationApi::class)
@Destination(start = true)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator?
) {
    var animateLogo by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(AppPrimaryColor)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {

            LottieLoader(
                modifier = Modifier.size(270.dp),
                lottieFile = R.raw.bubble
            )

            LaunchedEffect(Unit) {
                delay(2000)
                animateLogo = true
                delay(2000)
                navigator!!.popBackStack()
                navigator.navigate(HomeDestination())
            }

            this@Column.AnimatedVisibility(
                visible = animateLogo.not(),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 2000)
                ) + scaleOut(animationSpec = tween(durationMillis = 2000)),
            ) {
                Image(
                    modifier = Modifier
                        .widthIn(max = 170.dp)
                        .alpha(0.78F),
                    painter = painterResource(id = R.drawable.neatflix_logo_large),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenPrev() {
    SplashScreen(navigator = null)
}