package br.com.porto.isabel.weather.mvp.configuration;


import java.util.List;

import br.com.porto.isabel.weather.model.app.UserCity;
import br.com.porto.isabel.weather.view.adapter.usercity.UserCityListCallback;
import br.com.porto.isabel.weather.view.touch.CityTouchCallbackContract;

public interface ConfigurationContract {

    interface PresenterContract extends CityTouchCallbackContract, UserCityListCallback {

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

        void showCityInformation(UserCity userCity);
    }

    interface ModelContract {

        List<UserCity> getUserCityList();

        void deleteCity(UserCity city);

        void addCity(UserCity city);

        void selectCity(UserCity userCity);
    }

}
