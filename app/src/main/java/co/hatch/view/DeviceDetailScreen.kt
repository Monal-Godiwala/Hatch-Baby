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
import co.hatch.viewmodel.DeviceViewModel

@Composable
fun DeviceDetailScreen(
    viewModel: DeviceViewModel,
    deviceId: String,
    onBackClick: () -> Unit
) {

    // Trigger data loading when the screen is displayed
    LaunchedEffect(deviceId) {
        viewModel.selectDevice(deviceId)
    }

    val selectedDevice by viewModel.selectedDevice.collectAsState()

    val isLoading = selectedDevice == null

    Scaffold(
        topBar = {
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
                                    java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
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
            }
        }
    }
}
