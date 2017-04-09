package com.johnson.carstatus;

import android.content.Context;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.johnson.carstatus.model.SensorResult;
import com.johnson.carstatus.task.MySensorDevice;
import com.johnson.carstatus.task.StartBtnListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends AppCompatActivity {

    public TextView titleText, statusText, otherMsg, speedMsg;
    public Button controlBtn;

    public SensorManager mSensorManager;
    public MySensorDevice mySensorDevice;
    public LocationManager locationManager;

    private ImageView compassView;
    private LineChartView lineChartView,lineChartView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = (TextView) findViewById(R.id.title_text);
        statusText = (TextView) findViewById(R.id.status_text);
        otherMsg = (TextView) findViewById(R.id.other_msg);
        speedMsg = (TextView) findViewById(R.id.speed_msg);

        compassView=(ImageView) findViewById(R.id.compass_view);

        controlBtn = (Button) findViewById(R.id.control_btn);
        controlBtn.setClickable(false);

        this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mySensorDevice = new MySensorDevice(mSensorManager, this, locationManager);

        show("初始化", "...");
        checkTask();
    }

    /**
     * draw data on charts
     *
     * @param values
     * @param axisValuesForX
     */
    public void drawAccChart(List<PointValue> values, List<AxisValue> axisValuesForX) {
        lineChartView = (LineChartView) findViewById(R.id.chart);

        Line line = new Line(values)
                .setColor(Color.BLUE)
                .setCubic(false)
                .setHasPoints(true).setHasLabels(true);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        List<AxisValue> axisValuesForY = new ArrayList<>();
        AxisValue tempAxisValue;

        for (float i = -10.00f; i <= 10.00f; i += 0.50f) {
            tempAxisValue = new AxisValue(i);
            tempAxisValue.setLabel("" + i);
            axisValuesForY.add(tempAxisValue);
        }

        Axis xAxis = new Axis(axisValuesForX);
        Axis yAxis = new Axis(axisValuesForY);
        data.setAxisXBottom(xAxis);
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
    }

    /**
     * draw second charts
     *
     * @param values
     * @param axisValuesForX
     */
    public void drawAcc2Chart(List<PointValue> values, List<AxisValue> axisValuesForX) {
        lineChartView2 = (LineChartView) findViewById(R.id.chart2);

        Line line = new Line(values)
                .setColor(Color.RED)
                .setCubic(false)
                .setHasPoints(true).setHasLabels(true);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        List<AxisValue> axisValuesForY = new ArrayList<>();
        AxisValue tempAxisValue;

        for (float i = -10.00f; i <= 10.00f; i += 0.50f) {
            tempAxisValue = new AxisValue(i);
            tempAxisValue.setLabel("" + i);
            axisValuesForY.add(tempAxisValue);
        }

        Axis xAxis = new Axis(axisValuesForX);
        Axis yAxis = new Axis(axisValuesForY);
        data.setAxisXBottom(xAxis);
        data.setAxisYLeft(yAxis);

        lineChartView2.setLineChartData(data);
    }



    List<Float> verList =new ArrayList<Float>();
    List<Float> horList=new ArrayList<Float>();
    List<Float> timeList=new ArrayList<Float>();

    /**
     * push data to charts
     *
     * @param verticalAcc
     * @param horizontalAcc
     */
    public void pushToTableAcc(double verticalAcc, double horizontalAcc,double timeNow,double azimuth) {
        compassView.setRotation((float)azimuth);

        /**
         * process data for charts
         */
        if(listProcess(verticalAcc,horizontalAcc,timeNow)){
            DecimalFormat decimalFormat = new DecimalFormat("0.00");

            List<PointValue> values = new ArrayList<PointValue>();
            PointValue tempPointValue;
            for (int i = 0; i < 10; i ++) {
                tempPointValue = new PointValue(timeList.get(i),verList.get(i));
                tempPointValue.setLabel(decimalFormat
                        .format(verList.get(i)));
                values.add(tempPointValue);
            }

            List<PointValue> values2 = new ArrayList<PointValue>();
            for (int i = 0; i < 10; i ++) {
                tempPointValue = new PointValue(timeList.get(i),horList.get(i));
                tempPointValue.setLabel(decimalFormat
                        .format(horList.get(i)));
                values2.add(tempPointValue);
            }

            List<AxisValue> axisValuesForX = new ArrayList<>();
            AxisValue tempAxisValue;
            for (int i = 0; i <10; i ++) {
                tempAxisValue = new AxisValue(i);
                tempAxisValue.setLabel(""+decimalFormat.format(timeList.get(i)));
                axisValuesForX.add(tempAxisValue);
            }

            drawAccChart(values,axisValuesForX);
            drawAcc2Chart(values2,axisValuesForX);
        }
    }

    /**
     * process data for charts
     *
     * @param vdata
     * @param timeNow
     * @return
     */
    private boolean listProcess(double vdata,double hdata,double timeNow){
        verList.add((float)vdata);
        horList.add((float)hdata);
        timeList.add((float)timeNow);

        if(verList.size()>10){
            verList.remove(0);
            horList.remove(0);
            timeList.remove(0);
            return true;
        }
        return false;
    }

    public void show(String title, String content, String other) {
        this.show(title, content);
        CharSequence charSequence = Html.fromHtml(other);
        otherMsg.setText(charSequence);
    }

    public void show(String title, String content) {
        CharSequence charSequence = Html.fromHtml("<big>" + title + "</big>");
        titleText.setText(charSequence);
        this.show(content);
    }

    public void show(String content) {
        CharSequence charSequence = Html.fromHtml(content);
        statusText.setText(charSequence);
    }

    public void showSpeed(String content) {
        speedMsg.setText(content);
    }

    void checkTask() {
        int checkRes = 0;
        SensorResult result = mySensorDevice.sensorCheck();
        String status = "";
        if (result.isAcceleroneterExist()) {
            status += "<font color='#00CC99'>存在</font>";
            checkRes++;
        } else {
            status += "<font color='red'>不存在</font>";
        }
        status += "-加速度传感器<br>";

        if (result.isGyroscopeExist()) {
            status += "<font color='#00CC99'>存在</font>";
            checkRes++;
        } else {
            status += "<font color='red'>不存在</font>";
        }
        status += "-陀螺仪<br>";

        if (result.isOrientationExist()) {
            status += "<font color='#00CC99'>存在</font>";
            checkRes++;
        } else {
            status += "<font color='red'>不存在</font>";
        }
        status += "-方向传感器<br>";

        if (checkRes == 3) {
            status += "----------<br>检查成功，请固定好您的手机，然后点击开始";
            controlBtn.setClickable(true);
            controlBtn.setOnClickListener(new StartBtnListener(this));
        } else {
            status += "----------<br>您的手机缺少必要的传感器!";
        }
        show("检查传感器", status);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
