package br.com.porto.isabel.weather.mvp.home;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import br.com.porto.isabel.weather.model.app.UserCity;
import br.com.porto.isabel.weather.model.openweather.Current;
import br.com.porto.isabel.weather.model.openweather.Daily;
import br.com.porto.isabel.weather.model.openweather.Forecast;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
public class HomePresenterTest {

    @Mock
    private HomeContract.ModelContract mModel;

    @Mock
    private HomeContract.ViewContract mView;

    private HomePresenter mPresenter;

//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        mPresenter = new HomePresenter(mView, mModel);
//    }
//
//    @Test
//    public void ensurePresenterInitWillShowProgressAndRequestCurrentData() throws Exception {
//        mPresenter.onCreate();
//
//        verify(mView).showProgress();
//        verify(mModel).requestCurrentData();
//    }
//
//    @Test
//    public void ensurePresenterTryAgainWillShowProgressAndRequestCurrentData() throws Exception {
//        mPresenter.onTryAgainClicked();
//
//        verify(mView).showProgress();
//        verify(mModel).requestCurrentData();
//    }
//
//    @Test
//    public void ensurePresenterOnRefreshWillRequestCurrentData() throws Exception {
//        mPresenter.onRefresh();
//
//        verify(mModel).requestCurrentData();
//    }
//
//    @Test
//    public void ensureWhenSelectDifferentCityWillRequestCurrentData() throws Exception {
//        UserCity city = new UserCity("name", 12.0, 12.0, "id");
//        UserCity city2 = new UserCity("name2", 12.0, 12.0, "id2");
//        when(mModel.getCurrentCity()).thenReturn(city);
//
//        mPresenter.onCitySelected(city2);
//
//        verify(mView).showProgress();
//        verify(mModel).selectCity(city2);
//        verify(mModel).requestCurrentData();
//    }
//
//    @Test
//    public void ensureWhenSelectSameCityWillDoNothing() throws Exception {
//        UserCity city = new UserCity("name", 12.0, 12.0, "id");
//        when(mModel.getCurrentCity()).thenReturn(city);
//
//        mPresenter.onCitySelected(city);
//
//        verify(mView, never()).showProgress();
//        verify(mModel, never()).selectCity(city);
//        verify(mModel, never()).requestCurrentData();
//    }
//
//    @Test
//    public void ensureOnRequestCurrentWithSuccessWillRequestDailyData() throws Exception {
//        Current current = new Current();
//        mPresenter.onRequestCurrentWithSuccess(current);
//
//        verify(mView).showCurrentData(current);
//        verify(mModel).requestDailyData();
//    }
//
//    @Test
//    public void ensureOnRequestDailyWithSuccessWillShowContent() throws Exception {
//        Forecast forecast = new Forecast();
//        mPresenter.onRequestDailyWithSuccess(forecast);
//
//        verify(mView).showForecast(forecast);
//        verify(mView).showContent();
//    }
//
//    @Test
//    public void ensureOnRequestDailyWithErrorWillShowError() throws Exception {
//        mPresenter.onRequestDailyWithError();
//
//        verify(mView).showError();
//    }
//
//    @Test
//    public void ensureOnRequestCurrentWithErrorWillShowError() throws Exception {
//        mPresenter.onRequestCurrentWithError();
//
//        verify(mView).showError();
//    }
//
//    @Test
//    public void ensureOnCreateOptionsMenuWillShowCityList() throws Exception {
//        List<UserCity> cityList = new ArrayList<>();
//        UserCity city = new UserCity("name", 12.0, 12.0, "id");
//        when(mModel.getUserCityList()).thenReturn(cityList);
//        when(mModel.getCurrentCity()).thenReturn(city);
//        mPresenter.onCreateOptionsMenu();
//
//        verify(mView).showCityList(cityList, city);
//    }
//
//    @Test
//    public void ensureOnDailySelectedWillShowDailyInformation() throws Exception {
//        UserCity city = new UserCity("name", 12.0, 12.0, "id");
//        when(mModel.getCurrentCity()).thenReturn(city);
//        Daily daily = new Daily();
//
//        mPresenter.onDailySelected(daily);
//
//        verify(mView).showDailyInformation(daily, city);
//    }
//
//    @Test
//    public void ensureIfRequestDataIsCallByOnRefreshThenWillHideSwipeWhenFinish() throws Exception {
//        mPresenter.onRefresh();
//        mPresenter.onRequestCurrentWithError();
//
//        verify(mView).hideSwipe();
//    }


}