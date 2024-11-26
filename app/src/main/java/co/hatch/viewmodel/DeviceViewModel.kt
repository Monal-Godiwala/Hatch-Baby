package co.hatch.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.hatch.deviceClientLib.connectivity.ConnectivityClient
import co.hatch.deviceClientLib.model.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeviceViewModel : ViewModel() {

    private val connectivityClient = ConnectivityClient.Factory.create()

    private val _deviceList = MutableLiveData<List<Device>>(emptyList())
    val deviceList: LiveData<List<Device>> = _deviceList

    private val _selectedDevice = MutableLiveData<Device?>(null)
    val selectedDevice: LiveData<Device?> = _selectedDevice

    init {
        loadDevices()
    }

    private fun loadDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            _deviceList.postValue(
                connectivityClient.discoverDevices().sortedByDescending { device -> device.rssi })
        }
    }

    fun selectDevice(selectedDeviceId: String) {
        Log.e("Monal", "Called: $selectedDeviceId")
        _selectedDevice.value = null
        viewModelScope.launch(Dispatchers.IO) {
            connectivityClient.connectToDeviceBy(selectedDeviceId,
                object : ConnectivityClient.OnDeviceStateChangeListener {
                    override fun onDeviceStateChanged(deviceId: String, device: Device) {
                        if (deviceId == selectedDeviceId) {
                            _selectedDevice.postValue(device)
                        }
                    }
                })
        }
    }

}