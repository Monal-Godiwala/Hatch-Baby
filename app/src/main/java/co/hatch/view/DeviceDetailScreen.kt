import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
    LaunchedEffect(deviceId) {
        viewModel.selectDevice(deviceId)
    }

    val selectedDevice by viewModel.selectedDevice.collectAsState()

    selectedDevice?.let { device ->
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
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Name: ${device.name}", style = MaterialTheme.typography.h5)
                Text(
                    text = "Connection Status: ${if (device.connected) "Connected" else "Disconnected"}",
                    color = if (device.connected) Color.Green else Color.Red
                )
                Text(text = "Device ID: ${device.id}", style = MaterialTheme.typography.body1)
                Text(
                    text = "Latest Connected: ${
                        device.latestConnectedTime?.let {
                            java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .format(it)
                        }
                    }",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "Elapsed Connected: ${device.elapsedSecsConnected / 3600} hrs, " +
                            "${(device.elapsedSecsConnected % 3600) / 60} mins",
                    style = MaterialTheme.typography.body1
                )
                Text(text = "RSSI: ${device.rssi}", style = MaterialTheme.typography.body1)
            }
        }
    }
}
