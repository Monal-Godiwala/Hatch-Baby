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

    companion object {
        const val REFRESH_INTERVAL_MILLI_SECOND: Long = 10_000
    }

    // ConnectivityClient class object for Model Layer including data and repository
    private val connectivityClient = ConnectivityClient.Factory.create()

    // State for the list of devices
    private val _deviceList = MutableStateFlow<List<Device>>(emptyList())
    val deviceList: StateFlow<List<Device>> = _deviceList

    // State to track if the device list is currently being loaded
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // State for the currently selected device
    private val _selectedDevice = MutableStateFlow<Device?>(null)
    val selectedDevice: StateFlow<Device?> = _selectedDevice

    // State for the previously selected device
    private val _previousSelectedDeviceId = MutableStateFlow<String?>(null)

    init {
        // Initialize the device list when the ViewModel is created
        startDeviceListRefresh()
    }

    /**
     * Refreshes the device list on specified interval
     */
    private fun startDeviceListRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                refreshDeviceList()
                delay(REFRESH_INTERVAL_MILLI_SECOND)
            }
        }
    }

    /**
     * Refreshes the list of devices by fetching data from the repository.
     * Updates the `_deviceList` state with the fetched data.
     */
    private fun refreshDeviceList() {
        _isLoading.value = true
        try {
            _deviceList.value =
                connectivityClient.discoverDevices()
                    .sortedByDescending { device -> device.rssi }
        } catch (e: Exception) {
            _deviceList.value = emptyList()
        } finally {
            _isLoading.value = false
        }
    }

    /**
     * Selects a device by its ID and fetches its details from the repository.
     * Updates the `_selectedDevice` state with the fetched device.
     *
     * @param deviceId The ID of the device to select.
     */
    fun selectDevice(selectedDeviceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedDevice.value = null
            connectivityClient.connectToDeviceBy(selectedDeviceId,
                object : ConnectivityClient.OnDeviceStateChangeListener {
                    override fun onDeviceStateChanged(deviceId: String, device: Device) {
                        _selectedDevice.value = device
                        _previousSelectedDeviceId.value = deviceId
                    }
                })
        }
    }

    /**
     * Disconnects the previously selected Device object
     */
    fun disconnectDevice() {
        viewModelScope.launch(Dispatchers.IO) {
            _previousSelectedDeviceId.value?.let { connectivityClient.disconnectFromDevice(it) }
        }
    }

}