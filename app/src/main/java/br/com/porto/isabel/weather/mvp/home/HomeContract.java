package br.com.porto.isabel.weather.mvp.home;


import java.util.List;

import br.com.porto.isabel.weather.model.user.CurrentInterface;
import br.com.porto.isabel.weather.model.user.DailyInterface;
import br.com.porto.isabel.weather.model.user.ForecastInterface;
import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.view.adapter.daily.DailyCallback;

public interface HomeContract {

    interface PresenterContract extends DailyCallback {

        void onRequestDailyWithSuccess(ForecastInterface forecast);

        void onRequestDailyWithError();

        void onRequestCurrentWithSuccess(CurrentInterface current);

        void onRequestCurrentWithError();

        void onRefresh();

        void onCitySelected(UserCity city);

        void onCreateOptionsMenu();

        void init();

        void onTryAgainClicked();
    }

    interface ViewContract {

        void showProgress();

        void showCurrentData(CurrentInterface current);

        void showForecast(ForecastInterface forecast);

        void showContent();

        void hideSwipe();

        void showCityList(List<UserCity> userCityList, UserCity current);

        void showError();

        void showDailyInformation(DailyInterface daily, UserCity userCity);

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
