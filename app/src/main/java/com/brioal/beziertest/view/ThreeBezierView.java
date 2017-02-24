package com.brioal.beziertest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/24.
 */

public class ThreeBezierView extends View {
    //起点
    private int mStartX;
    private int mStartY;
    //第一个控制点
    private int mSecondX;
    private int mSecondY;
    //第二个控制点
    private int mThreeX;
    private int mThreeY;
    //终点
    private int mEndX;
    private int mEndY;

    private Paint mLinePaint;
    private Paint mPointPaint;
    private Path mPath;
    private boolean isThreePoint = false;

    public ThreeBezierView(Context context) {
        this(context, null);
    }

    public ThreeBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setDither(true);
        mLinePaint.setStyle(Paint.Style.STROKE.STROKE);
        mLinePaint.setStrokeWidth(10);
        mLinePaint.setColor(Color.RED);

        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setDither(true);
        mPointPaint.setStyle(Paint.Style.STROKE);
        mPointPaint.setStrokeWidth(10);
        mPointPaint.setColor(Color.BLACK);

        mPath = new Path();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                //判断靠近哪个点
                int secondIndex = (int) (Math.abs(event.getX() - mSecondX) + Math.abs(event.getY() - mSecondY));
                int threeIndex = (int) (Math.abs(event.getX() - mThreeX) + Math.abs(event.getY() - mThreeY));
                if (threeIndex < secondIndex) {
                    //第三个点
                    mThreeX = (int) event.getX();
                    mThreeY = (int) event.getY();
                } else {
                    //第二个点
                    mSecondX = (int) event.getX();
                    mSecondY = (int) event.getY();
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isThreePoint = !isThreePoint;
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mStartX = 5;
        mStartY = height / 2;
        mEndX = width - 5;
        mEndY = height / 2;
        mSecondX = width / 3;
        mSecondY = height / 2;
        mThreeX = (int) (width / 3 * 2.0f);
        mThreeY = height / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制起点终点
        canvas.drawCircle(mStartX, mStartY, 10, mPointPaint);
        canvas.drawCircle(mEndX, mEndY, 10, mPointPaint);
        //绘制两个控制点
        canvas.drawCircle(mSecondX, mSecondY, 10, mPointPaint);
        canvas.drawCircle(mThreeX, mThreeY, 10, mPointPaint);
        //绘制黑线
        canvas.drawLine(mStartX, mStartY, mSecondX, mSecondY, mPointPaint);
        canvas.drawLine(mSecondX, mSecondY, mThreeX, mThreeY, mPointPaint);
        canvas.drawLine(mThreeX, mThreeY, mEndX, mEndY, mPointPaint);
        //绘制曲线
        mPath.reset();
        mPath.moveTo(mStartX, mStartY);
        mPath.cubicTo(mSecondX, mSecondY, mThreeX, mThreeY, mEndX, mEndY);
        canvas.drawPath(mPath, mLinePaint);

    }
}
