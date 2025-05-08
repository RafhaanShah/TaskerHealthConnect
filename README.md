# Health Connect Plugin for Tasker

[Tasker](https://tasker.joaoapps.com/) plugin to interface with [Health Connect](https://developer.android.com/health-connect) on Android

## Current Features
- Read [Health Records](https://developer.android.com/health-and-fitness/guides/health-connect/develop/read-data)  as JSON.
- Read [Aggregated Health Records](https://developer.android.com/health-and-fitness/guides/health-connect/develop/aggregate-data) as JSON.
- Write [Health Records](https://developer.android.com/health-and-fitness/guides/health-connect/develop/write-data) from JSON.

## Installation
- Updates are currently released only on GitHub
- Get the latest APK from the [releases](https://github.com/RafhaanShah/TaskerHealthConnect/releases) page
- [Google Play Protect](https://developers.google.com/android/play-protect) may complain about untrusted applications because the APK is currently only signed with [Android Debug Certificates](https://developer.android.com/studio/publish/app-signing)
- Check release / update notes as the plugin is not considered stable, and breaking API changes are to be expected

## Usage
- Run the app, it will check to make sure that Health Connect is installed and will prompt for required permissions
- Open Tasker, and look for 'Tasker Health Connect' inside Action -> Plugins
- All Input and Output is in JSON format, you can use Tasker variables and Tasker's built-in JSON processing or other plugins
- For the expected format of the input and output, look at the documentation for the (Health Connect API)[https://developer.android.com/reference/kotlin/androidx/health/connect/client/HealthConnectClient]

## Building
- Clone the repository: `git clone https://github.com/RafhaanShah/TaskerHealthConnect`
- Build with gradle: `./gradlew assembleDebug`
- Or just open in Android Studio and click run

## Testing
- Download the [Health Connect Toolbox](https://developer.android.com/health-and-fitness/guides/health-connect/test/health-connect-toolbox) to read and write test data
- Activities will have additional debug buttons to log output info on debug builds

## Issues / Troubleshooting
- This plugin simply reads/writes data from/to Health Connect and provides it to Tasker
- It does not have anything to do with any data from any other source
- It does not modify or manipulate this data in any way except for serializing it to/from JSON
- To view your existing Health Connect data, use the [Health Connect Toolbox](https://developer.android.com/health-and-fitness/guides/health-connect/test/health-connect-toolbox)

## Contributing / Feature Requests
- Contributions via pull requests are welcome!
- Health Connect documentation can be found [here](https://developer.android.com/guide/health-and-fitness/health-connect/get-started)
- Tasker plugin documentation can be found [here](https://tasker.joaoapps.com/pluginslibrary.html)
- For feature requests please make a GitHub issue [here](https://github.com/RafhaanShah/TaskerHealthConnect/issues?q=is%3Aissue+is%3Aopen+sort%3Aupdated-desc) with details

## License
[MIT](https://choosealicense.com/licenses/mit/)
