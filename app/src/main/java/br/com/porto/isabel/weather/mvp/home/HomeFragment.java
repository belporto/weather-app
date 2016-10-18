package br.com.porto.isabel.weather.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import br.com.porto.isabel.weather.WeatherAppGraph;

public class HomeFragment extends Fragment {

    @Inject
    HomeContract.PresenterContract mPresenter;

    @Inject
    HomeContract.ViewContract mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeatherAppGraph.get().inject(this);
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

}
