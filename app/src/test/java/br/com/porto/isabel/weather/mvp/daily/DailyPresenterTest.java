package br.com.porto.isabel.weather.mvp.daily;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.porto.isabel.weather.model.openweather.Daily;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
public class DailyPresenterTest {

    @Mock
    private DailyContract.ViewContract mView;

    @Mock
    private DailyContract.ModelContract mModel;


    private DailyPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new DailyPresenter(mView, mModel);
    }

    @Test
    public void ensureInitWillShowCityNameAndDailyData() throws Exception {
        String cityName = "cityName";
        Daily dailyData = new Daily();
        when(mModel.getCityName()).thenReturn(cityName);
        when(mModel.getDaily()).thenReturn(dailyData);

        mPresenter.init();

        verify(mView).showCityName(cityName);
        verify(mView).showDailyData(dailyData);
    }


}