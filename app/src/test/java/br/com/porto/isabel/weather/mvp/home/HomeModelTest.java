package br.com.porto.isabel.weather.mvp.home;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import br.com.porto.isabel.weather.model.openweather.Current;
import br.com.porto.isabel.weather.model.openweather.Forecast;
import br.com.porto.isabel.weather.model.app.CurrentInterface;
import br.com.porto.isabel.weather.model.app.ForecastInterface;
import br.com.porto.isabel.weather.model.app.UserCity;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.service.WeatherAPI;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
public class HomeModelTest {

    @Mock
    private WeatherAPI mWeatherAPI;

    @Mock
    private UserCityRepository mUserCityRepository;

    @Captor
    private ArgumentCaptor<WeatherAPICallback<CurrentInterface>> mCurrentCapture;

    @Captor
    private ArgumentCaptor<WeatherAPICallback<ForecastInterface>> mDailyCapture;

    @Mock
    private HomeContract.PresenterContract mPresenter;


    private HomeModel mModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mModel = new HomeModel(mWeatherAPI, mUserCityRepository);
        mModel.setPresenter(mPresenter);
    }

    @Test
    public void ensureWhenRequestCurrentDataWillCallWeatherAPI() throws Exception {
        UserCity city = new UserCity("name", 12.0, 12.0, "id");
        when(mUserCityRepository.getCurrentCity()).thenReturn(city);

        mModel.requestCurrentData();

        verify(mWeatherAPI).getCurrent(eq(city.getLat()), eq(city.getLon()), mCurrentCapture.capture());
    }

    @Test
    public void ensureWhenRequestCurrentDataFailureThenWillCallPresenterErrorMethod() throws Exception {
        UserCity city = new UserCity("name", 12.0, 12.0, "id");
        WeatherAPICallback<CurrentInterface> callback = getCurrentCallback(city);

        callback.onFailure();

        verify(mPresenter).onRequestCurrentWithError();
    }


    @Test
    public void ensureWhenRequestCurrentDataResponseIsSuccessfulThenWillCallPresenterSuccessMethod() throws Exception {
        UserCity city = new UserCity("name", 12.0, 12.0, "id");
        WeatherAPICallback<CurrentInterface> callback = getCurrentCallback(city);
        Current current = new Current();

        callback.onSuccess(current);

        verify(mPresenter).onRequestCurrentWithSuccess(current);
    }

    private WeatherAPICallback<CurrentInterface> getCurrentCallback(UserCity city) {
        when(mUserCityRepository.getCurrentCity()).thenReturn(city);
        mModel.requestCurrentData();
        verify(mWeatherAPI).getCurrent(eq(city.getLat()), eq(city.getLon()), mCurrentCapture.capture());
        return mCurrentCapture.getValue();
    }

    @Test
    public void ensureWhenRequestDailyDataWillCallWeatherAPI() throws Exception {
        UserCity city = new UserCity("name", 12.0, 12.0, "id");
        when(mUserCityRepository.getCurrentCity()).thenReturn(city);

        mModel.requestDailyData();

        verify(mWeatherAPI).getDaily(eq(city.getLat()), eq(city.getLon()), mDailyCapture.capture());
    }

    @Test
    public void ensureWhenRequestDailyDataFailureThenWillCallPresenterErrorMethod() throws Exception {
        UserCity city = new UserCity("name", 12.0, 12.0, "id");
        WeatherAPICallback<ForecastInterface> callback = getDailyCallback(city);

        callback.onFailure();

        verify(mPresenter).onRequestDailyWithError();
    }


    @Test
    public void ensureWhenRequestDailyDataResponseIsSuccessfulThenWillCallPresenterSuccessMethod() throws Exception {
        UserCity city = new UserCity("name", 12.0, 12.0, "id");
        WeatherAPICallback<ForecastInterface> callback = getDailyCallback(city);
        Forecast forecast = new Forecast();

        callback.onSuccess(forecast);

        verify(mPresenter).onRequestDailyWithSuccess(forecast);
    }

    private WeatherAPICallback<ForecastInterface> getDailyCallback(UserCity city) {
        when(mUserCityRepository.getCurrentCity()).thenReturn(city);

        mModel.requestDailyData();

        verify(mWeatherAPI).getDaily(eq(city.getLat()), eq(city.getLon()), mDailyCapture.capture());
        return mDailyCapture.getValue();
    }

    @Test
    public void ensureGetUserCityListWillReturnAllUserCities() throws Exception {
        UserCity city = new UserCity("name", 12.0, 12.0, "id");
        UserCity city2 = new UserCity("name2", 12.0, 12.0, "id2");
        List<UserCity> list = new ArrayList<>();
        list.add(city);
        list.add(city2);

        when(mUserCityRepository.getAll()).thenReturn(list);

        assertEquals(list, mModel.getUserCityList());
    }

    @Test
    public void ensureSelectCityWillSelectTheCorrectCity() throws Exception {
        UserCity city = new UserCity("name", 12.0, 12.0, "id");

        mModel.selectCity(city);

        verify(mUserCityRepository).selectCity(city.getId());
    }

    @Test
    public void ensureGetCurrentCityWillReturnTheCorrectCurrentCity() throws Exception {
        UserCity city = new UserCity("name", 12.0, 12.0, "id");

        when(mUserCityRepository.getCurrentCity()).thenReturn(city);

        assertEquals(city, mModel.getCurrentCity());
    }


}