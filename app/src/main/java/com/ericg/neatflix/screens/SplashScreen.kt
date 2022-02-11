package com.ericg.neatflix.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ericg.neatflix.R

@Composable
fun SplashScreen() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180E36))
    ) {
        val (banner, logo, title, slogan) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.movie_banner),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5F)
                .constrainAs(banner) {
                    top.linkTo(parent.top)
                },
            contentScale = ContentScale.Crop,
            contentDescription = "Movie banner"
        )
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            modifier = Modifier
                //.size(size = 100.dp)
                .constrainAs(logo) {
                    top.linkTo(banner.bottom)
                    bottom.linkTo(banner.bottom)
                    start.linkTo(banner.start)
                    end.linkTo(banner.end)
                },
            contentDescription = "App logo"
        )
        Text(text = "Neatflix", color = Color(255, 255, 255).copy(alpha = 0.78F),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(logo.bottom, margin = 12.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        val sloganText = "Access unlimited movies, series & TV shows anywhere, anytime"
        Text(
            text = sloganText, color = Color(255, 255, 255).copy(alpha = 0.78F),
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .constrainAs(slogan) {
                    top.linkTo(title.bottom, margin = 12.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 4.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(device = Devices.PIXEL, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenPrev() {
    SplashScreen()
}