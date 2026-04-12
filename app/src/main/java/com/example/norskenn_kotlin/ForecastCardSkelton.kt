package com.example.norskenn_kotlin

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ForecastCardSkeleton() {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .height(85.dp)
            .fillMaxWidth(0.98f)
            .border(2.dp, Color(0x33FFFFFF), RoundedCornerShape(8.dp)),
        //.alpha(0.8f),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x883E689F))
        //elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(

        ) {
            Column(
            ) {
                Row(
                ) {
                }
            }
        }
    }
}

