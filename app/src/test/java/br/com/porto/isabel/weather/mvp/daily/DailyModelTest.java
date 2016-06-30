package br.com.porto.isabel.weather.mvp.daily;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.porto.isabel.weather.model.openweather.Daily;
import br.com.porto.isabel.weather.model.user.UserCity;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
public class DailyModelTest {

    @Mock
    private DailyContract.PresenterContract mPresenter;

    @Mock
    private UserCity mUserCity;

    @Mock
    private Daily mDaily;

    private DailyModel mModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mModel = new DailyModel(mUserCity, mDaily);
    }

    @Test
    public void ensureGetCityNameWillReturnTheCorrectName() throws Exception {
        String cityName = "City Name";
        when(mUserCity.getName()).thenReturn(cityName);

        assertEquals(cityName, mModel.getCityName());
    }

    @Test
    public void ensureGetDailyWillReturnTheDailyPassedByConstructor() throws Exception {
        assertEquals(mDaily, mModel.getDaily());
    }


}