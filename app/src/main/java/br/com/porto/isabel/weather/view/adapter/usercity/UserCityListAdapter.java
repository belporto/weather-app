
package br.com.porto.isabel.weather.view.adapter.usercity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.model.user.UserCity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserCityListAdapter extends RecyclerView.Adapter<UserCityListAdapter.ViewHolder> {

    private List<UserCity> mCityList;

    public UserCity getItem(int position) {
        return mCityList.get(position);
    }

    public void removeCity(UserCity city) {
        mCityList.remove(city);
        notifyDataSetChanged();
    }

    public void addCity(UserCity city) {
        if (!mCityList.contains(city)) {
            mCityList.add(city);
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.city_name)
        public TextView cityName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public UserCityListAdapter() {
        mCityList = new ArrayList<>();
    }

    public void setCityList(List<UserCity> cityList) {
        mCityList = cityList;
        notifyDataSetChanged();
    }

    @Override
    public UserCityListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_component, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserCity city = mCityList.get(position);
        holder.cityName.setText(city.toString());
    }


    @Override
    public int getItemCount() {
        return mCityList.size();
    }
}



