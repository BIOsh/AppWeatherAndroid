package com.example.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Common.Common;
import com.example.myapplication.Model.WeatherResult;
import com.example.myapplication.Retrofit.IOpenWeatherMap;
import com.example.myapplication.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWeatherFragment extends Fragment {

    ImageView img_weather;
    TextView txt_city_name, txt_date_time, txt_pressure, txt_coords, txt_sunset, txt_sunrise, txt_wind, txt_tempr, txt_humidity, txt_description;
    LinearLayout weather_panel;
    ProgressBar loading;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    static TodayWeatherFragment instance;

    public static TodayWeatherFragment getInstance(){

        if(instance == null)

            instance = new TodayWeatherFragment();
            return instance;

        }


    public TodayWeatherFragment() {

        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_today_weather, container, false);

        img_weather = itemView.findViewById(R.id.img_weather);
        txt_city_name = itemView.findViewById(R.id.txt_city_name);
        txt_wind = itemView.findViewById(R.id.txt_wind);
        txt_tempr = itemView.findViewById(R.id.txt_tempr);
        txt_pressure = itemView.findViewById(R.id.txt_pressure);
        txt_description = itemView.findViewById(R.id.txt_description);
        txt_sunset = itemView.findViewById(R.id.txt_sunset);
        txt_date_time = itemView.findViewById(R.id.txt_date_time);
        txt_humidity = itemView.findViewById(R.id.txt_humidity);
        txt_coords = itemView.findViewById(R.id.txt_coords);
        txt_sunrise = itemView.findViewById(R.id.txt_sunrise);

        weather_panel = itemView.findViewById(R.id.weather_panel);
        loading = itemView.findViewById(R.id.loading);

        getWeatherInformation();

        return itemView;
    }

    private void getWeatherInformation() {

        compositeDisposable.add(mService.getWeatherByLatLng(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {

                        //Load image
                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                            .append(weatherResult.getWeather().get(0).getIcon())
                        .append(".png").toString()).into(img_weather);

                        //Load information

                        txt_city_name.setText(weatherResult.getName());
                        txt_description.setText(new StringBuilder("Погода в ")
                        .append(weatherResult.getName().toString()));
                        txt_tempr.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append("°C").toString());
                        txt_date_time.setText(Common.converterUnixToDate(weatherResult.getDt()));
                        txt_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append(" гПа").toString());
                        txt_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append(" %").toString());
                        txt_sunrise.setText(Common.converterUnixToHour(weatherResult.getSys().getSunrise()));
                        txt_sunset.setText(Common.converterUnixToHour(weatherResult.getSys().getSunset()));
                        txt_coords.setText(new StringBuilder(weatherResult.getCoord().toString()).toString());

                        //Display panel
                        weather_panel.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Toast.makeText(getActivity(), ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
        );

    }

}
