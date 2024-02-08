# Health Connect Plugin for Tasker

[Tasker](https://tasker.joaoapps.com/) plugin to interface with [Health Connect](https://developer.android.com/health-connect) on Android

## Current Features
- ~~Get last X days step count~~ Retrieve all [Aggregate data](https://developer.android.com/health-and-fitness/guides/health-connect/develop/aggregate-data) for the last X days

## Installation
- Updates are currently released only on GitHub
- Get the latest APK from the [releases](https://github.com/RafhaanShah/TaskerHealthConnect/releases) page
- [Google Play Protect](https://developers.google.com/android/play-protect) may complain about untrusted applications because the APK is currently only signed with [Android Debug Certificates](https://developer.android.com/studio/publish/app-signing)

## Usage
- Run the app, it will check to make sure that Health Connect is installed and will prompt for required permissions
- Open Tasker, and look for 'Tasker Health Connect' inside Action -> Plugins

### Example Output

```json
[
  {
    "startTime":"2024-02-06T16:00:36.910Z",
    "endTime":"2024-02-07T16:00:36.910Z",
    "result":{
      "Height_height_min":1.55,
      "Height_height_avg":1.55,
      "Height_height_max":1.55,
      "Weight_weight_min":63,
      "Weight_weight_avg":63.5,
      "Weight_weight_max":64,
      "BasalCaloriesBurned_energy_total":1523.29049265625,
      "Nutrition_sugar_total":0,
      "Nutrition_cholesterol_total":0,
      "Nutrition_totalCarbohydrate_total":49.30000114440918,
      "Nutrition_totalFat_total":1.0000000298023224,
      "Nutrition_protein_total":1.600000023841858,
      "Nutrition_dietaryFiber_total":5.5,
      "Nutrition_iron_total":3.000000044703484E-4,
      "Nutrition_vitaminA_total":2.0070000076293946E-4,
      "Nutrition_vitaminC_total":0.0525,
      "Nutrition_calcium_total":0.2390999984741211,
      "Nutrition_calories_total":201,
      "Nutrition_potassium_total":0.458,
      "Nutrition_sodium_total":0.006,
      "Nutrition_transFat_total":0,
      "Nutrition_vitaminD_total":0,
      "Nutrition_saturatedFat_total":0,
      "TotalCaloriesBurned_energy_total":1523.29049265625,
      "HeartRateSeries_bpm_min":68,
      "HeartRate_bpm_min":68,
      "HeartRateSeries_bpm_avg":79,
      "HeartRate_bpm_avg":79,
      "HeartRateSeries_bpm_max":92,
      "HeartRate_bpm_max":92,
      "Steps_count_total":5660,
      "HeartRateSeries_count":48,
      "HeartRate_count":48,
      "SleepSession_duration":17040000
    }
  },
  {
    "startTime":"2024-02-05T16:00:36.910Z",
    "endTime":"2024-02-06T16:00:36.910Z",
    "result":{
      "BasalCaloriesBurned_energy_total":1564.5,
      "TotalCaloriesBurned_energy_total":1564.5,
      "Steps_count_total":2
    }
  },
  {
    "startTime":"2024-02-07T16:00:36.910Z",
    "endTime":"2024-02-08T12:01:36.910Z",
    "result":{
      "BasalCaloriesBurned_energy_total":1266.8881944444443,
      "TotalCaloriesBurned_energy_total":1266.8881944444443,
      "Steps_count_total":1423
    }
  }
]
```

## Building
- Clone the repository: `git clone https://github.com/RafhaanShah/TaskerHealthConnect`
- Build with gradle: `./gradlew assembleDebug`

# Contributing / Feature Requests
- Contributions via pull requests are welcome!
- Tasker plugin documentation can be found [here](https://developer.android.com/guide/health-and-fitness/health-connect/get-started)
- Health Connect documentation can be found [here](https://tasker.joaoapps.com/pluginslibrary.html)
- For feature requests please make a GitHub issue [here](https://github.com/RafhaanShah/TaskerHealthConnect/issues?q=is%3Aissue+is%3Aopen+sort%3Aupdated-desc) with details

## License
[MIT](https://choosealicense.com/licenses/mit/)
