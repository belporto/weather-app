package br.com.porto.isabel.weather.view;


import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import br.com.porto.isabel.weather.model.user.UserCity;

public class UserCityAdapter extends ArrayAdapter<UserCity> {

    public UserCityAdapter(Context context, int resource, List<UserCity> cities) {
        super(context, resource, cities);
    }
}
