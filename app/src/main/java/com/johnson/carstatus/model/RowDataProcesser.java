package com.johnson.carstatus.model;

/**
 * Created by johnson on 2017/3/19.
 */
public class RowDataProcesser {
    boolean isGyroscopeOK = false;
    boolean isAccelerometerOK=false;
    boolean isGpsOK = false;

    Boolean writeFlag=new Boolean(false);

    RowData rowData;

    /**
     * insert into data cache and prepare to write to SD card
     */
    synchronized void doNext() {
        if (isGyroscopeOK() && isAccelerometerOK()) {
            DataCache cache = DataCache.getInstance();
            cache.add(rowData);

            setGpsOK(false);
            setGyroscopeOK(false);
            setAccelerometerOK(false);
        }
    }

    double lastSpeed = 0, lastAccelerateSpeed = 0, lastPassedTime = 0, lastTime = 0;

    /**
     * set gps data
     *
     * @param speed
     * @param acceleratedSpeed
     */
    @Deprecated
    public void setGpsData(double speed, double acceleratedSpeed, double currentTime) {
        synchronized (writeFlag){
//            if(!isGyroscopeOK() && !isAccelerometerOK()){
//                rowData=new RowData();
//            }
//
//            rowData.setSpeed(speed);
//            rowData.setAcceleratedSpeed(acceleratedSpeed);
//            setGpsOK(true);
//
//            lastSpeed = speed;
//            lastAccelerateSpeed = acceleratedSpeed;
//            lastTime = currentTime;
//
//            doNext();
        }
    }

    /**
     * set gyroscope data
     *
     * @param sideTilt
     * @param verticalTilt
     * @param steeringAngle
     */
    public void setGyroscopeData(double sideTilt, double verticalTilt, double steeringAngle, double passedTime) {
        synchronized (writeFlag){
            if (!isGyroscopeOK() && !isGpsOK() && !isAccelerometerOK()) {
                rowData = new RowData();
            }

            rowData.setSideTilt(sideTilt);
            rowData.setVerticalTilt(verticalTilt);
            rowData.setSteeringAngle(steeringAngle);
            rowData.setPassedTime(passedTime);
            setGyroscopeOK(true);

            doNext();
        }
    }

    /**
     * set accelerometer data
     *
     * @param accVertical
     * @param accHorizon
     * @param speed
     */
    public void setAccelerometerData(double accVertical,double accHorizon,double speed){
        synchronized (writeFlag){
            if (!isGyroscopeOK() && !isGpsOK() && !isAccelerometerOK()) {
                rowData = new RowData();
            }

            rowData.setVerticalAcc(accVertical);
            rowData.setHorizontalAcc(accHorizon);
            rowData.setSpeed(speed);
            setAccelerometerOK(true);

            doNext();
        }
    }

    private RowDataProcesser() {

    }

    private static class RowDataHolder {
        private static final RowDataProcesser INSTANCE = new RowDataProcesser();
    }

    /**
     * get singleton instance
     *
     * @return
     */
    public static RowDataProcesser getInstance() {
        return RowDataHolder.INSTANCE;
    }

    public boolean isGyroscopeOK() {
        return isGyroscopeOK;
    }

    public void setGyroscopeOK(boolean gyroscopeOK) {
        isGyroscopeOK = gyroscopeOK;
    }

    public boolean isAccelerometerOK() {
        return isAccelerometerOK;
    }

    public void setAccelerometerOK(boolean accelerometerOK) {
        isAccelerometerOK = accelerometerOK;
    }

    public boolean isGpsOK() {
        return isGpsOK;
    }

    public void setGpsOK(boolean gpsOK) {
        isGpsOK = gpsOK;
    }
}
