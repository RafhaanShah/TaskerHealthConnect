# Health Connect Plugin for Tasker

[Tasker](https://tasker.joaoapps.com/) plugin to interface with [Health Connect](https://developer.android.com/health-connect) on Android

## Current Features
- Retrieve [Aggregate data](https://developer.android.com/health-and-fitness/guides/health-connect/develop/aggregate-data) for the last X days as JSON. See [HealthConnectRepository](app/src/main/java/com/rafapps/taskerhealthconnect/HealthConnectRepository.kt) and [HealthConnectDataTypes](app/src/main/java/com/rafapps/taskerhealthconnect/HealthConnectDataTypes.kt) for all data types, units, and JSON keys.
```json
[
  {
    "startTime":"2024-02-06T16:00:36.910Z",
    "endTime":"2024-02-07T16:00:36.910Z",
    "FloorsClimbedRecord_FLOORS_CLIMBED_TOTAL": 5.75,
    "ActiveCaloriesBurnedRecord_ACTIVE_CALORIES_TOTAL": 1532.1092
  },
  {
    "startTime":"2024-02-05T16:00:36.910Z",
    "endTime":"2024-02-06T16:00:36.910Z",
    "HeartRateRecord_BPM_AVG": 69,
    "StepsRecord_COUNT_TOTAL": 19822
  }
]
```

## Installation
- Updates are currently released only on GitHub
- Get the latest APK from the [releases](https://github.com/RafhaanShah/TaskerHealthConnect/releases) page
- [Google Play Protect](https://developers.google.com/android/play-protect) may complain about untrusted applications because the APK is currently only signed with [Android Debug Certificates](https://developer.android.com/studio/publish/app-signing)
- Check release / update notes as the plugin is not considered stable, and breaking API changes are to be expected

## Usage
- Run the app, it will check to make sure that Health Connect is installed and will prompt for required permissions
- Open Tasker, and look for 'Tasker Health Connect' inside Action -> Plugins

## Building
- Clone the repository: `git clone https://github.com/RafhaanShah/TaskerHealthConnect`
- Build with gradle: `./gradlew assembleDebug`
- Or just open in Android Studio and click run

## Testing
- Download the [Health Connect Toolbox](https://developer.android.com/health-and-fitness/guides/health-connect/test/health-connect-toolbox) to read and write test data
- Activities will have additional debug buttons to log output info on debug builds

## Contributing / Feature Requests
- Contributions via pull requests are welcome!
- Health Connect documentation can be found [here](https://developer.android.com/guide/health-and-fitness/health-connect/get-started)
- Tasker plugin documentation can be found [here](https://tasker.joaoapps.com/pluginslibrary.html)
- For feature requests please make a GitHub issue [here](https://github.com/RafhaanShah/TaskerHealthConnect/issues?q=is%3Aissue+is%3Aopen+sort%3Aupdated-desc) with details

## License
[MIT](https://choosealicense.com/licenses/mit/)
