package com.example.lhd.myonerowcode.androidQYZ;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义 圆环
 * Created by dhc on 2018/10/30.
 */

public class CustomRing extends View {

    private static final String TAG = "CustomRing";
    int mCircleXY;
    int mRadius;
    RectF mArcRectF;
    private Paint mArcPaint;
    private Paint mCirclePaint;
    private Paint mTextPaint;
    private String mText = "真垃圾，不详细";
    private int mTextSize = 80;

    public CustomRing(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomRing(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomRing(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRing(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int length = getMeasuredWidth();    //获取屏幕宽度
        Log.d(TAG, "onMeasure: 屏幕宽度：" + length);
        mCircleXY = length / 2;
        mRadius = (int) (length * 0.5 / 2);
        mArcRectF = new RectF(
                (float) (length * 0.1),
                (float) (length * 0.1),
                (float) (length * 0.9),
                (float) (length * 0.9));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(Color.RED);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(180);

        // 实例化画笔对象
        mCirclePaint = new Paint();
        // 给画笔设置颜色
        mCirclePaint.setColor(Color.RED);
        // 设置画笔属性
        mCirclePaint.setStyle(Paint.Style.FILL);  //  画笔属性是实心圆
//        mCirclePaint.setStyle(Paint.Style.STROKE);  //  画笔属性是空心圆
//        mCirclePaint.setStrokeWidth(8); //  设置画笔粗细

        // 绘制圆 四个参数：参数一：圆心的x坐标        参数二：圆心的y坐标        参数三：圆的半径        参数四：定义好的画笔
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);
        // 绘制弧线
        canvas.drawArc(mArcRectF, 0, 270, false, mArcPaint);
        // 绘制文字
        canvas.drawText(mText, 0, mText.length(), mCircleXY, mCircleXY + (mTextSize / 4), mTextPaint);
    }
}
