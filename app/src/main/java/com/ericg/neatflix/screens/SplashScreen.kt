package com.ericg.neatflix.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ericg.neatflix.R

@Composable
fun SplashScreen() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180E36))
    ) {
        val (banner, logo) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.movie_banner),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5F)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .constrainAs(banner) {
                    top.linkTo(parent.top)
                },
            contentScale = ContentScale.Crop,
            contentDescription = "Movie banner"
        )

        Image(
            modifier = Modifier
                .widthIn(max = 150.dp)
                .alpha(0.78F)
                .constrainAs(logo) {
                    top.linkTo(banner.bottom)
                    bottom.linkTo(banner.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            painter = painterResource(id = R.drawable.neatflix_logo_large),
            contentDescription = null
        )
    }
}

@Preview(device = Devices.PIXEL, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenPrev() {
    SplashScreen()
}