package co.hatch.view

import DeviceDetailScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.hatch.viewmodel.DeviceViewModel

@Composable
fun AppNavHost(viewModel: DeviceViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "device_list") {
        composable("device_list") {
            // Pass the ViewModel to the screen to display and manage the list of devices
            DeviceListScreen(viewModel = viewModel) { deviceId ->
                // Disconnect the previously connected device
                viewModel.disconnectDevice()
                // Navigate to the detail screen for the selected device
                navController.navigate("device_detail/$deviceId")
            }
        }

        // Accepts a parameter (deviceId) to identify the device to display
        composable(
            route = "device_detail/{deviceId}",
            arguments = listOf(navArgument("deviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Retrieve the deviceId from the navigation arguments
            val deviceId = backStackEntry.arguments?.getString("deviceId") ?: return@composable
            DeviceDetailScreen(
                viewModel = viewModel,
                deviceId = deviceId,
                onBackClick = {
                    // Navigate back to the previous screen (Device List)
                    navController.popBackStack()
                }
            )
        }
    }
}
