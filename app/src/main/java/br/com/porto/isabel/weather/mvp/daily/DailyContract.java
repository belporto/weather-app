package br.com.porto.isabel.weather.mvp.daily;


import br.com.porto.isabel.weather.model.Daily;

public interface DailyContract {

    interface PresenterContract {


        void init();
    }

    interface ViewContract {

        void showCityName(String cityName);

        void showDailyData(Daily daily);
    }

    interface ModelContract {

        String getCityName();

        Daily getDaily();
    }
}
