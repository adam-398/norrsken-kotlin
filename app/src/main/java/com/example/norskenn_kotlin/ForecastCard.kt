package com.example.norskenn_kotlin

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
fun ForecastCard(item: ForecastItem) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .height(90.dp)
            .fillMaxWidth(0.98f)
            .border(2.dp, Color(0x33FFFFFF), RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x771a3060))
    ) {
        Row (
            modifier = Modifier
                .weight(1f)
            .padding(all = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            ) {
                Text(
                    text = formatDate(item.time),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .alpha(0.8f),
                    color = Color.White,
                    fontSize = 13.sp,
                )
                Text(
                    text = formatTime(item.time),
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = "${item.temperature}°C",
                color = ColorChangingText(item.temperature),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Row (
                Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "${item.precipitation} mm",
                    color = Color.White,
                    modifier = Modifier.alpha(0.6f),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                )
                AsyncImage(
                    model = "https://raw.githubusercontent.com/metno/weathericons/main/weather/png/${item.symbolCode}.png",
                    contentDescription = item.symbolCode,
                    modifier = Modifier
                        .padding(start = 25.dp)
                        .size(75.dp)
                )

            }

        }
    }

}

fun formatDate(isoTime: String): String {
    if (isoTime.isEmpty()) return ""
    val parsed = ZonedDateTime.parse(isoTime)
    return parsed.format(DateTimeFormatter.ofPattern("dd MMMM"))
}

fun formatTime(isoTime: String): String {
    if (isoTime.isEmpty()) return ""
    val parsed = ZonedDateTime.parse(isoTime)
    return parsed.format(DateTimeFormatter.ofPattern("HH:mm"))
}
