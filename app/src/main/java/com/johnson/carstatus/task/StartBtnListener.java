package com.johnson.carstatus.task;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.johnson.carstatus.MainActivity;
import com.johnson.carstatus.R;

/**
 * Created by johnson on 2017/3/11.
 */
public class StartBtnListener implements View.OnClickListener {
    MainActivity mainActivity;

    public StartBtnListener(MainActivity mainActivity) {
        super();
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View view) {
        String btnText = String.valueOf(mainActivity.controlBtn.getText());
        if (btnText.equals(mainActivity.getString(R.string.start_btn))) {
            /**
             * 开始
             */
            new AlertDialog.Builder(mainActivity).setTitle("系统提示")
                    .setMessage("请垂直固定好手机。\r\n\r\n点击确定后，系统会参照当前姿态计算数据。")
                    .setPositiveButton("我以固定好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mainActivity.mySensorDevice.start();
                            mainActivity.controlBtn.setText(mainActivity.getString(R.string.end_btn));
                        }
                    }).show();
        } else if (btnText.equals(mainActivity.getString(R.string.end_btn))) {
            /**
             * 结束
             */
            mainActivity.mySensorDevice.stop();
            mainActivity.controlBtn.setText(mainActivity.getString(R.string.start_btn));
        }
    }
}
