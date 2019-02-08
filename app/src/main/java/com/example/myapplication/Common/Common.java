package com.example.myapplication.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static final String APP_ID = "7260cad761c0d25fa54f44ca5bb7882d";
    public static Location current_location = null;

    public static String converterUnixToDate(long dt) {

        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy (EEE)");
        String formatted = sdf.format(date);
        return formatted;
    }

    public static String converterUnixToHour(long dt) {

        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formatted = sdf.format(date);
        return formatted;

    }
}
