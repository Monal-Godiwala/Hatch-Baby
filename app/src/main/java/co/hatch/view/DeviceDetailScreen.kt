import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.hatch.deviceClientLib.model.Device
import co.hatch.viewmodel.DeviceViewModel
import java.text.SimpleDateFormat

@Composable
fun DeviceDetailScreen(
    viewModel: DeviceViewModel,
    deviceId: String,
    onBackClick: () -> Unit
) {

    // LaunchedEffect to trigger data loading whenever the deviceId changes
    LaunchedEffect(deviceId) {
        viewModel.selectDevice(deviceId)
    }

    // Observe the selected device's state from the ViewModel
    val selectedDevice by viewModel.selectedDevice.collectAsState()

    // Determine if the screen should show a loading state
    val isLoading = selectedDevice == null

    Scaffold(
        topBar = {
            // Top App Bar with a back button and a title
            TopAppBar(
                title = { Text(text = "Device Details") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                isLoading -> {
                    // Show a loading indicator when the device is being loaded
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                selectedDevice != null -> {
                    // Display device details
                    DeviceDetailsContent(selectedDevice)
                }

                else -> {
                    // Display an error message if the device data is unavailable
                    Text(
                        text = "Error loading device details",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

@Composable
private fun DeviceDetailsContent(selectedDevice: Device?) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Name: ${selectedDevice?.name}",
            style = MaterialTheme.typography.h5
        )
        Text(
            text = "Connection Status: ${if (selectedDevice?.connected == true) "Connected" else "Disconnected"}",
            color = if (selectedDevice?.connected == true) Color.Green else Color.Red
        )
        Text(
            text = "Device ID: ${selectedDevice?.id}",
            style = MaterialTheme.typography.body1
        )
        Text(
            text = "Latest Connected: ${
                selectedDevice?.latestConnectedTime?.let {
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(it)
                }
            }",
            style = MaterialTheme.typography.body1
        )
        Text(
            text = "Elapsed Connected: ${
                selectedDevice?.elapsedSecsConnected?.div(
                    3600
                )
            } hrs, " +
                    "${(selectedDevice?.elapsedSecsConnected?.rem(3600))?.div(60)} mins",
            style = MaterialTheme.typography.body1
        )
        Text(
            text = "RSSI: ${selectedDevice?.rssi}",
            style = MaterialTheme.typography.body1
        )
    }
}
