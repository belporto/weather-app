package br.com.porto.isabel.weather.mvp.home;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.util.List;

import br.com.porto.isabel.weather.model.app.CurrentInterface;
import br.com.porto.isabel.weather.model.app.DailyInterface;
import br.com.porto.isabel.weather.model.app.ForecastInterface;
import br.com.porto.isabel.weather.model.app.UserCity;
import br.com.porto.isabel.weather.model.app.WeatherData;
import br.com.porto.isabel.weather.view.adapter.daily.DailyCallback;

public interface HomeContract {

    interface PresenterContract extends DailyCallback {

        void onRequestDataWithSuccess(WeatherData forecast);

        void onRequestDataWithError();

        void onRefresh();

        void onCitySelected(UserCity city);

        void onCreateOptionsMenu();

        void init();

        void onTryAgainClicked();
    }

    interface ViewContract {

        View getView();

        void showProgress();

        void showCurrentData(CurrentInterface current);

        void showForecast(ForecastInterface forecast);

        void showContent();

        void hideSwipe();

        void showCityList(List<UserCity> userCityList, UserCity current);

        void showError();

        void showDailyInformation(DailyInterface daily, UserCity userCity);

        void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

        void setPresente(PresenterContract presenter);
    }

    interface ModelContract {

        void setPresenter(PresenterContract presenter);

        List<UserCity> getUserCityList();

        void selectCity(UserCity city);

        UserCity getCurrentCity();

        void requestData();

    }

}
