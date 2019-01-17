package com.wj.circleprogressview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author wj on 2019/1/16.
 */
public class CircleProgressView extends View {

    /**
     * 整圆进度条
     */
    public static final int TYPE_CIRCLE = 0;
    /**
     * 切割进度条
     */
    public static final int TYPE_CLIP = 1;
    /**
     * 进度条类型
     */
    private int mProgressType;
    /**
     * 进度条动画持续时间
     */
    private int mDuration;
    /**
     * 是否显示进度条动画
     */
    private boolean mShowAnimation;
    /**
     * 进度条颜色
     */
    private int mProgressColor;
    /**
     * 当前进度
     */
    private float mProgress;
    /**
     * 进度条宽度
     */
    private int mProgressWidth;
    /**
     * 进度条起始角度
     */
    private int mStartAngle;
    /**
     * 进度条终止角度
     */
    private int mEndAngle;
    /**
     * 进度条背景颜色
     */
    private int mBackgroundColor;
    private int mViewWidth;
    private int mDefaultWidth = CommonUtil.dp2px(getContext(), 10);
    private RectF mRectf;
    private OnProgressChangedListener mListener;
    private ValueAnimator mValueAnimator;
    private Paint mProgressPaint;
    private Paint mBackgroundPaint;
    private float mTotalProgress;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleProgressView, defStyleAttr, 0);
        mProgressWidth = (int) typedArray.getDimension(R.styleable.CircleProgressView_progressWidth, mDefaultWidth);
        mProgressColor = (int) typedArray.getDimension(R.styleable.CircleProgressView_progressColor, ContextCompat.getColor(getContext(), R.color.colorAccent));
        mStartAngle = typedArray.getInt(R.styleable.CircleProgressView_startAngle, 0);
        mEndAngle = typedArray.getInt(R.styleable.CircleProgressView_startAngle, 360);
        mBackgroundColor = (int) typedArray.getDimension(R.styleable.CircleProgressView_backgroundColor, ContextCompat.getColor(getContext(), R.color.grey_f1));
        mShowAnimation = typedArray.getBoolean(R.styleable.CircleProgressView_animation, false);
        mDuration = typedArray.getInt(R.styleable.CircleProgressView_duration, 1000);
        typedArray.recycle();

        mProgressPaint.setStrokeWidth(mProgressWidth);
        mProgressPaint.setColor(mProgressColor);

        mBackgroundPaint.setStrokeWidth(mProgressWidth);
        mBackgroundPaint.setColor(mBackgroundColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        mViewWidth = Math.min(width, height);
        setMeasuredDimension(mViewWidth, mViewWidth);
    }

    private int measureWidth(int widthMeasureSpec) {
        int width;
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                width = size < mProgressWidth ? mProgressWidth : size;
                break;
            case MeasureSpec.AT_MOST:
                width = mDefaultWidth * 2;
                break;
            default:
                width = CommonUtil.getScreenWidthInPx(getContext());
                break;
        }
        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int height;
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                height = size < mProgressWidth ? mProgressWidth : size;
                break;
            case MeasureSpec.AT_MOST:
                height = mDefaultWidth * 2;
                break;
            default:
                height = CommonUtil.getScreenHeightInPx(getContext());
                break;
        }
        return height;
    }

    private void initPaint() {
        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectf = new RectF(mProgressWidth / 2, mProgressWidth / 2, mViewWidth - mProgressWidth / 2, mViewWidth - mProgressWidth / 2);
        if (mProgressType == TYPE_CIRCLE) {
            canvas.drawCircle(mViewWidth / 2, mViewWidth / 2, mViewWidth / 2 - mProgressWidth / 2, mBackgroundPaint);
            canvas.drawArc(mRectf, mStartAngle, mProgress * 360 / 100, false, mProgressPaint);
        } else if (mProgressType == TYPE_CLIP) {
            canvas.drawArc(mRectf, mStartAngle, mEndAngle - mStartAngle, false, mBackgroundPaint);
            canvas.drawArc(mRectf, mStartAngle, mProgress * 360 / 100, false, mProgressPaint);
        }
    }

    /**
     * 设置进度条颜色
     */
    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
        mProgressPaint.setColor(mProgressColor);
    }

    /**
     * @param progress      进度
     * @param showAnimation 是否展示动画
     */
    public void setProgress(float progress, boolean showAnimation) {
        mShowAnimation = showAnimation;
        if (mProgressType == TYPE_CLIP) {
            progress = (int) ((mEndAngle - mStartAngle) * 100 / 360.0f);
            mTotalProgress = progress;
        } else {
            mTotalProgress = 100;
        }
        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            mValueAnimator.cancel();
        }
        if (mShowAnimation) {
            mValueAnimator = ValueAnimator.ofFloat(progress);
            mValueAnimator.setDuration(mDuration);
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mProgress = (float) animation.getAnimatedValue();
                    if (mListener != null) {
                        mListener.onProgressChanged(mProgress * 100 / mTotalProgress);
                    }
                    invalidate();
                }
            });
            mValueAnimator.start();
        } else {
            mProgress = progress;
            invalidate();
        }
    }

    /**
     * 设置动画持续时间
     */
    public void setDuration(int duration) {
        mDuration = duration;
    }

    /**
     * 设置进度进度条宽度
     */
    public void setProgressWidth(int progressWidth) {
        mProgressWidth = progressWidth;
        mBackgroundPaint.setStrokeWidth(progressWidth);
        mProgressPaint.setStrokeWidth(progressWidth);
    }

    /**
     * 设置进度起始角度
     */
    public void setStartAngle(int startAngle) {
        mStartAngle = startAngle;
    }

    /**
     * 设置进度条类型：{@link CircleProgressView#TYPE_CIRCLE}、{@link CircleProgressView#TYPE_CLIP}
     */
    public void setProgressType(int progressType) {
        mProgressType = progressType;
    }

    /**
     * 设置切割圆结束角度
     */
    public void setEndAngle(int endAngle) {
        mEndAngle = endAngle;
    }

    /**
     * 进度条开始、结束形状
     */
    public void setCap(Paint.Cap cap) {
        mProgressPaint.setStrokeCap(cap);
        mBackgroundPaint.setStrokeCap(cap);
    }

    /**
     * 设置背景圆颜色
     */
    public void setBackgroundCircleColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
        mBackgroundPaint.setColor(mBackgroundColor);
        invalidate();
    }

    /**
     * 设置进度监听
     */
    public void setOnProgressChangedListener(OnProgressChangedListener listener) {
        mListener = listener;
    }

    public interface OnProgressChangedListener {
        void onProgressChanged(float currentProgress);
    }
}
