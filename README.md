# Health Connect Plugin for Tasker

[Tasker](https://tasker.joaoapps.com/) plugin to interface with [Health Connect](https://developer.android.com/health-connect) on Android

## Current Features
- Read [Health Data](https://developer.android.com/health-and-fitness/guides/health-connect/develop/read-data)  as JSON.
- Read [Aggregated Health Data](https://developer.android.com/health-and-fitness/guides/health-connect/develop/aggregate-data) as JSON.
- Write [Health Data](https://developer.android.com/health-and-fitness/guides/health-connect/develop/write-data) from JSON.
- Note that the Health Connect API is only designed for on-demand fetching and pushing of data, it does NOT support getting notified of changes and so you cannot build application that react to changes in data.

## Installation
- Get the APK from the [GitHub releases](https://github.com/RafhaanShah/TaskerHealthConnect/releases) page, or through [Obtainium](https://obtainium.imranr.dev/)
- Check release / update notes for major version releases, as breaking changes could affect existing Tasks

[<img src="https://raw.githubusercontent.com/LawnchairLauncher/lawnchair/refs/heads/15-dev/docs/assets/badge-github.png"
  alt="Get it on GitHub"
  height="80">](https://github.com/RafhaanShah/TaskerHealthConnect/releases)
[<img src="https://raw.githubusercontent.com/LawnchairLauncher/lawnchair/refs/heads/15-dev/docs/assets/badge-obtainium.png"
  alt="Get it on Obtainium"
  height="80">](obtainium://app/%7B%22id%22%3A%22com.rafapps.taskerhealthconnect%22%2C%22url%22%3A%22https%3A%2F%2Fgithub.com%2FRafhaanShah%2FTaskerHealthConnect%22%2C%22author%22%3A%22RafhaanShah%22%2C%22name%22%3A%22Tasker%20Health%20Connect%22%2C%22preferredApkIndex%22%3A0%2C%22additionalSettings%22%3A%22%7B%5C%22includePrereleases%5C%22%3Afalse%2C%5C%22fallbackToOlderReleases%5C%22%3Atrue%2C%5C%22filterReleaseTitlesByRegEx%5C%22%3A%5C%22%5C%22%2C%5C%22filterReleaseNotesByRegEx%5C%22%3A%5C%22%5C%22%2C%5C%22verifyLatestTag%5C%22%3Afalse%2C%5C%22sortMethodChoice%5C%22%3A%5C%22date%5C%22%2C%5C%22useLatestAssetDateAsReleaseDate%5C%22%3Afalse%2C%5C%22releaseTitleAsVersion%5C%22%3Atrue%2C%5C%22trackOnly%5C%22%3Afalse%2C%5C%22versionExtractionRegEx%5C%22%3A%5C%22%5C%22%2C%5C%22matchGroupToUse%5C%22%3A%5C%22%5C%22%2C%5C%22versionDetection%5C%22%3Atrue%2C%5C%22releaseDateAsVersion%5C%22%3Afalse%2C%5C%22useVersionCodeAsOSVersion%5C%22%3Afalse%2C%5C%22apkFilterRegEx%5C%22%3A%5C%22%5C%22%2C%5C%22invertAPKFilter%5C%22%3Afalse%2C%5C%22autoApkFilterByArch%5C%22%3Atrue%2C%5C%22appName%5C%22%3A%5C%22TaskerHealthConnect%5C%22%2C%5C%22appAuthor%5C%22%3A%5C%22RafhaanShah%5C%22%2C%5C%22shizukuPretendToBeGooglePlay%5C%22%3Afalse%2C%5C%22allowInsecure%5C%22%3Afalse%2C%5C%22exemptFromBackgroundUpdates%5C%22%3Afalse%2C%5C%22skipUpdateNotifications%5C%22%3Afalse%2C%5C%22about%5C%22%3A%5C%22%5C%22%2C%5C%22refreshBeforeDownload%5C%22%3Afalse%7D%22%7D)

## Usage
- Caveat: this is not a beginner friendly plugin - it requires a fair amount of work and basic knowledge of JSON and checking the Health Connect API to use properly
- Run the app, it will check to make sure that Health Connect is installed and will prompt for required permissions
- Open Tasker, and look for 'Tasker Health Connect' inside Action -> Plugins
- All Input and Output is in JSON format, you can use Tasker variables and Tasker's built-in JSON processing or other plugins
- For the expected format of the input and output, look at the documentation for the [Health Connect API](https://developer.android.com/reference/kotlin/androidx/health/connect/client/HealthConnectClient)
- You can find sample input data inside the [unit test resources/input](app/src/test/resources/input)
- You can find sample output data inside the [unit test resources/output](app/src/test/resources/output)
- You can find sample aggregated data inside the [unit test resources/aggregated](app/src/test/resources/aggregated)

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
- For any bug reports, please attach logs dumped via [logcat](https://developer.android.com/tools/logcat)

## Contributing / Feature Requests
- Contributions via pull requests are welcome!
- Health Connect documentation can be found [here](https://developer.android.com/guide/health-and-fitness/health-connect/get-started)
- Tasker plugin documentation can be found [here](https://tasker.joaoapps.com/pluginslibrary.html)
- For feature requests please make a GitHub issue [here](https://github.com/RafhaanShah/TaskerHealthConnect/issues?q=is%3Aissue+is%3Aopen+sort%3Aupdated-desc) with details

## License
[MIT](https://choosealicense.com/licenses/mit/)
