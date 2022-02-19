package com.ericg.neatflix.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericg.neatflix.R

@Composable
fun Home() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180E36))
    ) {
        SearchBarAndProfile()
    }
}

@Composable
fun SearchBarAndProfile() {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(0.78F)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0XFF423460))
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(34.dp),
                    tint = Color.White.copy(alpha = 0.48F),
                    contentDescription = "search icon"
                )
                Text(
                    text = "Search...",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White.copy(alpha = 0.48F)
                )
                DropdownMenu()
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentDescription = "profile"
        )
    }
}

@Composable
fun DropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Movies", "Series")
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxHeight()
            .width(80.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFF180E36))
    ) {
        Text(
            items[selectedIndex],
            color = Color.White.copy(alpha = 0.78F),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(4.dp)
                .clickable(onClick = { expanded = true }),
            textAlign = TextAlign.Center
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color(0XFF423460))
                .clip(RoundedCornerShape(4.dp))
        ) {
            items.forEachIndexed { index, value ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    selectedIndex = index
                }) {
                    Text(
                        text = value,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                    )
                }
                if (index != items.lastIndex) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp)
                            .height(.5.dp)
                            .background(Color(0XFF9495B1))
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePrev() {
    Home()
}