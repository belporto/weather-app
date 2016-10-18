package br.com.porto.isabel.weather;


import br.com.porto.isabel.weather.app.builder.WeatherInjector;
import br.com.porto.isabel.weather.app.builder.module.WeatherComponent;

/**
 * Class used to init the WeatherAppGraph app graph and the base graph
 */
public class WeatherAppGraph {

  private static WeatherInjector weatherInjector;

  public static void init(WeatherInjector weatherInjector) {
    WeatherAppGraph.weatherInjector = weatherInjector;

  }

  public static WeatherInjector get() {
    if (weatherInjector == null) {
      throw new IllegalStateException("Did you forget to call ConciergeItinerary.init in your application class?");
    }

    return weatherInjector;
  }

  public static WeatherComponent component() {
    return weatherInjector.getComponent();
  }

}
