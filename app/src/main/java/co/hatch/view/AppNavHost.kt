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
            DeviceListScreen(viewModel = viewModel) { deviceId ->
                viewModel.disConnectDevice()
                navController.navigate("device_detail/$deviceId")
            }
        }
        composable(
            route = "device_detail/{deviceId}",
            arguments = listOf(navArgument("deviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getString("deviceId") ?: return@composable
            DeviceDetailScreen(
                viewModel = viewModel,
                deviceId = deviceId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
