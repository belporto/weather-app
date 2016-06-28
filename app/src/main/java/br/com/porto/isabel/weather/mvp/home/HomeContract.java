package br.com.porto.isabel.weather.mvp.home;


import java.util.List;

import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Daily;
import br.com.porto.isabel.weather.model.Forecast;
import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.view.adapter.daily.DailyCallback;

public interface HomeContract {

    interface PresenterContract extends DailyCallback {

        void onRequestDailyWithSuccess(Forecast forecast);

        void onRequestDailyWithError();

        void onRequestCurrentWithSuccess(Current current);

        void onRequestCurrentWithError();

        void onRefresh();

        void onCitySelected(UserCity city);

        void onCreateOptionsMenu();

        void init();

        void onTryAgainClicked();
    }

    interface ViewContract {

        void showProgress();

        void showCurrentData(Current current);

        void showForecast(Forecast forecast);

        void showContent();

        void hideSwipe();

        void showCityList(List<UserCity> userCityList, UserCity current);

        void showError();

        void showDailyInformation(Daily daily, UserCity userCity);


    }

    interface ModelContract {

        void setPresenter(PresenterContract presenter);

        void requestDailyData();

        void requestCurrentData();

        List<UserCity> getUserCityList();

        void selectCity(UserCity city);

        UserCity getCurrentCity();

    }

}
