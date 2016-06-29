package br.com.porto.isabel.weather.mvp.daily;


import br.com.porto.isabel.weather.model.Daily;
import br.com.porto.isabel.weather.model.user.UserCity;

public class DailyModel implements DailyContract.ModelContract {

    private UserCity mUserCity;
    private Daily mDaily;

    public DailyModel(UserCity userCity, Daily daily) {
        mUserCity = userCity;
        mDaily = daily;
    }

    @Override
    public String getCityName() {
        return mUserCity.getName();
    }

    @Override
    public Daily getDaily() {
        return mDaily;
    }
}
