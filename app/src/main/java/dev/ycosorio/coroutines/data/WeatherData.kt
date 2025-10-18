package dev.ycosorio.coroutines.data

data class WeatherData(
    val city: String,
    val temperature: Int,
    val description: String,
    val humidity: Int,
    val windSpeed: Double
)