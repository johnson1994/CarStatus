package com.johnson.carstatus.model;

/**
 * Created by johnson on 2017/3/11.
 */
@Deprecated
public class RealTimeData {

    double x;
    double y;
    double z;

    /* 时间 */
    double passedTime;
    /* 速度 */
    double speed;
    /* 加速度 */
    double acceleratedSpeed;

    /* 侧倾角度 */
    double sideTilt=0;
    /* 纵倾角度 */
    double verticalTilt=0;
    /* 转向角度 */
    double steeringAngle=0;

    public RealTimeData() {
        super();
    }

    public RealTimeData(double x, double y, double z) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }


    public double getPassedTime() {
        return passedTime;
    }

    public void setPassedTime(double passedTime) {
        this.passedTime = passedTime;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAcceleratedSpeed() {
        return acceleratedSpeed;
    }

    public void setAcceleratedSpeed(double acceleratedSpeed) {
        this.acceleratedSpeed = acceleratedSpeed;
    }

    public double getSideTilt() {
        return sideTilt;
    }

    public void setSideTilt(double sideTilt) {
        this.sideTilt = sideTilt;
    }

    public double getVerticalTilt() {
        return verticalTilt;
    }

    public void setVerticalTilt(double verticalTilt) {
        this.verticalTilt = verticalTilt;
    }

    public double getSteeringAngle() {
        return steeringAngle;
    }

    public void setSteeringAngle(double steeringAngle) {
        this.steeringAngle = steeringAngle;
    }
}
