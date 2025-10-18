package dev.ycosorio.coroutines.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.ycosorio.coroutines.view.components.StatusCard
import dev.ycosorio.coroutines.view.components.WeatherCard
import dev.ycosorio.coroutines.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel = viewModel()
) {
    val uiState by weatherViewModel.uiState.collectAsState()
    var cityInput by remember { mutableStateOf("") }

    // Lógica para mostrar datos de una o varias ciudades
    val city: String
    val temperature: String
    val description: String

    if (uiState.weatherDataList.isNotEmpty()) {
        val firstCity = uiState.weatherDataList.first()
        city = "Chile (Varias)"
        temperature = "${firstCity.temperature}°C"
        description = uiState.weatherDataList.joinToString(separator = "\n") {
            "📍 ${it.city}: ${it.temperature}°C - ${it.description}"
        }
    } else {
        city = uiState.weatherData?.city ?: "--"
        temperature = uiState.weatherData?.let { "${it.temperature}°C" } ?: "--"
        description = uiState.weatherData?.let {
            """
            ${it.description}
            💧 Humedad: ${it.humidity}%
            💨 Viento: ${it.windSpeed} km/h
            """.trimIndent()
        } ?: "Esperando datos..."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "☀️ Weather App", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1976D2))
        WeatherCard(city = city, temperature = temperature, description = description)
        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp))
        }

        StatusCard(uiState.statusMessage)
        Spacer(modifier = Modifier.height(24.dp))

        // --- CAMPO DE BÚSQUEDA Y BOTÓN ---
        OutlinedTextField(
            value = cityInput,
            onValueChange = { cityInput = it },
            label = { Text("Buscar una ciudad...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !uiState.isLoading
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (cityInput.isNotBlank()) {
                    weatherViewModel.loadWeatherForCity(cityInput)
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            enabled = !uiState.isLoading && cityInput.isNotBlank()
        ) {
            Text("🔍 Buscar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        // --- FIN DE CAMPO DE BÚSQUEDA ---

        // Botón para cargar múltiples ciudades (demuestra async/await)
        Button(
            onClick = { weatherViewModel.loadMultipleWeather() },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
            enabled = !uiState.isLoading
        ) {
            Text("🌍 Cargar 3 Ciudades (Paralelo)")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Botón para cancelar
        Button(
            onClick = { weatherViewModel.cancelJob() },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
            enabled = uiState.isLoading
        ) {
            Text("❌ Cancelar Operación")
        }
    }
}