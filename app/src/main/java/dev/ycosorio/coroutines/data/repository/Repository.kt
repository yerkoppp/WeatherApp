package dev.ycosorio.coroutines.data.repository

import dev.ycosorio.coroutines.data.WeatherData
import kotlinx.coroutines.delay

/**
 * Simula una llamada a API REST
 *
 * suspend: Función que puede suspenderse
 * - No bloquea el hilo
 * - Solo puede llamarse desde coroutines
 * - delay() pausa la coroutine pero el hilo queda libre
 */
private suspend fun simulateApiCall(): WeatherData {
    delay(3000) // Simula latencia de red (3 segundos)
    return WeatherData(
        city = "Santiago",
        temperature = 22,
        description = "Parcialmente nublado",
        humidity = 65,
        windSpeed = 12.5
    )
}

/**
 * Obtiene clima de una ciudad específica
 */
private suspend fun fetchCityWeather(cityName: String): WeatherData {
    delay(2000) // Simula latencia
    return WeatherData(
        city = cityName,
        temperature = (15..30).random(),
        description = listOf("Soleado", "Nublado", "Lluvia ligera").random(),
        humidity = (40..80).random(),
        windSpeed = (5..20).random().toDouble()
    )
}
