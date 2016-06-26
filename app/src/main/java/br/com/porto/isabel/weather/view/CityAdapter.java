package br.com.porto.isabel.weather.view;


import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import br.com.porto.isabel.weather.model.City;

public class CityAdapter extends ArrayAdapter<City> {

    public CityAdapter(Context context, int resource, List<City> cities) {
        super(context, resource, cities);
    }
}
