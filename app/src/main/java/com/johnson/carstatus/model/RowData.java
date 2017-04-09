package com.johnson.carstatus.model;

/**
 * Created by johnson on 2017/3/19.
 */
public class RowData {

    /* 纵向加速度 */
    double verticalAcc;
    /* 横向加速度 */
    double horizontalAcc;

    /* gyroscope data */
    double x;
    double y;
    double z;
    /* 侧倾角度 */
    double sideTilt = 0;
    /* 纵倾角度 */
    double verticalTilt = 0;
    /* 转向角度 */
    double steeringAngle = 0;

    /* 时间 */
    double passedTime;
    /* 速度 */
    double speed;

    public double getVerticalAcc() {
        return verticalAcc;
    }

    public void setVerticalAcc(double verticalAcc) {
        this.verticalAcc = verticalAcc;
    }

    public double getHorizontalAcc() {
        return horizontalAcc;
    }

    public void setHorizontalAcc(double horizontalAcc) {
        this.horizontalAcc = horizontalAcc;
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
}
