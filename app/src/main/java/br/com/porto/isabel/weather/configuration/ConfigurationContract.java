package br.com.porto.isabel.weather.configuration;


import java.util.List;

import br.com.porto.isabel.weather.model.City;
import br.com.porto.isabel.weather.view.CityTouchCallbackContract;

public interface ConfigurationContract {

    interface PresenterContract extends CityTouchCallbackContract {

        void init();
    }

    interface ViewContract {

        void showUserCity(List<City> userCityList);

        void onCityDeleted(City city);
    }

    interface ModelContract {


        void setPresenter(PresenterContract presenter);

        List<City> getUserCityList();

        void deleteCity(City city);

    }

}
