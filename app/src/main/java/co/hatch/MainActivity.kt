package co.hatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import co.hatch.view.AppNavHost
import co.hatch.viewmodel.DeviceViewModel

class MainActivity : ComponentActivity() {
    private val viewModel = DeviceViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavHost(viewModel = viewModel)
            }
        }
    }
}
