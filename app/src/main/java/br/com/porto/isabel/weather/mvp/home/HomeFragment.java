package br.com.porto.isabel.weather.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.twistedequations.mvl.rx.AndroidRxSchedulers;
import com.twistedequations.mvl.rx.DefaultAndroidRxSchedulers;

import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.repository.cache.SharedPreferencesUserCityCacheStrategy;
import br.com.porto.isabel.weather.repository.cache.UserCityCacheStrategy;
import br.com.porto.isabel.weather.repository.cache.UserCityCachedRepository;
import br.com.porto.isabel.weather.service.retrofit.openweather.RetrofitWeatherAPI;

public class HomeFragment extends Fragment {

    private HomeContract.PresenterContract mPresenter;

    private HomeContract.ViewContract mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Gson gson = new Gson();
        UserCityCacheStrategy cacheStrategy = new SharedPreferencesUserCityCacheStrategy(getContext(), gson);
        UserCityRepository userCityRepository = new UserCityCachedRepository(cacheStrategy, gson);
        HomeModel model = new HomeModel(new RetrofitWeatherAPI(), userCityRepository);
        mView = new HomeView(getContext());
        HomePresenter presenter = new HomePresenter(mView, model, new DefaultAndroidRxSchedulers());
        mPresenter = presenter;
        mView.setPresente(presenter);
        setHasOptionsMenu(true);
        mPresenter.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar supportActionBar = ((AppCompatActivity) getContext()).getSupportActionBar();
        supportActionBar.setTitle(null);
    }

    //    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelable(CURRENT, mCurrent);
//        outState.putParcelable(FORECAST, mForecast);
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mView.getView();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mView.onCreateOptionsMenu(menu, inflater);
    }
}
