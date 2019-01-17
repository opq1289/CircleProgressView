package com.wj.demo;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.wj.circleprogressview.CircleProgressView;
import com.wj.circleprogressview.CommonUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CircleProgressView view = findViewById(R.id.view);
        final EditText progressEt = findViewById(R.id.progress_et);
        final EditText colorEt = findViewById(R.id.color_et);
        final EditText widthEt = findViewById(R.id.width_et);
        final EditText backgroundEt = findViewById(R.id.background_color_et);
        final EditText angleEt = findViewById(R.id.angle_et);
        final EditText durationEt = findViewById(R.id.duration_et);
        final EditText endEt = findViewById(R.id.end_et);
        TextView tv = findViewById(R.id.tv);
        final TextView progressTv = findViewById(R.id.progress_tv);
        final CheckBox cb = findViewById(R.id.cb);
        final CheckBox typeCb = findViewById(R.id.type_cb);
        final CheckBox capCb = findViewById(R.id.cap_cb);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(angleEt.getText().toString())) {
                    view.setStartAngle(Integer.valueOf(angleEt.getText().toString()));
                }
                if (!TextUtils.isEmpty(colorEt.getText().toString())) {
                    view.setProgressColor(Color.parseColor(colorEt.getText().toString()));
                }
                if (!TextUtils.isEmpty(backgroundEt.getText().toString())) {
                    view.setBackgroundCircleColor(Color.parseColor(backgroundEt.getText().toString()));
                }
                if (!TextUtils.isEmpty(widthEt.getText().toString())) {
                    view.setProgressWidth(CommonUtil.dp2px(MainActivity.this, Integer.valueOf(widthEt.getText().toString())));
                }
                if (!TextUtils.isEmpty(durationEt.getText().toString())) {
                    view.setDuration(Integer.valueOf(durationEt.getText().toString()));
                }
                if (!TextUtils.isEmpty(endEt.getText().toString())) {
                    view.setEndAngle(Integer.valueOf(endEt.getText().toString()));
                }
                view.setProgressType(typeCb.isChecked() ? CircleProgressView.TYPE_CLIP : CircleProgressView.TYPE_CIRCLE);
                view.setCap(capCb.isChecked() ? Paint.Cap.ROUND : Paint.Cap.BUTT);
                if (!TextUtils.isEmpty(progressEt.getText().toString())) {
                    view.setProgress(Integer.parseInt(progressEt.getText().toString()), cb.isChecked());
                }
            }
        });
        view.setOnProgressChangedListener(new CircleProgressView.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(float currentProgress) {
                progressTv.setText((int) (currentProgress) + "%");
            }
        });
    }
}
