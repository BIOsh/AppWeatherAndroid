package com.example.myapplication.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_date_time, txt_description, txt_tempr;
        ImageView img_weather;

        public MyViewHolder(View itemView) {
            super(itemView);

            img_weather = itemView.findViewById(R.id.img_weather);
            txt_date_time = itemView.findViewById(R.id.txt_date_time);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_tempr = itemView.findViewById(R.id.txt_tempr);

        }
    }
}
