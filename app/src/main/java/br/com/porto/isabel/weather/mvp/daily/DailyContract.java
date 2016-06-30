package br.com.porto.isabel.weather.mvp.daily;


import br.com.porto.isabel.weather.model.app.DailyInterface;

public interface DailyContract {

    interface PresenterContract {
        void init();
    }

    interface ViewContract {

        void showCityName(String cityName);

        void showDailyData(DailyInterface daily);
    }

    interface ModelContract {

        String getCityName();

        DailyInterface getDaily();
    }
}
