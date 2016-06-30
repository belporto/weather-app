package br.com.porto.isabel.weather.mvp.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import br.com.porto.isabel.weather.model.app.UserCity;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
public class ConfigurationPresenterTest {

    @Mock
    private ConfigurationContract.ViewContract mView;

    @Mock
    private ConfigurationContract.ModelContract mModel;


    private ConfigurationPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new ConfigurationPresenter(mView, mModel);
    }

    @Test
    public void ensureInitWillShowUserCity() {
        List<UserCity> cityList = new ArrayList<>();
        when(mModel.getUserCityList()).thenReturn(cityList);

        mPresenter.init();

        verify(mView).showUserCity(cityList);
    }

    @Test
    public void ensureWhenAddCityClickedThenShowCityDialog() {
        mPresenter.onAddCityClicked();

        verify(mView).showCityDialog();
    }

    @Test
    public void ensureOnCityClickedWillAddCityAndCallView() {
        UserCity userCity = new UserCity("FakeName", 1.2, 1.2, "FakeID");

        mPresenter.onCityClicked(userCity);

        verify(mModel).addCity(userCity);
        verify(mView).onCityAdded(userCity);
    }

    @Test
    public void ensureOnCitySizeLimitReachedWillRevertSwipeAndShowDialog() {
        mPresenter.onCitySizeLimitReached();

        verify(mView).revertSwipe();
        verify(mView).showLimitDialog();
    }

    @Test
    public void ensureOnCityDeletedWillCallView() {
        UserCity userCity = new UserCity("FakeName", 1.2, 1.2, "FakeID");

        mPresenter.onCityDeleted(userCity);

        verify(mView).onCityDeleted(userCity);
    }

    @Test
    public void ensureOnSwipeWillDeleteCity() {
        UserCity userCity = new UserCity("FakeName", 1.2, 1.2, "FakeID");

        mPresenter.onSwipe(userCity);

        verify(mModel).deleteCity(userCity);
    }

    @Test
    public void ensureOnCitySelectedWillSelectCityAndShowCityInformation() {
        UserCity userCity = new UserCity("FakeName", 1.2, 1.2, "FakeID");

        mPresenter.onCitySelected(userCity);

        verify(mModel).selectCity(userCity);
        verify(mView).showCityInformation(userCity);
    }


}