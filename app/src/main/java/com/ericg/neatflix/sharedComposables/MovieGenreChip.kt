package com.ericg.neatflix.sharedComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MovieGenreChip(
    genre: String,
    background: Color = Color(0XFFC9F964).copy(alpha = 0.16F),
    textColor: Color = Color(0XFFC9F964)
) {
    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .widthIn(min = 80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(background)
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = genre,
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontWeight = FontWeight.Thin,
            modifier = Modifier.padding(horizontal = 2.dp)
        )
    }
}
