package com.ericg.neatflix.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericg.neatflix.R
import com.ericg.neatflix.screens.destinations.WatchListDestination
import com.ericg.neatflix.sharedComposables.BackButton
import com.ericg.neatflix.sharedComposables.CustomSwitch
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.AppPrimaryColor
import com.ericg.neatflix.ui.theme.ButtonColor
import com.ericg.neatflix.viewmodel.PrefsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Destination
@Composable
fun Profile(
    navigator: DestinationsNavigator,
    prefsViewModel: PrefsViewModel = hiltViewModel()
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(AppPrimaryColor)
    ) {
        val (
            backButton,
            editButton,
            profileHeading,
            userName,
            topBgImage,
            profilePhoto,
            imageBoarder,
            translucentBr,
            btnWatchList,
            settingsBox,
            appVersion
        ) = createRefs()

        BackButton(
            modifier = Modifier
                .constrainAs(backButton) {
                    start.linkTo(parent.start, margin = 10.dp)
                    top.linkTo(parent.top, margin = 16.dp)
                }) {
            navigator.navigateUp()
        }

        FloatingActionButton(
            modifier = Modifier
                .size(42.dp)
                .constrainAs(editButton) {
                    end.linkTo(parent.end, margin = 10.dp)
                    top.linkTo(parent.top, margin = 16.dp)
                },
            backgroundColor = ButtonColor,
            contentColor = AppOnPrimaryColor,
            onClick = { }) {
            Icon(imageVector = Icons.Rounded.Edit, contentDescription = "edit profile")
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
                .fillMaxHeight(0.27F)
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
                        ),
                        startY = 0F
                    )
                )
                .constrainAs(translucentBr) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(topBgImage.bottom)
                }
        )

        /** Custom boarder -> Reason: The default Image  boarder wasn't working properly */
        Box(
            modifier = Modifier
                .size(83.5.dp)
                .clip(CircleShape)
                .background(AppPrimaryColor)
                .constrainAs(imageBoarder) {
                    top.linkTo(topBgImage.bottom)
                    start.linkTo(topBgImage.start, margin = 26.dp)
                    bottom.linkTo(topBgImage.bottom)
                }
        )

        CoilImage(
            imageModel = R.drawable.ic_user,
            previewPlaceholder = R.drawable.ic_user,
            contentScale = ContentScale.Crop,
            circularReveal = CircularReveal(duration = 1000),
            shimmerParams = ShimmerParams(
                baseColor = AppOnPrimaryColor,
                highlightColor = Color.LightGray,
                durationMillis = 500,
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
            return if (name.length <= 16) name else {
                name.removeRange(15..name.lastIndex) + "..."
            }
        }

        Text(
            text = userName("NeatFlix User"),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = AppOnPrimaryColor,
            modifier = Modifier.constrainAs(userName) {
                top.linkTo(profilePhoto.bottom, margin = 4.dp)
                start.linkTo(profilePhoto.start)
                end.linkTo(profilePhoto.end)
            }
        )

        Button(
            modifier = Modifier
                .constrainAs(btnWatchList) {
                    end.linkTo(parent.end, margin = 24.dp)
                    top.linkTo(topBgImage.bottom)
                    bottom.linkTo(topBgImage.bottom)
                },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF4C3D6D),
                contentColor = AppOnPrimaryColor
            ),
            onClick = {
                navigator.navigate(WatchListDestination)
            }
        ) {
            Text(text = "My List")
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
                text = "v: 1.0-beta [dummy profile]",
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = AppOnPrimaryColor.copy(alpha = 0.5F)
            )
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(12.dp))
                .background(Color(0XFF423460).copy(alpha = 0.46F))
                .heightIn(min = 100.dp)
                .constrainAs(settingsBox) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(appVersion.top)
                    top.linkTo(profilePhoto.bottom)
                }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Preferences",
                    modifier = Modifier.padding(vertical = 12.dp),
                    fontSize = 18.sp,
                    color = AppOnPrimaryColor,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(
                    modifier = Modifier
                        .height((0.5).dp)
                        .fillMaxWidth(1F)
                        .background(AppOnPrimaryColor.copy(alpha = 0.15F))
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Enable Adult Search",
                        fontSize = 18.sp,
                        color = AppOnPrimaryColor,
                        fontWeight = FontWeight.Light
                    )

                    val enableAdultSearch =
                        prefsViewModel.includeAdult.value.collectAsState(initial = true)

                    CustomSwitch(
                        checkable = true,
                        checked = enableAdultSearch.value ?: true,
                        onCheckedChange = {
                            prefsViewModel.updateIncludeAdult(
                                enableAdultSearch.value?.not() ?: false
                            )
                        })
                }
                Spacer(
                    modifier = Modifier
                        .height((0.5).dp)
                        .fillMaxWidth(0.8F)
                        .background(AppOnPrimaryColor.copy(alpha = 0.12F))
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Use Device Theme",
                        fontSize = 18.sp,
                        color = AppOnPrimaryColor,
                        fontWeight = FontWeight.Light
                    )

                    CustomSwitch(
                        checkable = remember { false },
                        checked = false,
                        onCheckedChange = {

                        })
                }
            }
        }
    }
}
