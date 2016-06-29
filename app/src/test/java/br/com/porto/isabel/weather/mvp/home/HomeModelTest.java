package br.com.porto.isabel.weather.mvp.home;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Daily;
import br.com.porto.isabel.weather.model.Forecast;
import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.mvp.home.HomeContract;
import br.com.porto.isabel.weather.mvp.home.HomeModel;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.service.WeatherAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest(Response.class)
public class HomeModelTest {

    @Mock
    private WeatherAPI mWeatherAPI;

    @Mock
    private UserCityRepository mUserCityRepository;

    @Captor
    private ArgumentCaptor<Callback<Current>> mCurrentCapture;

    @Captor
    private ArgumentCaptor<Callback<Forecast>> mDailyCapture;

    @Mock
    private Call<Current> mCallCurrent;

    @Mock
    private Call<Forecast> mCallForecast;

    @Mock
    private Response<Current> mResponseCurrent;

    @Mock
    private Response<Forecast> mResponseForecast;

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
        Callback<Current> callback = getCurrentCallback(city);

        callback.onFailure(mCallCurrent, new Exception());

        verify(mPresenter).onRequestCurrentWithError();
    }

    @Test
    public void ensureWhenRequestCurrentDataResponseWithErrorThenWillCallPresenterErrorMethod() throws Exception {
        UserCity city = new UserCity("name", 12.0, 12.0, "id");
        Callback<Current> callback = getCurrentCallback(city);
        when(mResponseCurrent.isSuccessful()).thenReturn(false);

        callback.onResponse(mCallCurrent, mResponseCurrent);

        verify(mPresenter).onRequestCurrentWithError();
    }


    @Test
    public void ensureWhenRequestCurrentDataResponseIsSuccessfulThenWillCallPresenterSuccessMethod() throws Exception {
        UserCity city = new UserCity("name", 12.0, 12.0, "id");
        Callback<Current> callback = getCurrentCallback(city);
        when(mResponseCurrent.isSuccessful()).thenReturn(true);
        Current current = new Current();
        when(mResponseCurrent.body()).thenReturn(current);

        callback.onResponse(mCallCurrent, mResponseCurrent);

        verify(mPresenter).onRequestCurrentWithSuccess(current);
    }

    private Callback<Current> getCurrentCallback(UserCity city) {
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
        Callback<Forecast> callback = getDailyCallback(city);

        callback.onFailure(mCallForecast, new Exception());

        verify(mPresenter).onRequestDailyWithError();
    }

    @Test
    public void ensureWhenRequestDailyDataResponseWithErrorThenWillCallPresenterErrorMethod() throws Exception {
        UserCity city = new UserCity("name", 12.0, 12.0, "id");
        Callback<Forecast> callback = getDailyCallback(city);
        when(mResponseForecast.isSuccessful()).thenReturn(false);

        callback.onResponse(mCallForecast, mResponseForecast);

        verify(mPresenter).onRequestDailyWithError();
    }


    @Test
    public void ensureWhenRequestDailyDataResponseIsSuccessfulThenWillCallPresenterSuccessMethod() throws Exception {
        UserCity city = new UserCity("name", 12.0, 12.0, "id");
        Callback<Forecast> callback = getDailyCallback(city);
        when(mResponseForecast.isSuccessful()).thenReturn(true);
        Forecast forecast = new Forecast();
        when(mResponseForecast.body()).thenReturn(forecast);

        callback.onResponse(mCallForecast, mResponseForecast);

        verify(mPresenter).onRequestDailyWithSuccess(forecast);
    }

    private Callback<Forecast> getDailyCallback(UserCity city) {
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