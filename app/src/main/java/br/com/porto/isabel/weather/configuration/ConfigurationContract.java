package br.com.porto.isabel.weather.configuration;


import java.util.List;

import br.com.porto.isabel.weather.model.City;

public interface ConfigurationContract {

    interface PresenterContract {

        void init();
    }

    interface ViewContract {

        void showUserCity(List<City> userCityList);
    }

    interface ModelContract {


        void setPresenter(PresenterContract presenter);

        List<City> getUserCityList();
    }

}
