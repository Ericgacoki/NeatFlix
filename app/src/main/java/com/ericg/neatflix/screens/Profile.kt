package com.ericg.neatflix.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ericg.neatflix.R
import com.ericg.neatflix.sharedComposables.BackButton
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.AppPrimaryColor
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun Profile() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(AppPrimaryColor)
    ) {
        val (
            backButton,
            profileHeading,
            userName,
            topBgImage,
            profilePhoto,
            imageBoarder,
            translucentBr,
            viewedMoviesIcon,
            favMoviesIcon,
            appVersion) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(backButton) {
                    start.linkTo(parent.start, margin = 10.dp)
                    top.linkTo(parent.top, margin = 16.dp)
                }
        ) {
            BackButton {

            }
        }

        Text(
            modifier = Modifier.constrainAs(profileHeading) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(backButton.top)
                bottom.linkTo(backButton.bottom)
            },
            text = "Profile",
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppOnPrimaryColor
        )

        Image(
            painter = painterResource(id = R.drawable.popcorn),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3F)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .constrainAs(topBgImage) {
                    top.linkTo(backButton.bottom, margin = 16.dp)
                },
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color(0XFF180E36).copy(alpha = 0.5F),
                            Color(0XFF180E36).copy(alpha = 0.75F),
                            Color(0XFF180E36).copy(alpha = 1F)
                        ), startY = 0F
                    )
                )
                .constrainAs(translucentBr) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(topBgImage.bottom)
                }
        )

        /** Custom boarder -> Reason: Image boarder wasn't working properly */
        Box(
            modifier = Modifier
                .size(83.5.dp).clip(CircleShape)
                .background(AppPrimaryColor)
                .constrainAs(imageBoarder) {
                    top.linkTo(topBgImage.bottom)
                    start.linkTo(topBgImage.start, margin = 26.dp)
                    bottom.linkTo(topBgImage.bottom)
                }
        )

        CoilImage(
            imageModel = R.drawable.timothee,
            previewPlaceholder = R.drawable.timothee,
            contentScale = ContentScale.Crop,
            circularReveal = CircularReveal(duration = 1000),
            shimmerParams = ShimmerParams(
                baseColor = AppOnPrimaryColor,
                highlightColor = Color.LightGray,
                durationMillis = 350,
                dropOff = 0.65F,
                tilt = 20F
            ),
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .constrainAs(profilePhoto) {
                    top.linkTo(topBgImage.bottom)
                    start.linkTo(topBgImage.start, margin = 28.dp)
                    bottom.linkTo(topBgImage.bottom)
                },
            contentDescription = null
        )

        fun userName(name: String): String {
            return if (name.length <= 10) name else {
                name.removeRange(9..name.lastIndex) + "..."
            }
        }

        Text(
            text = userName("John Doe"),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = AppOnPrimaryColor,
            modifier = Modifier.constrainAs(userName) {
                top.linkTo(profilePhoto.bottom, margin = 4.dp)
                start.linkTo(profilePhoto.start)
                end.linkTo(profilePhoto.end)
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.constrainAs(viewedMoviesIcon) {
                start.linkTo(profilePhoto.end, margin = 36.dp)
                top.linkTo(topBgImage.bottom)
                bottom.linkTo(profilePhoto.bottom)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_eyes),
                contentDescription = null,
                tint = AppOnPrimaryColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "12",
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = AppOnPrimaryColor
            )

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.constrainAs(favMoviesIcon) {
                start.linkTo(viewedMoviesIcon.end)
                top.linkTo(topBgImage.bottom)
                bottom.linkTo(profilePhoto.bottom)
                end.linkTo(parent.end, margin = 24.dp)
            }
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = AppOnPrimaryColor
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "34",
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = AppOnPrimaryColor
            )
        }
        Column(
            modifier = Modifier.constrainAs(appVersion) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 24.dp)
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.neatflix_logo_large),
                modifier = Modifier.widthIn(max = 100.dp),
                alpha = 0.78F,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "v: 0.0.1-alpha",
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = AppOnPrimaryColor.copy(alpha = 0.78F)
            )
        }
    }


}

@Preview(device = Devices.PIXEL_3)
@Composable
fun ProfilePreview() {
    Profile()
}