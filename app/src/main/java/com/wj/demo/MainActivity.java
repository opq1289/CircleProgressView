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
        final CircleProgressView progressView = findViewById(R.id.view);
        final EditText progressEt = findViewById(R.id.progress_et);
        final EditText colorEt = findViewById(R.id.color_et);
        final EditText widthEt = findViewById(R.id.width_et);
        final EditText backgroundEt = findViewById(R.id.background_color_et);
        final EditText angleEt = findViewById(R.id.angle_et);
        final EditText durationEt = findViewById(R.id.duration_et);
        final EditText endEt = findViewById(R.id.end_et);
        TextView tv = findViewById(R.id.tv);
        final TextView progressTv = findViewById(R.id.progress_tv);
        final CheckBox animCb = findViewById(R.id.anim_cb);
        final CheckBox typeCb = findViewById(R.id.type_cb);
        final CheckBox capCb = findViewById(R.id.cap_cb);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(angleEt.getText().toString())) {
                    progressView.setStartAngle(Integer.valueOf(angleEt.getText().toString()));
                }
                if (!TextUtils.isEmpty(colorEt.getText().toString())) {
                    progressView.setProgressColor(Color.parseColor(colorEt.getText().toString()));
                }
                if (!TextUtils.isEmpty(backgroundEt.getText().toString())) {
                    progressView.setBackgroundCircleColor(Color.parseColor(backgroundEt.getText().toString()));
                }
                if (!TextUtils.isEmpty(widthEt.getText().toString())) {
                    progressView.setProgressWidth(CommonUtil.dp2px(MainActivity.this, Integer.valueOf(widthEt.getText().toString())));
                }
                if (!TextUtils.isEmpty(durationEt.getText().toString())) {
                    progressView.setDuration(Integer.valueOf(durationEt.getText().toString()));
                }
                if (!TextUtils.isEmpty(endEt.getText().toString())) {
                    progressView.setEndAngle(Integer.valueOf(endEt.getText().toString()));
                }
                progressView.setProgressType(typeCb.isChecked() ? CircleProgressView.TYPE_CLIP : CircleProgressView.TYPE_CIRCLE);
                progressView.setCap(capCb.isChecked() ? Paint.Cap.ROUND : Paint.Cap.BUTT);
                if (!TextUtils.isEmpty(progressEt.getText().toString())) {
                    progressView.setProgress(Integer.parseInt(progressEt.getText().toString()), animCb.isChecked());
                    progressTv.setVisibility(animCb.isChecked() ? View.VISIBLE : View.GONE);
                }
            }
        });
        progressView.setOnProgressChangedListener(new CircleProgressView.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(float currentProgress) {
                progressTv.setText((int) (currentProgress) + "%");
            }
        });
    }
}
