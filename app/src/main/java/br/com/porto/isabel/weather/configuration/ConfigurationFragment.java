package br.com.porto.isabel.weather.configuration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.model.City;
import br.com.porto.isabel.weather.repository.MemoryUserCityRepository;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.view.CityListAdapter;
import br.com.porto.isabel.weather.view.CityTouchCallback;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfigurationFragment extends Fragment implements ConfigurationContract.ViewContract {

    @BindView(R.id.configuration_city_list)
    RecyclerView mRecyclerView;

    private CityListAdapter mAdapter;

    private ConfigurationContract.PresenterContract mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = configureView(inflater, container);

        UserCityRepository userCityRepository = MemoryUserCityRepository.getInstance();

        ConfigurationModel model = new ConfigurationModel(userCityRepository);
        mPresenter = new ConfigurationPresenter(this, model);
        model.setPresenter(mPresenter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new CityTouchCallback(mAdapter, mPresenter, getActivity(), getResources(), 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mPresenter.init();

        return view;
    }

    private View configureView(LayoutInflater inflater, @Nullable ViewGroup container) {
        final View view = inflater.inflate(R.layout.configuration_fragment, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new CityListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void showUserCity(List<City> userCityList) {
        mAdapter.setCityList(userCityList);
    }

    @Override
    public void onCityDeleted(City city) {
        mAdapter.removeCity(city);
    }
}
