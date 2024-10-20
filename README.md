## Flight Search App
The Flight Search App is a project based on the [Google Android Basics with Compose Course](https://developer.android.com/codelabs/basic-android-kotlin-compose-flight-search) lab. It allows users to search for flights by entering airport names or IATA identifiers and view available flight routes from their selected airport. This project demonstrates skills in SQL, Room, and DataStore.

### Features
- Search Airports: Enter an airport name or IATA code to get autocomplete suggestions from the database.
- View Flight Routes: See a list of available flights from the selected airport with destination airport details.
- Favorite Routes: Save and manage favorite flight routes.
- Favorites List: Display saved favorite routes when no search is active.
- Persistent Search: The app stores the last search query using DataStore and restores it when the app is reopened.

### Technologies Used
- Room: Database for storing and querying airport and flight route data.
- SQL: Custom SQL queries for autocomplete and flight searches.
- DataStore: Used for storing the search query persistently, allowing the app to retain the last search state when reopened.
- Jetpack Compose: UI framework used for creating responsive and modern UI components.

### Usage
- Enter an airport name or IATA code in the search field.
- Select an airport from the autocomplete suggestions to view available flight routes.
- Mark any flight routes as favorites by selecting them from the list.
- The favorites list will be displayed when no search query is active.
- Close and reopen the app, and the last search query will be restored with the search field pre-filled.
