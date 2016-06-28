package br.com.porto.isabel.weather.configuration;


import java.util.List;

import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.view.touch.CityTouchCallbackContract;

public interface ConfigurationContract {

    interface PresenterContract extends CityTouchCallbackContract {

        void init();

        void onAddCityClicked();

        void onCityClicked(UserCity city);

        void onCitySizeLimitReached();

        void onCityDeleted(UserCity city);
    }

    interface ViewContract {

        void showCityDialog();

        void showUserCity(List<UserCity> userCityList);

        void onCityDeleted(UserCity city);

        void onCityAdded(UserCity city);

        void showLimitDialog();

        void revertSwipe();
    }

    interface ModelContract {

        List<UserCity> getUserCityList();

        void deleteCity(UserCity city);

        void addCity(UserCity city);

    }

}
