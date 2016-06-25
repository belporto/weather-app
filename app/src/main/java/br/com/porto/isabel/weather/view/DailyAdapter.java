package br.com.porto.isabel.weather.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.formatter.DateFormatter;
import br.com.porto.isabel.weather.model.Daily;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    private DateFormatter mDateFormatter;
    private IconUtil mIconUtil;
    private List<Daily> mDaily;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.daily_week_day)
        public TextView weekDayTextView;

        @BindView(R.id.daily_weather_image)
        public ImageView weatherImageView;

        @BindView(R.id.daily_max_temp)
        public TextView maxTemperatureTextView;

        @BindView(R.id.daily_min_temp)
        public TextView minTemperatureTextView;

        @BindView(R.id.daily_humidity)
        public TextView humidityTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public DailyAdapter(DateFormatter dateFormatter, IconUtil iconUtil) {
        mDateFormatter = dateFormatter;
        mDaily = new ArrayList<>();
        mIconUtil = iconUtil;
    }

    public void setDailyList(List<Daily> daily) {
        mDaily = daily;
        notifyDataSetChanged();
    }

    @Override
    public DailyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_component, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Daily daily = mDaily.get(position);
        holder.humidityTextView.setText(daily.getHumidity().intValue() + "%");
        holder.maxTemperatureTextView.setText(daily.getMaxTemperature().intValue() + "°");
        holder.minTemperatureTextView.setText(daily.getMinTemperature().intValue() + "°");
        holder.weekDayTextView.setText(mDateFormatter.format(daily.getDate(), "EEE"));
        holder.weatherImageView.setImageResource(mIconUtil.getWeatherImageResource(daily.getWeatherCode()));
    }


    @Override
    public int getItemCount() {
        return mDaily.size();
    }
}


