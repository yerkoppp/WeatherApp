package dev.ycosorio.coroutines.data.repository

import dev.ycosorio.coroutines.BuildConfig
import dev.ycosorio.coroutines.data.WeatherData
import dev.ycosorio.coroutines.data.remote.WeatherApiService
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * WeatherRepository
 *
 * Responsabilidad: Manejar el acceso a datos del clima.
 * - Consume datos de una API real con Retrofit.
 * - Obtiene la API Key de forma segura desde BuildConfig.
 */
class WeatherRepository {

    // ------------------- CONFIGURACIÓN DE RETROFIT -------------------

    private val apiService: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    private val apiKey = BuildConfig.API_WEATHER

    // ------------------- FUNCIONES PÚBLICAS -------------------

    /**
     * Obtiene el clima de una ciudad específica desde la API.
     * Esta es la función principal que realiza la llamada de red.
     */
    suspend fun fetchCityWeather(cityName: String): WeatherData {

        delay(3000) // Simula 3 segundos de carga para demostración
        val response = apiService.getWeatherByCity(cityName, apiKey)

        // Mapeamos la respuesta de la API a nuestro modelo de datos interno (WeatherData).
        return WeatherData(
            city = response.name,
            temperature = response.main.temp.toInt(),
            description = response.weather.firstOrNull()?.description?.replaceFirstChar { it.uppercase() } ?: "No disponible",
            humidity = response.main.humidity,
            windSpeed = response.wind.speed
        )
    }


}