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
import br.com.porto.isabel.weather.repository.UserCityRepository;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
public class ConfigurationModelTest {

    @Mock
    private ConfigurationContract.PresenterContract mPresenter;

    @Mock
    private UserCityRepository mRepository;

    private ConfigurationModel mModel;

    private UserCity mUserCity;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mModel = new ConfigurationModel(mRepository);
        mModel.setPresenter(mPresenter);
        mUserCity = new UserCity("name", 12.0, 12.0, "id");
    }

    @Test
    public void ensureAddCityWillAddTheCityInRepository() throws Exception {
        mModel.addCity(mUserCity);

        verify(mRepository).addCity(mUserCity);
    }

    @Test
    public void ensureDeleteCityWillRemoveTheCityAndCallOnCityDeleted() throws Exception {
        List<UserCity> all = new ArrayList<>();
        all.add(new UserCity("FakeName", 1.0, 1.0, "fakeid"));
        all.add(mUserCity);
        when(mRepository.getAll()).thenReturn(all);

        mModel.deleteCity(mUserCity);

        verify(mRepository).removeCity(mUserCity.getId());
        verify(mPresenter).onCityDeleted(mUserCity);
    }

    @Test
    public void ensureDeleteCityWontDeleteCityIfHasOnlyOneCity() throws Exception {
        List<UserCity> all = new ArrayList<>();
        all.add(mUserCity);
        when(mRepository.getAll()).thenReturn(all);

        mModel.deleteCity(mUserCity);

        verify(mPresenter).onCitySizeLimitReached();
    }

    @Test
    public void ensureGetUserCityListWillReturnAlCitiesInRepository() throws Exception {
        List<UserCity> all = new ArrayList<>();
        all.add(mUserCity);
        when(mRepository.getAll()).thenReturn(all);

        assertEquals(all, mModel.getUserCityList());
    }

    @Test
    public void ensureSelectCityWillSelectTheCityInRepository() throws Exception {
        mModel.selectCity(mUserCity);

        verify(mRepository).selectCity(mUserCity.getId());
    }


}