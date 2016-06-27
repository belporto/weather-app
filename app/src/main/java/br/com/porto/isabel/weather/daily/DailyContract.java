package br.com.porto.isabel.weather.daily;


import java.util.List;

import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Daily;
import br.com.porto.isabel.weather.model.Forecast;
import br.com.porto.isabel.weather.model.user.UserCity;

public interface DailyContract {

    interface PresenterContract {


        void init();
    }

    interface ViewContract {


        void showCityName(String cityName);

        void showDailyData(Daily daily);
    }

    interface ModelContract {


        void setPresenter(PresenterContract presenter);

        String getCityName();

        Daily getDaily();
    }
}
