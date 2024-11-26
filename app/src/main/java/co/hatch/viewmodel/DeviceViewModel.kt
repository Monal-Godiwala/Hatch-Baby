package co.hatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.hatch.deviceClientLib.connectivity.ConnectivityClient
import co.hatch.deviceClientLib.model.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeviceViewModel : ViewModel() {

    private val connectivityClient = ConnectivityClient.Factory.create()

    private val _deviceList = MutableStateFlow<List<Device>>(emptyList())
    val deviceList: StateFlow<List<Device>> = _deviceList

    private val _selectedDevice = MutableStateFlow<Device?>(null)
    val selectedDevice: StateFlow<Device?> = _selectedDevice

    init {
        startDeviceListRefresh()
    }

    private fun startDeviceListRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                _deviceList.value =
                    connectivityClient.discoverDevices()
                        .sortedByDescending { device -> device.rssi }
                delay(10_000) // 10 seconds
            }
        }
    }

    fun selectDevice(selectedDeviceId: String) {
        _selectedDevice.value = null
        viewModelScope.launch(Dispatchers.IO) {
            connectivityClient.connectToDeviceBy(selectedDeviceId,
                object : ConnectivityClient.OnDeviceStateChangeListener {
                    override fun onDeviceStateChanged(deviceId: String, device: Device) {
                        _selectedDevice.value = device
                    }
                })
        }
    }

}