package com.johnson.carstatus.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnson on 2017/3/11.
 */
@Deprecated
public class SensorResult {

    /* 加速度 */
    boolean acceleroneterExist = false;
    /* 陀螺仪 */
    boolean gyroscopeExist = false;
    /* 方向传感器 */
    boolean orientationExist = false;

    /* 上一次的速度 */
    double lastSpeed = 0;
    /* 上一次加速度 */
    double lastAccelerometer = 0;
    /* 上一次获得加速度传感器的时间（毫秒数） */
    long lastGetAccTime;
    /* 初始时间（毫秒数） */
    long initTime;
    /* 加速度初始值（静止状态） */
    RealTimeData AcclerometerInit = null;
    /* 3轴加速度值 列表 */
    List<RealTimeData> accelerometerDatas = new ArrayList<RealTimeData>(1000);

    /* 上一次获得陀螺仪时间 */
    long lastRadTime;
    /* 上一次陀螺仪数据 */
    RealTimeData lastRedData = null;
    /* 3轴陀螺仪值 列表 */
    List<RealTimeData> gyroscopeDatas = new ArrayList<RealTimeData>(1000);

    private static class SensorResultHolder {
        private static final SensorResult INSTANCE = new SensorResult();
    }

    private SensorResult() {
    }

    public static final SensorResult getInstance() {
        return SensorResultHolder.INSTANCE;
    }

    public boolean isOrientationExist() {
        return orientationExist;
    }

    public void setOrientationExist(boolean orientationExist) {
        this.orientationExist = orientationExist;
    }

    public boolean isAcceleroneterExist() {
        return acceleroneterExist;
    }

    public void setAcceleroneterExist(boolean acceleroneterExist) {
        this.acceleroneterExist = acceleroneterExist;
    }

    public boolean isGyroscopeExist() {
        return gyroscopeExist;
    }

    public double getLastSpeed() {
        return lastSpeed;
    }

    public void setLastSpeed(double lastSpeed) {
        this.lastSpeed = lastSpeed;
    }

    public double getLastAccelerometer() {
        return lastAccelerometer;
    }

    public void setLastAccelerometer(double lastAccelerometer) {
        this.lastAccelerometer = lastAccelerometer;
    }

    public long getLastGetAccTime() {
        return lastGetAccTime;
    }

    public void setLastGetAccTime(long lastGetAccTime) {
        this.lastGetAccTime = lastGetAccTime;
    }

    public long getInitTime() {
        return initTime;
    }

    public void setInitTime(long initTime) {
        this.initTime = initTime;
    }

    public RealTimeData getAcclerometerInit() {
        return AcclerometerInit;
    }

    public void setAcclerometerInit(RealTimeData acclerometerInit) {
        AcclerometerInit = acclerometerInit;
    }

    public void setGyroscopeExist(boolean gyroscopeExist) {
        this.gyroscopeExist = gyroscopeExist;
    }

    public List<RealTimeData> getAccelerometerDatas() {
        return accelerometerDatas;
    }

    public void setAccelerometerDatas(List<RealTimeData> accelerometerDatas) {
        this.accelerometerDatas = accelerometerDatas;
    }

    public long getLastRadTime() {
        return lastRadTime;
    }

    public void setLastRadTime(long lastRadTime) {
        this.lastRadTime = lastRadTime;
    }

    public RealTimeData getLastRedData() {
        return lastRedData;
    }

    public void setLastRedData(RealTimeData lastRedData) {
        this.lastRedData = lastRedData;
    }

    public List<RealTimeData> getGyroscopeDatas() {
        return gyroscopeDatas;
    }

    public void setGyroscopeDatas(List<RealTimeData> gyroscopeDatas) {
        this.gyroscopeDatas = gyroscopeDatas;
    }
}
