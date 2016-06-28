package br.com.porto.isabel.weather.mvp.daily;


public class DailyPresenter implements DailyContract.PresenterContract {

    private DailyContract.ViewContract mView;
    private DailyContract.ModelContract mModel;

    public DailyPresenter(DailyContract.ViewContract view, DailyContract.ModelContract model) {
        this.mView = view;
        this.mModel = model;
    }


    @Override
    public void init() {
        mView.showCityName(mModel.getCityName());
        mView.showDailyData(mModel.getDaily());
    }
}
