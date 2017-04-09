package com.johnson.carstatus.task;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.johnson.carstatus.MainActivity;
import com.johnson.carstatus.model.RealTimeData;
import com.johnson.carstatus.model.RowDataProcesser;
import com.johnson.carstatus.model.SensorResult;

import java.util.List;

/**
 * Created by johnson on 2017/3/11.
 */
public class MySensorListener implements SensorEventListener {

    int sensorType;

    @Deprecated
    MainActivity mainActivity;

    public MySensorListener(int sensorType) {
        super();
        this.sensorType = sensorType;
    }

    @Deprecated
    public MySensorListener(int sensorType, MainActivity mainActivity) {
        super();
        this.sensorType = sensorType;
        this.mainActivity = mainActivity;
    }

    static boolean isFirst = true;
    static long lastTime;
    static long startTime;
    @Deprecated
    static double lastX = 0, lastY = 0, lastZ = 0;
    @Deprecated
    static double lastSide = 0, lastVertical = 0, lastSterring = 0;

    static boolean accIsFirst = true;
    static long accLastTime;
    static double accLastx = 0, accLasty = 0, accLastz = 0, lastSpeed = 0;

    static double originPatich,originRoll;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            /**
             * 角速度传感器
             */
            //setResult(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
            double x = sensorEvent.values[0];
            double y = sensorEvent.values[1];
            double z = sensorEvent.values[2];

            long currentTime = System.currentTimeMillis();

            if (isFirst) {
                lastTime = currentTime;
                startTime = lastTime;
                lastX = x;
                lastY = y;
                lastZ = z;
                isFirst = false;
                return;
            }

            double passedTime = (currentTime - startTime) * 1.0 / 1000;
            double costTime = (currentTime - lastTime) * 1.0 / 1000;
            /* 侧倾角度 */
            double sideTilt = lastSide + ((z + lastZ) / 2) * costTime ;
            /* 纵倾角度 */
            double verticalTilt = lastVertical + ((x + lastX) / 2) * costTime;
            /* 转向角度 */
            double steeringAngle = lastSterring + ((y + lastY) / 2) * costTime;

            RowDataProcesser processer = RowDataProcesser.getInstance();
            processer.setGyroscopeData(sideTilt, verticalTilt, steeringAngle, passedTime);

            lastTime = currentTime;
            lastX = x;
            lastY = y;
            lastZ = z;
            lastSide = sideTilt;
            lastVertical = verticalTilt;
            lastSterring = steeringAngle;
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            /**
             * 加速度传感器
             */
            double accx = sensorEvent.values[0];
            double accy = sensorEvent.values[1];
            double accz = sensorEvent.values[2];

            long currentTime = System.currentTimeMillis();

            if (accIsFirst) {
                accLastTime = currentTime;
                accLastx = accx;
                accLasty = accy;
                accLastz = accz;
                accIsFirst = false;
                return;
            }

            double costTime = (currentTime - lastTime) * 1.0 / 1000;
            /* 纵向加速度 */
            double accVertical = -accz;
            /* 横向加速度 */
            double accHorizon = -accx;
            double speed = lastSpeed + (-accz - accLastz) / 2 * costTime;

            RowDataProcesser processer = RowDataProcesser.getInstance();
            processer.setAccelerometerData(accx, accy, accz);

            accLastTime=currentTime;
            lastSpeed=speed;
            accLastx = accx;
            accLasty = accy;
            accLastz = accz;
        }else if(sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION){
            long currentTime = System.currentTimeMillis();
            double azimuth=sensorEvent.values[0];
            double pitch=sensorEvent.values[1];
            double roll=sensorEvent.values[2];

            if (isFirst) {
                lastTime = currentTime;
                startTime = lastTime;
                originPatich=pitch;
                originRoll=roll;
                isFirst = false;
                return;
            }

            double passedTime = (currentTime - startTime) * 1.0 / 1000;
            RowDataProcesser processer = RowDataProcesser.getInstance();
            processer.setGyroscopeData(roll-originRoll, pitch-originPatich, azimuth, passedTime);

            lastTime = currentTime;
        }
    }

    public void reset() {
        isFirst = true;
        lastTime = 0;
        lastX = 0;
        lastY = 0;
        lastZ = 0;
        lastSide = 0;
        lastVertical = 0;
        lastSterring = 0;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
