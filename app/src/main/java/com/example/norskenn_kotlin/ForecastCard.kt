package com.example.norskenn_kotlin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
fun ForecastCard(item: ForecastItem) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .height(250.dp)
            .width(150.dp),
            //.alpha(0.8f),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x882a4f80))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = formatDate(item.time),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = formatTime(item.time),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "${item.temperature}°C",
                color = ColorChangingText(item.temperature),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "${item.precipitation} mm",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            AsyncImage(
                model = "https://raw.githubusercontent.com/metno/weathericons/main/weather/png/${item.symbolCode}.png",
                contentDescription = item.symbolCode,
                modifier = Modifier
                    .size(75.dp)
            )
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
