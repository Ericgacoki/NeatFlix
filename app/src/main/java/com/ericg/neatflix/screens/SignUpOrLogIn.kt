package com.ericg.neatflix.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.ButtonColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SignUpOrLogInScreen(
    navigator: DestinationsNavigator
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180E36))
    ) {
        val (banner, logo, title, slogan, btnSignUp, btnLogIn) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.popcorn),
            modifier = Modifier
                .fillMaxWidth()
                // .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .constrainAs(banner) {
                    top.linkTo(parent.top)
                }
                .fillMaxHeight(fraction = 0.5F),
            contentScale = ContentScale.Crop,
            contentDescription = "Movie banner"
        )
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            modifier = Modifier.constrainAs(logo) {
                top.linkTo(banner.bottom)
                bottom.linkTo(banner.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            contentDescription = "App logo"
        )

        val welcomeText = "Access unlimited movies, series & TV shows anywhere, anytime"
        Text(
            text = welcomeText,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .constrainAs(slogan) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(logo.bottom, margin = 12.dp)
                },
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            color = Color.White.copy(alpha = 0.78F)
        )
        Button(
            onClick = {},
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .fillMaxWidth()
                .height(46.dp)
                .constrainAs(btnSignUp) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(slogan.bottom, margin = 24.dp)
                },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ButtonColor
            )
        ) {
            Text(text = "Sign up", color = AppOnPrimaryColor)
        }
        Button(
            onClick = {

            },
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .fillMaxWidth()
                .height(46.dp)
                .constrainAs(btnLogIn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(btnSignUp.bottom, margin = 24.dp)
                },
            colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor)
        ) {
            Text(text = "Log in", color = AppOnPrimaryColor)
        }
    }
}
