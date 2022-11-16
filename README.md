# Health Connect Plugin for Tasker

[Tasker](https://tasker.joaoapps.com/) plugin to interface with [Health Connect](https://developer.android.com/health-connect) on Android

## Current Features
- Get last X days step count

## Installation
- Updates are currently released only on GitHub
- Get the latest APK from the [releases](https://github.com/RafhaanShah/TaskerHealthConnect/releases) page
- [Google Play Protect](https://developers.google.com/android/play-protect) may complain about untrusted applications because the APK is currently only signed with [Android Debug Certificates](https://developer.android.com/studio/publish/app-signing)

## Usage
- Run the app, it will check to make sure that Health Connect is installed and will prompt for required permissions
- Open Tasker, and look for 'Tasker Health Connect' inside Action -> Plugins

## Building
- Clone the repository: `git clone https://github.com/RafhaanShah/TaskerHealthConnect`
- Build with gradle: `./gradlew assembleDebug`

## License
[MIT](https://choosealicense.com/licenses/mit/)
  
