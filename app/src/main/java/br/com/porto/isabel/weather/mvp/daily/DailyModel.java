package br.com.porto.isabel.weather.mvp.daily;


import br.com.porto.isabel.weather.model.app.DailyInterface;
import br.com.porto.isabel.weather.model.app.UserCity;

public class DailyModel implements DailyContract.ModelContract {

    private UserCity mUserCity;
    private DailyInterface mDaily;

    public DailyModel(UserCity userCity, DailyInterface daily) {
        mUserCity = userCity;
        mDaily = daily;
    }

    @Override
    public String getCityName() {
        return mUserCity.getName();
    }

    @Override
    public DailyInterface getDaily() {
        return mDaily;
    }
}
