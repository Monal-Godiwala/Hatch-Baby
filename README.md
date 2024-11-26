# Hatch Android Homework Project

## Overview
This project is a simple Android application designed to showcase the ability to work with legacy code, implement scalable solutions, and use modern Android development tools. The application fulfills the functional requirements outlined in the assignment, with partial implementation of the nice-to-have features.

---

## Functional Requirements

### Core Requirements
1. **List Devices**:
   - Used `ConnectivityClient#discoverDevices()` to fetch and list available devices.
   - Displayed device name and RSSI values in the list.
   - Ordered devices by RSSI in descending order, with the strongest signal at the top.
   - Added a loading indicator during data fetch and displayed appropriate messages when no data is available.

2. **Refresh List**:
   - Implemented periodic updates to the device list every 10 seconds to reflect changes in available devices.

3. **Detail Screen (Basic)**:
   - Added a secondary screen to display additional details about a selected device.
   - Used `ConnectivityClient#connectToDeviceBy` to fetch and display detailed device information.

### Nice-to-Have Features
1. **Implemented Features**:
   - Integrated `connectToDeviceBy` and `disconnectFromDevice` methods to allow establishing and terminating connections with devices.

2. **Pending Features**:
   - Allowing the user to rename a device on the detail screen and ensuring the updated name is reflected on the list screen is partially implemented.
   - Add Unit test cases for the ViewModel to verify the list sorting and periodic refresh logic.
- Due to time constraints, this functionality is incomplete.

---

## Technical Overview

### Architecture
- **Pattern**: Followed the MVVM architecture to ensure scalability and separation of concerns.
- **Components**:
   - ViewModel for managing UI-related data and handling periodic refresh logic.

### Technology Stack
- **UI**: Built using Jetpack Compose for modern, declarative, and reactive UI development.
- **Async Operations**: Utilized Kotlin Coroutines for managing asynchronous data fetching and refreshing the device list.

---

## Known Limitations and Future Work
1. **Rename Device**:
   - The ability to rename a device on the detail screen and reflect the updated name in the list view is partially implemented.
   - This functionality can be completed using `ConnectivityClient#updateDeviceName` and updating the ViewModel state.

2. **UI Enhancements**:
   - Further visual improvements can be made to enhance the user experience.

3. **Robust Error Handling**:
   - Current error handling is basic. Future work could include:
      - More descriptive error messages.

---

## How to Run
1. Clone the repository and open the project in Android Studio.
2. Build and run the project on a physical or virtual Android device.
3. Interact with the app to:
   - View the list of devices.
   - Tap a device to view its details.
   - Observe automatic refresh of the list every 10 seconds.

---

## Conclusion
This project demonstrates the ability to:
- Work with legacy code.
- Implement modern Android development practices.

I look forward to discussing the project further and exploring areas for improvement.
