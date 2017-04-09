package com.johnson.carstatus.task;

import com.johnson.carstatus.model.RealTimeData;
import com.johnson.carstatus.model.SensorResult;

/**
 * Created by johnson on 2017/3/11.
 */

public class DataProcess {

    @Deprecated
    static double filterMin = 0.8;

    /**
     * 处理加速度传感器数据
     */
    @Deprecated
    public static void processAccelerometer(RealTimeData data, RealTimeData initData, long initTime, long lastTime, long currentTime, double lastAccele, double lastSpeed) {
        double thisTimeAcceler = Datafilter(data.getZ() - initData.getZ());     // 当前z轴加速度（去除重力加速度影响）
        double costTime = (currentTime - lastTime) * 1.0 / 1000;    // 时间段
        data.setPassedTime((currentTime - initTime) * 1.0 / 1000);  // 总用时

        double averageAcceler = -(lastAccele + thisTimeAcceler) / 2; // 平均加速度（取反）
        data.setAcceleratedSpeed(averageAcceler);
        double speedNow = lastSpeed + averageAcceler * costTime;    // 当前速度
        data.setSpeed(speedNow);

        SensorResult.getInstance().setLastAccelerometer(thisTimeAcceler);
        SensorResult.getInstance().setLastSpeed(speedNow);
        SensorResult.getInstance().setLastGetAccTime(currentTime);
    }

    /**
     * 　抖动过滤
     *
     * @param data
     * @return
     */
    @Deprecated
    static double Datafilter(double data) {
        if (Math.abs(data) < filterMin)
            return 0;
        else
            return data;
    }

    /**
     * 处理陀螺仪数据
     *
     * @param data
     * @param lastData
     * @param lastTime
     * @param currentTime
     */
    @Deprecated
    public static void processGyroscope(RealTimeData data, RealTimeData lastData, long lastTime, long currentTime) {
        double costTime = (currentTime - lastTime) * 1.0 / 1000;
        /* 侧倾角度 */
        double sideTilt = lastData.getSideTilt() + ((data.getZ() + lastData.getZ()) / 2) * costTime * costTime;
        /* 纵倾角度 */
        double verticalTilt = lastData.getVerticalTilt() + ((data.getX() + lastData.getX()) / 2) * costTime * costTime;
        /* 转向角度 */
        double steeringAngle = lastData.getSteeringAngle() + ((data.getY() + lastData.getY()) / 2) * costTime * costTime;

        SensorResult.getInstance().getGyroscopeDatas().get(SensorResult.getInstance().getGyroscopeDatas().size() - 1).setSideTilt(sideTilt);
        SensorResult.getInstance().getGyroscopeDatas().get(SensorResult.getInstance().getGyroscopeDatas().size() - 1).setVerticalTilt(verticalTilt);
        SensorResult.getInstance().getGyroscopeDatas().get(SensorResult.getInstance().getGyroscopeDatas().size() - 1).setSteeringAngle(steeringAngle);

        SensorResult.getInstance().setLastRadTime(currentTime);
        SensorResult.getInstance().setLastRedData(data);
    }

    /**
     * 弧度转角度
     *
     * @param rad
     * @return
     */
    public static double radToDge(double rad) {
        return rad * 180 / 3.14;
    }

    /**
     * 速度单位转换
     * m/s 转换为 km/h
     *
     * @param speed
     * @return
     */
    public static double speedUnitTransfer(double speed) {
        return speed * 3600 * 1.0 / 1000;
    }

}
