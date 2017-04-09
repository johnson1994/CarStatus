package com.johnson.carstatus.model;

import android.text.Html;

import com.johnson.carstatus.MainActivity;
import com.johnson.carstatus.task.DataProcess;
import com.johnson.carstatus.task.Output;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnson on 2017/3/19.
 */
public class DataCache {
    /* 用来区分不同文件名 */
    long timeStamp;
    /* 主界面 */
    MainActivity mainActivity;

    List<RowData> rowCache;
    int max=1000;

    private DataCache() {
        rowCache = new ArrayList<RowData>(max);
    }

    private static class DataCacheHolder {
        private static final DataCache INSTANCE = new DataCache();
    }

    /**
     * get instance
     *
     * @return
     */
    public static DataCache getInstance() {
        return DataCacheHolder.INSTANCE;
    }

    /**
     * add one row into list
     *
     * @param oneRow
     */
    public void add(RowData oneRow) {
        transferUnit(oneRow);
        rowCache.add(oneRow);
        show(oneRow);

        if(rowCache.size()>=max){
            write();
        }
    }

    /**
     * 单位转换
     *
     * @param oneRow
     */
    void transferUnit(RowData oneRow){
        oneRow.setSpeed(DataProcess.speedUnitTransfer(oneRow.getSpeed()));

//        oneRow.setSideTilt(DataProcess.radToDge(oneRow.getSideTilt()));
//        oneRow.setVerticalTilt(DataProcess.radToDge(oneRow.getVerticalTilt()));
//        oneRow.setSteeringAngle(DataProcess.radToDge(oneRow.getSteeringAngle()));
    }


    /**
     * 写入SD卡
     */
    public void write(){
        Output.write(rowCache,timeStamp);
        rowCache.clear();
    }

    /**
     * show status on the main activity
     *
     * @param oneRow
     */
    void show(RowData oneRow){
        DecimalFormat df = new DecimalFormat("0.00");

        StringBuilder sb=new StringBuilder();
        sb.append("时间:"+df.format(oneRow.getPassedTime())+"<br>");
        sb.append("速度:"+df.format(oneRow.getSpeed())+"<br>");
        sb.append("纵向加速度:"+df.format(oneRow.verticalAcc)+"<br>");
        sb.append("横向加速度:"+df.format(oneRow.horizontalAcc)+"<br>");
        sb.append("侧倾:"+df.format(oneRow.getSideTilt())+"<br>");
        sb.append("纵倾:"+df.format(oneRow.getVerticalTilt())+"<br>");
        sb.append("方位角:"+df.format(oneRow.getSteeringAngle())+"<br>");

        mainActivity.show("状态参数",sb.toString());
        mainActivity.pushToTableAcc(oneRow.getVerticalAcc(),oneRow.getHorizontalAcc(),oneRow.getPassedTime(),oneRow.getSteeringAngle());
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<RowData> getRowCache() {
        return rowCache;
    }

    public void setRowCache(List<RowData> rowCache) {
        this.rowCache = rowCache;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
