package br.com.porto.isabel.weather.mvp.configuration;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import java.util.List;

import br.com.porto.isabel.weather.DrawerActivity;
import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.bus.BusProvider;
import br.com.porto.isabel.weather.model.app.UserCity;
import br.com.porto.isabel.weather.mvp.configuration.places.PlacesDialogFragment;
import br.com.porto.isabel.weather.repository.UserCityRepository;
import br.com.porto.isabel.weather.repository.cache.SharedPreferencesUserCityCacheStrategy;
import br.com.porto.isabel.weather.repository.cache.UserCityCacheStrategy;
import br.com.porto.isabel.weather.repository.cache.UserCityCachedRepository;
import br.com.porto.isabel.weather.view.adapter.usercity.UserCityListAdapter;
import br.com.porto.isabel.weather.view.touch.CityTouchCallback;
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
        configureMVP();

        View view = configureView(inflater, container);

        mDialogFragment = PlacesDialogFragment.newInstance();

        mPresenter.init();


        return view;
    }

    private void configureMVP() {
        Gson gson = new Gson();
        UserCityCacheStrategy cacheStrategy = new SharedPreferencesUserCityCacheStrategy(getContext(), gson);
        UserCityRepository userCityRepository = new UserCityCachedRepository(cacheStrategy, gson);
        ConfigurationModel model = new ConfigurationModel(userCityRepository);
        mPresenter = new ConfigurationPresenter(this, model);
        model.setPresenter(mPresenter);
    }

    private View configureView(LayoutInflater inflater, @Nullable ViewGroup container) {
        final View view = inflater.inflate(R.layout.configuration_fragment, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new UserCityListAdapter(mPresenter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        supportActionBar.setTitle(R.string.city_manager_menu);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new CityTouchCallback(mAdapter, mPresenter, getActivity(), getResources(), 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

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
    public void deleteCity(UserCity city) {
        mAdapter.removeCity(city);
    }

    @Override
    public void addCity(UserCity city) {
        mAdapter.addCity(city);
    }

    @Override
    public void showLimitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder.setTitle(R.string.dialog_limit_title);
        builder.setMessage(R.string.dialog_limit_message);
        builder.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    @Override
    public void revertSwipe() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCityInformation(UserCity userCity) {
        DrawerActivity drawerActivity = (DrawerActivity) getActivity();
        drawerActivity.selectItem(R.id.nav_home);
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
