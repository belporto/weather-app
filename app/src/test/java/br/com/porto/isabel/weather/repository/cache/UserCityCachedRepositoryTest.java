package br.com.porto.isabel.weather.repository.cache;


import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.porto.isabel.weather.model.app.UserCity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Gson.class)
public class UserCityCachedRepositoryTest {

    @Mock
    private UserCityCacheStrategy mStrategy;

    private Gson mGson;

    private UserCityCachedRepository mRepository;

    private UserCity mFakeUserCity;
    private UserCity mFakeUserCity2;

    private Map<String, UserCity> mCityMap;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mGson = new Gson();
        mFakeUserCity = new UserCity("name", 12.0, 12.0, "id");
        mFakeUserCity2 = new UserCity("name2", 122.0, 122.0, "id2");
        mCityMap = new HashMap<>();
        mCityMap.put(mFakeUserCity.getId(), mFakeUserCity);
    }

    @Test
    public void ensureWhenCacheIsNullWillReturnTheDefaultCities() {
        setupRepositoryWithEmptyCache();
        List<UserCity> userCityList = mRepository.getAll();

        assertEquals(4, userCityList.size());

        List<String> defaultNames = new ArrayList<String>();
        defaultNames.add("Dublin");
        defaultNames.add("London");
        defaultNames.add("Barcelona");
        defaultNames.add("New York");

        for (UserCity userCity : userCityList) {
            assertTrue(defaultNames.contains(userCity.getName()));
        }
    }

    @Test
    public void ensureWhenCacheIsNullTheCurrentElementWillNotBeNull() {
        setupRepositoryWithEmptyCache();

        assertNotNull(mRepository.getCurrentCity());
    }

    @Test
    public void ensureWhenCachedTheCurrentElementWillBeTheCachedOne() {
        setupRepositoryWithNotEmptyCache();

        assertEquals(mFakeUserCity, mRepository.getCurrentCity());
    }

    @Test
    public void ensureWhenCacheGetAllCitiesWillReturnTheCitiesCached() {
        setupRepositoryWithNotEmptyCache();

        assertEquals(mCityMap.values().size(), mRepository.getAll().size());
        assertEquals(new ArrayList<>(mCityMap.values()).get(0), mRepository.getAll().get(0));
    }

    @Test
    public void ensureAddCityWillSaveTheCityMap() {
        setupRepositoryWithNotEmptyCache();

        mRepository.addCity(mFakeUserCity);

        verify(mStrategy).saveCities(anyMap());
    }

    @Test
    public void ensureRemoveCityWillSaveTheCityMap() {
        setupRepositoryWithNotEmptyCache();

        mRepository.removeCity("fakeId");

        verify(mStrategy).saveCities(anyMap());
    }

    @Test
    public void ensureSelectCityWillSaveTheCurrentCity() {
        setupRepositoryWithNotEmptyCache();

        mRepository.selectCity("fakeId");

        verify(mStrategy).saveCurrentCity(any(UserCity.class));
    }

    @Test
    public void ensureWhenRemoveTheCurrentElementThenTheCurrentElementWillBeChanged() {
        setupRepositoryWithNotEmptyCache();

        mRepository.addCity(mFakeUserCity2);
        mRepository.selectCity(mFakeUserCity2.getId());
        mRepository.removeCity(mFakeUserCity2.getId());

        assertEquals(mFakeUserCity, mRepository.getCurrentCity());
        verify(mStrategy).saveCurrentCity(mFakeUserCity);
    }


    @Test
    public void ensureWhenRemoveTheLastElementThenCurrentWillBeNull() {
        setupRepositoryWithNotEmptyCache();

        mRepository.removeCity(mFakeUserCity.getId());

        assertNull(mRepository.getCurrentCity());
    }

    @Test
    public void ensureWhenRemoveTheLastElementThenCityListWillBeEmpty() {
        setupRepositoryWithNotEmptyCache();

        mRepository.removeCity(mFakeUserCity.getId());

        assertTrue(mRepository.getAll().isEmpty());
    }


    private void setupRepositoryWithEmptyCache() {
        when(mStrategy.getCities()).thenReturn(null);
        when(mStrategy.getCurrent()).thenReturn(null);
        mRepository = new UserCityCachedRepository(mStrategy, mGson);
    }

    private void setupRepositoryWithNotEmptyCache() {
        when(mStrategy.getCities()).thenReturn(mCityMap);
        when(mStrategy.getCurrent()).thenReturn(mFakeUserCity);
        mRepository = new UserCityCachedRepository(mStrategy, mGson);
    }

}
