package dev.ycosorio.coroutines.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ycosorio.coroutines.data.WeatherData
import dev.ycosorio.coroutines.data.repository.WeatherRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Representa todo el estado de la pantalla del clima.
 * Es una única fuente de verdad para la UI.
 */
data class WeatherUiState(
    val isLoading: Boolean = false,
    val weatherData: WeatherData? = null,
    val weatherDataList: List<WeatherData> = emptyList(),
    val statusMessage: String = "⏳ Presiona un botón o busca una ciudad"
)

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private var weatherJob: Job? = null

    /**
     * Carga el clima de una ciudad específica ingresada por el usuario.
     * DEMUESTRA: `launch`, `withContext`, manejo de `Job` y cancelación.
     */
    fun loadWeatherForCity(cityName: String) {
        weatherJob?.cancel() // Cancela cualquier operación anterior
        weatherJob = viewModelScope.launch {
            _uiState.value = WeatherUiState(isLoading = true, statusMessage = "🔄 Consultando clima para $cityName...")
            try {
                // Llama al repositorio en el hilo de IO
                val data = withContext(Dispatchers.IO) {
                    repository.fetchCityWeather(cityName)
                }
                // Actualiza el estado con los datos recibidos
                _uiState.value = WeatherUiState(weatherData = data, statusMessage = "✅ Datos de $cityName cargados")
            } catch (e: CancellationException) {
                _uiState.value = _uiState.value.copy(isLoading = false, statusMessage = "❌ Operación cancelada")
                throw e
            } catch (e: Exception) {
                // Maneja cualquier otro error (ej: ciudad no encontrada, sin internet)
                _uiState.value = _uiState.value.copy(isLoading = false, statusMessage = "❌ Error: ${e.message}")
            }
        }
    }

    /**
     * Carga el clima de múltiples ciudades en paralelo.
     * DEMUESTRA: `async`, `await` para concurrencia.
     */
    fun loadMultipleWeather() {
        weatherJob?.cancel()
        weatherJob = viewModelScope.launch {
            _uiState.value = WeatherUiState(isLoading = true, statusMessage = "🔄 Cargando múltiples ciudades en paralelo...")
            try {
                // Inicia todas las llamadas en paralelo en el hilo de IO
                val santiagoDeferred = async(Dispatchers.IO) { repository.fetchCityWeather("Santiago") }
                val valparaisoDeferred = async(Dispatchers.IO) { repository.fetchCityWeather("Valparaíso") }
                val concepcionDeferred = async(Dispatchers.IO) { repository.fetchCityWeather("Concepción") }

                // Espera a que todas terminen
                val cities = listOf(santiagoDeferred.await(), valparaisoDeferred.await(), concepcionDeferred.await())
                _uiState.value = WeatherUiState(weatherDataList = cities, statusMessage = "✅ 3 ciudades cargadas en paralelo")

            } catch (e: CancellationException) {
                _uiState.value = _uiState.value.copy(isLoading = false, statusMessage = "❌ Carga múltiple cancelada")
                throw e
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, statusMessage = "❌ Error: ${e.message}")
            }
        }
    }

    /**
     * Cancela la coroutine que se esté ejecutando.
     * DEMUESTRA: Cancelación explícita del Job.
     */
    fun cancelJob() {
        weatherJob?.cancel()
    }
}