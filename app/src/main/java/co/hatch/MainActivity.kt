package co.hatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import co.hatch.view.AppNavHost
import co.hatch.viewmodel.DeviceViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content view for the activity using Jetpack Compose
        setContent {

            MaterialTheme {

                // Provide the DeviceViewModel to the Composable tree
                val viewModel = DeviceViewModel()

                // Render the main navigation host for the app
                AppNavHost(viewModel = viewModel)
            }
        }
    }
}
