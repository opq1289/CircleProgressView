### CircleProgressView

这是一个圆形的进度条，可以自由地修改进度条大小，颜色，开启动画，设置进度条形状等。

效果图如下：

无动画：

![noanim_union](https://github.com/opq1289/CircleProgressView/blob/master/images/noanim_union.png)

有动画：

整圆：

![progress_anim](https://github.com/opq1289/CircleProgressView/blob/master/images/progress_anim.gif)

切割圆：

![clip_anim](https://github.com/opq1289/CircleProgressView/blob/master/images/clip_anim.gif)

##### 引入方式：

`compile 'com.wangjun1289:circleprogressview:1.0.0'`

##### 使用方式：

```
CircleProgressView progressView = findViewById(R.id.view);
//起始角度
progressView.setStartAngle(90);
//进度条颜色
progressView.setProgressColor(Color.parseColor("#d81b60"));
//背景圆颜色
progressView.setBackgroundCircleColor(Color.parseColor("#f1f1f1"));
//进度条宽度
progressView.setProgressWidth(CommonUtil.dp2px(this, 10));
//动画持续时间
progressView.setDuration(1000);
//切割圆的终止角度
progressView.setEndAngle(300);
//CircleProgressView.TYPE_CLIP 切割圆
//CircleProgressView.TYPE_CIRCLE 整圆
progressView.setProgressType(CircleProgressView.TYPE_CIRCLE);
//设置进度条起始、终止形状   Paint.Cap.ROUND：圆形   Paint.Cap.BUTT：普通平面
progressView.setCap(capCb.isChecked() ? Paint.Cap.ROUND : Paint.Cap.BUTT);
//设置当前进度，开启动画
progressView.setProgress(60, true);
```

##### 详细参数：

| 描述                     |      属性       | 方法                                                         |
| :----------------------- | :-------------: | ------------------------------------------------------------ |
| 设置进度条宽度           |  progressWidth  | setProgressWidth(int progressWidth)                          |
| 设置进度条颜色           |  progressColor  | setProgressColor(int progressColor)                          |
| 设置背景圆颜色           | backgroundColor | setBackgroundCircleColor(int backgroundColor)                |
| 设置起始角度             |   startAngle    | setStartAngle(int startAngle)                                |
| 设置切割圆的终止角度     |    endAngle     | setEndAngle(int endAngle)                                    |
| 设置是否开启动画         |    animation    | 无                                                           |
| 动画持续时间             |    duration     | setDuration(int duration)                                    |
| 设置当前进度             |       无        | setProgress(float progress, boolean showAnimation)           |
| 设置开启动画时的进度监听 |       无        | setOnProgressChangedListener(OnProgressChangedListener listener) |
| 设置进度条起始、终止形状 |       无        | setCap(Paint.Cap cap)                                        |

博客链接：[https://juejin.im/post/5c4183c851882525525168c0](https://juejin.im/post/5c4183c851882525525168c0)

