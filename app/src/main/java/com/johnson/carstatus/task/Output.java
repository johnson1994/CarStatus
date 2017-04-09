package com.johnson.carstatus.task;


import android.os.Environment;
import android.util.Log;

import com.johnson.carstatus.model.RealTimeData;
import com.johnson.carstatus.model.RowData;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by johnson on 2017/3/13.
 */
public class Output {
    public static String pathName = "/sdcard/carStatus/";

    /**
     * 生成csv文件内容，并调用写文件
     *
     * @param accelerometerDatas
     * @param gyroscopeDatas
     * @param scale
     * @param fileName
     */
    @Deprecated
    public static void write(List<RealTimeData> accelerometerDatas, List<RealTimeData> gyroscopeDatas, int scale, String fileName) {
        RealTimeData accelerTemp, gyroscopeTemp;
        StringBuilder sb = new StringBuilder();
        sb.append("时间,车速,加速度,侧倾角度,纵倾角度,转向角度\r\n");
        for (int i = 0; i < scale; i++) {
            accelerTemp = accelerometerDatas.get(i);
            gyroscopeTemp = gyroscopeDatas.get(i);
            sb.append(accelerTemp.getPassedTime() + "," + accelerTemp.getSpeed() + "," + accelerTemp.getAcceleratedSpeed());
            sb.append(DataProcess.radToDge(gyroscopeTemp.getSideTilt()) + "," + DataProcess.radToDge(gyroscopeTemp.getVerticalTilt()) + "," + DataProcess.radToDge(gyroscopeTemp.getSteeringAngle()) + "\r\n");
        }
        writeFileToSD(fileName + ".csv", sb.toString());
    }

    /**
     * 生成CSV文件，并写入SD卡
     *
     * @param rowCache
     * @param timeStamp
     */
    public static void write(List<RowData> rowCache, long timeStamp) {
        DecimalFormat df = new DecimalFormat("0.00");
        StringBuilder sb = new StringBuilder();
        sb.append("时间,车速,纵向加速度,横向加速度,侧倾角度,纵倾角度,方位角\r\n");
        for (int i = 0; i < rowCache.size(); i++) {
            RowData dat = rowCache.get(i);
            sb.append(df.format(dat.getPassedTime()) + "," + df.format(dat.getSpeed()) + "," + df.format(dat.getVerticalAcc()) + ","+df.format(dat.getHorizontalAcc())+",");
            sb.append(df.format(dat.getSideTilt()) + "," + df.format(dat.getVerticalTilt()) + "," + df.format(dat.getSteeringAngle()) + "\r\n");
        }
        writeFileToSD(timeStamp + ".csv", sb.toString());
    }

    /**
     * 向SD卡写文件
     *
     * @param fileName
     * @param s
     */
    private static void writeFileToSD(String fileName, String s) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Log.d("TestFile", "SD card is not avaiable/writeable right now.");
            return;
        }

        try {
            File path = new File(pathName);
            File file = new File(pathName + fileName);
            if (!path.exists()) {
                path.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file, true);
            byte[] buf = s.getBytes();
            stream.write(buf);
            stream.close();

        } catch (Exception e) {
            Log.e("TestFile", "Error on writeFilToSD.");
            e.printStackTrace();
        }
    }

}
