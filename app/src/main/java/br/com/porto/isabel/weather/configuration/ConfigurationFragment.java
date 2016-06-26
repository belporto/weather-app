package br.com.porto.isabel.weather.configuration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.bus.BusProvider;
import br.com.porto.isabel.weather.configuration.places.PlacesDialogFragment;
import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.repository.MemoryUserCityRepository;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.view.UserCityListAdapter;
import br.com.porto.isabel.weather.view.CityTouchCallback;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfigurationFragment extends Fragment implements ConfigurationContract.ViewContract {

    @BindView(R.id.configuration_city_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private UserCityListAdapter mAdapter;

    private ConfigurationContract.PresenterContract mPresenter;

    private PlacesDialogFragment mDialogFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = configureView(inflater, container);

        UserCityRepository userCityRepository = MemoryUserCityRepository.getInstance();

        ConfigurationModel model = new ConfigurationModel(userCityRepository);
        mPresenter = new ConfigurationPresenter(this, model);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new CityTouchCallback(mAdapter, mPresenter, getActivity(), getResources(), 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mPresenter.init();

        mDialogFragment = PlacesDialogFragment.newInstance();

        return view;
    }

    private View configureView(LayoutInflater inflater, @Nullable ViewGroup container) {
        final View view = inflater.inflate(R.layout.configuration_fragment, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new UserCityListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        supportActionBar.setTitle(R.string.city_manager_menu);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onAddCityClicked();
            }
        });
        return view;
    }

    @Override
    public void showUserCity(List<UserCity> userCityList) {
        mAdapter.setCityList(userCityList);
    }

    @Override
    public void onCityDeleted(UserCity city) {
        mAdapter.removeCity(city);
    }

    @Override
    public void onCityAdded(UserCity city) {
        mAdapter.addCity(city);
    }


    @Override
    public void showCityDialog() {
        mDialogFragment.show(getFragmentManager(), "dialog");
    }


    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onCitySelected(UserCity userCity) {
       mPresenter.onCityClicked(userCity);
    }
}
