package br.com.porto.isabel.weather.home;


import java.util.List;

import br.com.porto.isabel.weather.model.City;
import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Forecast;

public interface HomeContract {

    interface PresenterContract {

        void onRequestDailyWithSuccess(Forecast forecast);

        void onRequestDailyWithError();

        void onRequestCurrentWithSuccess(Current current);

        void onRequestCurrentWithError();

        void onRefresh();

        void onCitySelected(City city);

        void onCreateOptionsMenu();
    }

    interface ViewContract {

        void showProgress();

        void showCurrentData(Current current);

        void showForecast(Forecast forecast);

        void hideProgress();

        void hideSwipe();

        void showCityList(List<City> userCityList);
    }

    interface ModelContract {

        void setPresenter(PresenterContract presenter);

        void requestDailyData();

        void requestCurrentData();

        List<City> getUserCityList();

        void selectCity(City city);

    }

}
