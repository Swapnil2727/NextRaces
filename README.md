## Build tools & versions used

Android Studio : Android Studio Flamingo | 2022.2.1 Beta 4
minSdk 26

## Setup Instructions

To set up the project locally, follow these steps:

1. Clone the repository.
2. Open the project in Android Studio.
3. Build the project to resolve dependencies and generate necessary files.
4. Run the app on an emulator or physical device.

# Next to Go Races Android App

This Android app is a solution to the technical task provided by Entain as part of the Mobile
Engineer interview process. The app displays 'Next to Go' races using the provided API. It allows
users to see a time-ordered list of races, filter races by categories, and view race details.

## Features

- **Time-Ordered Race List:** Displays a time-ordered list of races sorted by the advertised start
  time in ascending order.

- **Dynamic Race Filtering:** Provides the ability to filter races by categories (Horse, Harness,
  and Greyhound racing) using a convenient top-right pop-up menu. Simply tap on the pop-up menu and
  select the desired category to filter the races accordingly.

- **Deselect All Filters:** Allows users to deselect all filters and display the next 5 races from
  all categories. Simply select the "All Races" option from the pop-up menu to remove any applied
  filters and show races from all categories.

- **Race Countdown:** Shows the meeting name, race number, and the advertised start time as a
  countdown for each race. The countdown is automatically updated in real-time to reflect the
  remaining time until the race starts.

- **Always Display 5 Races:** Ensures that the app always displays 5 races on the screen, providing
  a consistent and focused user experience.

- **Automatic Data Refresh:** Implements a pull-to-refresh mechanism that enables users to refresh
  the race data manually. Simply swipe down on the race list screen to trigger a refresh and fetch
  the latest race information from the API.

By incorporating these features, the app offers a user-friendly interface with real-time race
updates, dynamic filtering options, and seamless data refresh capabilities.

## Technologies Used

- Jetpack Compose: A modern UI toolkit for building native Android UIs.
- Kotlin: The programming language used for app development.
- Dagger Hilt: A dependency injection library for Android.
- Kotest: Kotest Matchers is a powerful library that provides a wide range of expressive and
  flexible matchers for testing in Kotlin.
- [faker](https://github.com/serpro69/kotlin-faker) to generate test data
- [Retrofit](https://square.github.io/retrofit/) A type-safe HTTP client for Android and Java
- [Moshi](https://github.com/square/moshi)Moshi is a modern JSON library for Android, Java and
  Kotlin. It makes it easy to parse JSON into Java and Kotlin classes
- [Accompanist placeholder](https://google.github.io/accompanist/placeholder/)A library which
  provides a modifier for display 'placeholder' UI while content is loading

## Project Structure

The project follows a standard Android project structure, separating different components into
packages. The main packages are as follows:

- `data`: Contains the data models, API service, and repository for fetching race data.
- `ui`: Contains the Jetpack Compose UI code, including screens, components, and navigation.
- `di`: Contains the Dagger Hilt dependency injection setup.
- `utils`: Contains utility classes and extensions used throughout the app.
- `test`: Contains test classes for unit testing.

## Testing

The project includes unit tests. The tests cover unit testing of view model and UI for the interview
purpose. In production app it is always useful to add API, Repository, and Screenshot testing for
Composables, but for the purpose interview I think that's too much work.

## ScreenShots

![Screenshot_20230624-124820](https://github.com/Swapnil2727/NextRaces/assets/39974969/2e376dd0-bade-4a63-8cba-248c6da2f169) ![Screenshot_20230624-124811](https://github.com/Swapnil2727/NextRaces/assets/39974969/ff9d2b8a-a8d9-48c0-9463-c24df48c61cd) ![Screenshot_20230624-124834](https://github.com/Swapnil2727/NextRaces/assets/39974969/e35f2683-f5a5-4f85-b704-cf806702d106)
