package dev.ycosorio.coroutines.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric", // Para recibir T° en Celsius
        @Query("lang") lang: String = "es"      // Para descripción en español
    ): WeatherApiResponse // Un nuevo data class para la respuesta
}