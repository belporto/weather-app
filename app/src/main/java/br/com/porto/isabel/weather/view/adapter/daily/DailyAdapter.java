package br.com.porto.isabel.weather.view.adapter.daily;

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
import br.com.porto.isabel.weather.view.IconUtil;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    private final DailyCallback mDailyCallback;
    private DateFormatter mDateFormatter;
    private IconUtil mIconUtil;
    protected List<Daily> mDaily;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

        @BindView(R.id.daily_content)
        public View content;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            content.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Daily daily = mDaily.get(getAdapterPosition());
            mDailyCallback.onDailySelected(daily);
        }
    }

    public DailyAdapter(DateFormatter dateFormatter, IconUtil iconUtil, DailyCallback dailyCallback) {
        mDateFormatter = dateFormatter;
        mDaily = new ArrayList<>();
        mIconUtil = iconUtil;
        mDailyCallback = dailyCallback;
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


