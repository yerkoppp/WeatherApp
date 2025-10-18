package dev.ycosorio.coroutines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.ycosorio.coroutines.ui.theme.WeatherAppTheme
import dev.ycosorio.coroutines.view.WeatherScreen

/**
 * MainActivity con Jetpack Compose
 *
 * CONCEPTOS CLAVE COMPOSE + COROUTINES:
 * - State: Variables observables que recomponen la UI
 * - LaunchedEffect: Ejecuta coroutines vinculadas a composables
 * - rememberCoroutineScope: Scope para coroutines en composables
 * - lifecycleScope: Scope atado al ciclo de vida (desde Activity)
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFE3F2FD)
                ) {
                    WeatherScreen()
                }
            }
        }
    }
}



