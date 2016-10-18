package br.com.porto.isabel.weather.mvp.home;


import com.twistedequations.mvl.rx.AndroidRxSchedulers;

import br.com.porto.isabel.weather.model.app.DailyInterface;
import br.com.porto.isabel.weather.model.app.UserCity;
import br.com.porto.isabel.weather.model.app.WeatherData;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class HomePresenter implements HomeContract.PresenterContract {

    private HomeContract.ViewContract mView;
    private HomeContract.ModelContract mModel;
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private final AndroidRxSchedulers mSchedulers;

    public HomePresenter(HomeContract.ViewContract view, HomeContract.ModelContract model, AndroidRxSchedulers schedulers) {
        mView = view;
        mModel = model;
        mSchedulers = schedulers;
    }

    @Override
    public void onCreate() {
        compositeSubscription.add(subscribeLoadWeatherData());
        compositeSubscription.add(subscribeRefresh());

        //TODO: compositeSubscription.add(subscribeDailyClicks());
    }

    private Subscription subscribeLoadWeatherData() {
        return Observable.just(null)
                .doOnNext(aVoid -> mView.showProgress())
                .switchMap(aVoid -> observableLoadData())// pull to refresh event
                .subscribe(setDataViewBlock, e -> {
                    mView.showError();
                });
    }

    private Subscription subscribeSelectCity() {
        return mView.observeSelectCity()
                .filter(userCity -> {
                    UserCity currentCity = mModel.getCurrentCity();
                    return currentCity != null && userCity != null && !currentCity.equals(userCity);
                })
                .doOnNext(aVoid -> mView.showProgress())
                .doOnNext(userCity -> mModel.selectCity(userCity))
                .switchMap(aVoid -> observableLoadData())// pull to refresh event
                .subscribe(setDataViewBlock, e -> {
                    mView.showError();
                });
    }


    private Subscription subscribeRefresh() {
        return mView.observePullToRefresh()
                .switchMap(aVoid -> observableLoadData())// pull to refresh event
                .subscribe(setDataViewBlock, e -> {
                    mView.showError();
                    mView.hideSwipe();
                });
    }



    @Override
    public void onTryAgainClicked() {
        mView.showProgress();
        mModel.requestData();
    }

    @Override
    public void onDestroy() {
        compositeSubscription.clear();
    }

    @Override
    public void onCreateOptionsMenu() {
        mView.showCityList(mModel.getUserCityList(), mModel.getCurrentCity());
        compositeSubscription.add(subscribeSelectCity());
    }

    @Override
    public void onDailySelected(DailyInterface daily) {
        mView.showDailyInformation(daily, mModel.getCurrentCity());
    }

    private Observable<WeatherData> observableLoadData(){
        return Observable.just(null)
                .observeOn(mSchedulers.network()) // network tread
                .switchMap(userCity -> mModel.requestData()) //
                .observeOn(mSchedulers.mainThread()); // main thread

    }

    private final Action1<WeatherData> setDataViewBlock = weatherData -> {
        mView.showCurrentData(weatherData.getCurrent());
        mView.showForecast(weatherData.getForecast());
        mView.showContent();
        mView.hideSwipe();
    };
}
