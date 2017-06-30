# Weather App

This is a weather app

To run this project it's necessary to add a new file `keys.xml` into the `values` folder.
This file should contain the key "google_places_api_key" and "open_weather_api_key"

```
<resources>
  <string name="google_places_api_key"><your api key></string>
  <string name="open_weather_api_key"><your api key></string>
</resources>
```

You can get your API key here: http://openweathermap.org/ and https://developers.google.com/places/


### Design Patterns + Architecture Solutions

* Strategy Pattern
    * I’ve used the Strategy Pattern as shown below to define how to cache the files.
    * The implementation of the cache uses `SharedPreferences`. With that, instead of changing the `UserCityCachedRepository` to save the data anywhere else (eg. in the cloud), we only need to implement other `UserCityCacheStrategy` and use it in the Repository.
* Adapter Pattern
    * Adapter Pattern is commonly used in Android Applications. I’ve used this pattern to show the list of 5 days forecast and to show the list of cities.
* MVP
    * I’ve used MVP to separate the presentation layer for the logic. It enables testing the logic code without using instrumentation tests.
* OO Principles
    * Favors composition over inheritance
    *  Program to interface not to implementation. I can swap the weather API and the communication library without having to make significant changes in the code.

  
### Approaches
* Use the Open Weather Map as a weather service - http://openweathermap.org/
    * Because it’s free and has unlimited requests.
* Use the Google Places to show the suggested cities - https://developers.google.com/places/
    * It’s easy to use and give the suggested cities.
* Use the SharedPreferences to save the cities in cache.
    * I’ve thought about using some ORM library to save the cities in cache, but I think it’s an over architecture. So, I decided to use SharedPreferences to save the city list as a JSON.
* Create a CustomView to show details, as wind speed and humidity.
    * I think in this case it’s a good approach, because I don’t want to repeat the code in the xml file nor use the RecyclerView to show the items.

### Third Party Libraries
* Glide: to show the daily forecast image as a background in the forecast daily detail screen.
* ButterKnife: to inject view dependencies.
* Retrofit: to communicate with the weather API.
* Gson: to convert JSON files.
* Otto: to send messages between the My Cities activity and the Home Screen.
* JUnit: to implement JUnit tests.
* PowerMock: to use mock in Junit tests.
* Play Services Location: to suggest the cities.