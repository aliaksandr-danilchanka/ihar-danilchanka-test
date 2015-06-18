package by.ihar.weather.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import by.ihar.weather.android.R;
import by.ihar.weather.android.model.ForecastWeather;

public class ForecastWeatherAdapter extends RecyclerView.Adapter<ForecastWeatherAdapter.ViewHolder> {

    private ForecastWeather[] mWeathers;
    private Context mContext;


    public ForecastWeatherAdapter(ForecastWeather[] weathers, Context context) {
        mWeathers = weathers;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_forecast_weather_item, parent, false));
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ForecastWeather weather = mWeathers[position];
        Picasso.with(mContext)
                .load(weather.weatherIconUrl)
                .into(holder.mImgStatusForecast);
        holder.mTxtDay.setText(weather.day);
        holder.mTxtStatus.setText(weather.weatherStatus);
        holder.mTxtTemperature.setText(weather.getTemperature());
    }


    @Override
    public int getItemCount() {
        return mWeathers.length;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.img_status_forecast)
        ImageView mImgStatusForecast;
        @InjectView(R.id.txt_day)
        TextView mTxtDay;
        @InjectView(R.id.txt_temperature)
        TextView mTxtTemperature;
        @InjectView(R.id.txt_status)
        TextView mTxtStatus;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
