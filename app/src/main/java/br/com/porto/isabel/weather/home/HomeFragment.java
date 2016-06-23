package br.com.porto.isabel.weather.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.model.Current;
import br.com.porto.isabel.weather.model.Forecast;
import br.com.porto.isabel.weather.service.WeatherAPI;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements HomeContract.ViewContract {

    private HomeContract.PresenterContract mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);

        HomeModel model = new HomeModel(new WeatherAPI());
        mPresenter = new HomePresenter(this, model);
        model.setPresenter(mPresenter);

        mPresenter.init();

        return view;
    }

    @Override
    public void showProgress() {
        //TODO:
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showCurrentData(Current current) {

    }

    @Override
    public void showForecast(Forecast forecast) {

    }

}
