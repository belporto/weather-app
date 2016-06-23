package br.com.porto.isabel.weather.home;


import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Forecast;

public interface HomeContract {

    interface PresenterContract {

        void init();

        void onRequestDailyWithSuccess(Forecast forecast);

        void onRequestDailyWithError();

        void onRequestCurrentWithSuccess(Current current);

        void onRequestCurrentWithError();
    }

    interface ViewContract {

        void showProgress();

        void showCurrentData(Current current);

        void showForecast(Forecast forecast);

        void hideProgress();
    }

    interface ModelContract {

        void setPresenter(PresenterContract presenter);

        void requestDailyData(int cityId);

        void requestCurrentData(int cityId);
    }

}
