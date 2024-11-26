package co.hatch.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.hatch.deviceClientLib.model.Device
import co.hatch.viewmodel.DeviceViewModel

@Composable
fun DeviceListScreen(
    viewModel: DeviceViewModel,
    onDeviceClick: (String) -> Unit // Callback for navigating to the detail screen
) {
    // Observe the list of devices from the ViewModel
    val devices by viewModel.deviceList.collectAsState()
    // Observe the loading state to show an indicator if data is being refreshed
    val isLoading by viewModel.isLoading.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                // Display loading indicator
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            devices.isEmpty() -> {
                // Show a message if there are no devices to display
                Text(
                    text = "No devices available.",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                // Display the device list
                LazyColumn {
                    items(devices) { device ->
                        DeviceListItem(device = device, onClick = { onDeviceClick(device.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun DeviceListItem(device: Device, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }, // Navigate to detail when clicked
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = device.name, style = MaterialTheme.typography.h6)
            Text(text = "RSSI: ${device.rssi}", style = MaterialTheme.typography.body2)
        }
    }
}
