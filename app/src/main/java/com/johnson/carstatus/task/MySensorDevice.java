package com.johnson.carstatus.task;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import com.johnson.carstatus.MainActivity;
import com.johnson.carstatus.R;
import com.johnson.carstatus.model.DataCache;
import com.johnson.carstatus.model.SensorResult;

/**
 * Created by johnson on 2017/3/11.
 */
public class MySensorDevice {
    private SensorManager mSensorManager;
    private LocationManager locationManager;

    private Sensor accelerometer, gyroscope,orientation;
    private MySensorListener accelerometerListener, gyroscopeListener,orientationListener;
    private MyLocationListener locationListener;

    MainActivity mainActivity;

    public MySensorDevice(SensorManager mSensorManager, MainActivity mainActivity, LocationManager locationManager) {
        this.mSensorManager = mSensorManager;
        this.mainActivity = mainActivity;
        this.locationManager = locationManager;

        accelerometerListener = new MySensorListener(Sensor.TYPE_LINEAR_ACCELERATION, mainActivity);
        gyroscopeListener = new MySensorListener(Sensor.TYPE_GYROSCOPE, mainActivity);
        orientationListener=new MySensorListener(Sensor.TYPE_ORIENTATION,mainActivity);

        locationListener = new MyLocationListener(mainActivity);
    }

    /**
     * 检查传感器是否存在
     *
     * @return SensorResult
     */
    public SensorResult sensorCheck() {
        SensorResult result = SensorResult.getInstance();
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            result.setAcceleroneterExist(true);
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        }
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            result.setGyroscopeExist(true);
            gyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null) {
            result.setOrientationExist(true);
            orientation=mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        return result;
    }

    public void start() {
        reset();

        DataCache cache = DataCache.getInstance();
        cache.setMainActivity(mainActivity);
        cache.setTimeStamp(System.currentTimeMillis());

        mSensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
//        mSensorManager.registerListener(gyroscopeListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(orientationListener,orientation,SensorManager.SENSOR_DELAY_NORMAL);

//        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//        mainActivity.show("当前状态","正在获得GPS状态信息...");
    }

    public void stop() {
        mSensorManager.unregisterListener(accelerometerListener);
//        mSensorManager.unregisterListener(gyroscopeListener);
        mSensorManager.unregisterListener(orientationListener);

//        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.removeUpdates(locationListener);


        DataCache cache=DataCache.getInstance();
        cache.write();
        new AlertDialog.Builder(mainActivity).setTitle("系统提示")
                .setMessage("文件已保存至" + Output.pathName + cache.getTimeStamp()+".csv")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

        /*
        int sizeAcc = SensorResult.getInstance().getAccelerometerDatas().size();
        int sizeGyro = SensorResult.getInstance().getGyroscopeDatas().size();
        Output.write(
                SensorResult.getInstance().getAccelerometerDatas(),
                SensorResult.getInstance().getGyroscopeDatas(),
                (sizeAcc <= sizeGyro) ? sizeAcc : sizeGyro,
                MySensorListener.fileName);

        new AlertDialog.Builder(mainActivity).setTitle("系统提示")
                .setMessage("文件已保存至" + Output.pathName + MySensorListener.fileName)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();*/
    }

    public void reset() {
        /*
        SensorResult.getInstance().setLastSpeed(0);
        SensorResult.getInstance().setLastAccelerometer(0);
        SensorResult.getInstance().setAcclerometerInit(null);
        SensorResult.getInstance().getAccelerometerDatas().clear();

        SensorResult.getInstance().setLastRedData(null);
        SensorResult.getInstance().getGyroscopeDatas().clear();*/

        gyroscopeListener.reset();
        accelerometerListener.reset();
        orientationListener.reset();

//        locationListener.reset();

        DataCache rowCache=DataCache.getInstance();
        rowCache.getRowCache().clear();
    }
}
