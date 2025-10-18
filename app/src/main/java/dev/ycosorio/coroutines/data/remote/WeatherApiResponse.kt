package dev.ycosorio.coroutines.data.remote

data class WeatherApiResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

data class Main(
    val temp: Double,
    val humidity: Int
)

data class Weather(
    val description: String,
    val icon: String // Opcional, para mostrar un ícono del clima
)

data class Wind(
    val speed: Double
)