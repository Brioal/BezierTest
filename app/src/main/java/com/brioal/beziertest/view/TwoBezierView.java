package com.brioal.beziertest.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/24.
 */

public class TwoBezierView extends View {
    private int mStartX;
    private int mStartY;
    private int mControlX;
    private int mControlY;
    private int mEndX;
    private int mEndY;
    private Paint mLinePaint;
    private Paint mPointPaint;
    private Path mPath;

    public TwoBezierView(Context context) {
        this(context, null);
    }

    public TwoBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setDither(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(10);
        mLinePaint.setColor(Color.RED);

        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setDither(true);
        mPointPaint.setColor(Color.BLACK);
        mPointPaint.setStrokeWidth(10);
        mPointPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mStartX = 5;
        mEndX = width - 5;
        mStartY = height / 2;
        mEndY = height / 2;
        mControlX = width / 2;
        mControlY = height / 2;
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                mControlX = (int) event.getX();
                mControlY = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                final ValueAnimator animatorX = ValueAnimator.ofInt(mControlX, getWidth() / 2);
                animatorX.setDuration(500);
                animatorX.setInterpolator(new OvershootInterpolator());
                animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int x = (int) animation.getAnimatedValue();
                        mControlX = x;
                        invalidate();
                    }
                });
                animatorX.start();
                final ValueAnimator animatorY = ValueAnimator.ofInt(mControlY, getHeight() / 2);
                animatorY.setDuration(500);
                animatorY.setInterpolator(new OvershootInterpolator());
                animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int y = (int) animation.getAnimatedValue();
                        mControlY = y;
                        invalidate();
                    }
                });
                animatorY.start();

        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制起点终点
        canvas.drawCircle(mStartX, mStartY, 8, mPointPaint);
        canvas.drawCircle(mEndX, mEndY, 8, mPointPaint);

        //绘制控制点
        canvas.drawCircle(mControlX, mControlY, 8, mPointPaint);

        //绘制连接线
        canvas.drawLine(mStartX, mStartY, mControlX, mControlY, mPointPaint);
        canvas.drawLine(mControlX, mControlY, mEndX, mEndY, mPointPaint);

        //绘制曲线
        mPath.reset();
        mPath.moveTo(mStartX, mStartY);
        mPath.quadTo(mControlX, mControlY, mEndX, mEndY);
        canvas.drawPath(mPath, mLinePaint);
    }
}
