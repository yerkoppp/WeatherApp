package dev.ycosorio.coroutines.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Card que muestra los datos del clima
 */
@Composable
fun WeatherCard(city: String, temperature: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = city,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = temperature,
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF6F00)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = description,
                fontSize = 16.sp,
                color = Color(0xFF666666),
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }
    }
}

