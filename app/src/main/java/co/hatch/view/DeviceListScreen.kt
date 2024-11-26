package co.hatch.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.hatch.deviceClientLib.model.Device
import co.hatch.viewmodel.DeviceViewModel

@Composable
fun DeviceListScreen(
    viewModel: DeviceViewModel,
    onDeviceClick: (String) -> Unit
) {
    val devices by viewModel.deviceList.collectAsState()

    Column {
        Text(
            text = "Refreshing device list every 10 seconds...",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(8.dp)
        )
        LazyColumn {
            items(items = devices) { device ->
                DeviceListItem(device = device, onClick = { onDeviceClick(device.id) })
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
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = device.name, style = MaterialTheme.typography.h6)
                Text(
                    text = if (device.connected) "Connected" else "Disconnected",
                    color = if (device.connected) Color.Green else Color.Red
                )
            }
            Text(text = "RSSI: ${device.rssi}", style = MaterialTheme.typography.body2)
        }
    }
}
