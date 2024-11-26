package co.hatch.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.hatch.deviceClientLib.model.Device
import co.hatch.viewmodel.DeviceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceDetailScreen(
    viewModel: DeviceViewModel,
    deviceId: String,
    onBackClick: () -> Unit
) {

    LaunchedEffect(deviceId) {
        viewModel.selectDevice(deviceId)
    }

    val selectedDevice: Device? by viewModel.selectedDevice.observeAsState()

    selectedDevice?.let { device ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Device Details") },
                    navigationIcon = {
                        IconButton(onClick = { onBackClick() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
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
                Text(
                    text = "Name: ${device.name}",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Connection Status: ${if (device.connected) "Connected" else "Disconnected"}",
                    color = if (device.connected) Color.Green else Color.Red
                )
                Text(
                    text = "Device ID: ${device.id}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Latest Connected: ${
                        java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(device.latestConnectedTime)
                    }",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Elapsed Connected: ${device.elapsedSecsConnected / 3600} hrs, " +
                            "${(device.elapsedSecsConnected % 3600) / 60} mins",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = "RSSI: ${device.rssi}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
