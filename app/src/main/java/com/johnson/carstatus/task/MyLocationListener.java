package com.johnson.carstatus.task;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.johnson.carstatus.MainActivity;
import com.johnson.carstatus.model.RowDataProcesser;

/**
 * Created by johnson on 2017/3/19.
 */
public class MyLocationListener implements LocationListener {

    MainActivity mainActivity;

    static boolean isFirst = true;
    static long lastTime;
    static double lastSpeed = 0;

    public MyLocationListener(MainActivity mainActivity) {
        super();
        this.mainActivity = mainActivity;
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        float speed = location.getSpeed();
        String msg = "latitude:" + latitude + " longitude:" + longitude + " speed:" + speed;
        mainActivity.showSpeed(msg);

        long currentTime = System.currentTimeMillis();
        if (isFirst) {
            lastTime = currentTime;
            lastSpeed = speed;
            return;
        }

        double costTime = (currentTime - lastTime) * 1.0 / 1000;
        double acceleratedSpeed = (speed - lastSpeed) * 1.0 / costTime;

        RowDataProcesser processer = RowDataProcesser.getInstance();
        processer.setGpsData(speed, acceleratedSpeed, currentTime);

        lastTime = currentTime;
        lastSpeed = speed;
    }

    public void reset() {
        isFirst = true;
        lastTime = 0;
        lastSpeed = 0;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
