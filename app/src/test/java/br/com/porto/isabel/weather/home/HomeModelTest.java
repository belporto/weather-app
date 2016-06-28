package br.com.porto.isabel.weather.home;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.mvp.home.HomeModel;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.service.WeatherAPI;
import retrofit2.Callback;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
public class HomeModelTest {

    @Mock
    private WeatherAPI mWeatherAPI;


    private HomeModel mModel;

    @Mock
    private UserCityRepository mUserCityRepository;

    @Captor
    private ArgumentCaptor<Callback<Current>> mCurrentCapture;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mModel = new HomeModel(mWeatherAPI, mUserCityRepository);
    }

    @Test
    public void ensureWhenRequestCurrentDataWillCallWeatherAPI() throws Exception {
        UserCity city = new UserCity("name", 12.0, 12.0, "id");
        when(mUserCityRepository.getCurrentCity()).thenReturn(city);

        mModel.requestCurrentData();

        verify(mWeatherAPI).getCurrent(eq(city.getLat()), eq(city.getLon()), mCurrentCapture.capture());
    }


}