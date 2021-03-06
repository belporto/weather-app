package br.com.porto.isabel.weather;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.porto.isabel.weather.mvp.configuration.ConfigurationFragment;
import br.com.porto.isabel.weather.mvp.home.HomeFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String CURRENT_FRAGMENT_TAG = "currentFragmentTag";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            HomeFragment fragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, CURRENT_FRAGMENT_TAG)
                    .commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    public void selectItem(int id) {
        navigationView.setCheckedItem(id);
        if (id == R.id.nav_home) {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.container, new HomeFragment()).
                    commit();

        } else if (id == R.id.nav_cities) {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.container, new ConfigurationFragment()).
                    commit();
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        selectItem(id);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
