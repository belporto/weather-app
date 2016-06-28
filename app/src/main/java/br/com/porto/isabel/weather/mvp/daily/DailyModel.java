package br.com.porto.isabel.weather.mvp.daily;


import br.com.porto.isabel.weather.model.Daily;
import br.com.porto.isabel.weather.model.user.UserCity;

public class DailyModel implements DailyContract.ModelContract {

    private UserCity mUserCity;
    private Daily mDaily;
    private DailyContract.PresenterContract mPresenter;

    public DailyModel(UserCity userCity, Daily daily) {
        mUserCity = userCity;
        mDaily = daily;
    }

    @Override
    public void setPresenter(DailyContract.PresenterContract presenter){
        mPresenter = presenter;
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
