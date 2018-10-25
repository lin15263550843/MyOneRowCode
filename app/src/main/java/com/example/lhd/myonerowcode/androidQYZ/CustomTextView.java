package com.example.lhd.myonerowcode.androidQYZ;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dhc on 2018/10/25.
 * <p>
 * Android 中的 LinearGradient 用来实现线性渐变效果
 * 此类是Shader的子类     通过paint.setShader来设置渐变。
 * 有两个构造方法分别如下：
 * 1、
 * LinearGradient(float x0, float y0, float x1, float y1, int colors[], float positions[], TileMode tile)
 * 注：Android中计算x,y坐标都是以屏幕左上角为原点，向右为x+，向下为y+
 * 第一个参数为线性起点的x坐标
 * 第二个参数为线性起点的y坐标
 * 第三个参数为线性终点的x坐标
 * 第四个参数为线性终点的y坐标
 * 第五个参数为实现渐变效果的颜色的组合
 * 第六个参数为前面的颜色组合中的各颜色在渐变中占据的位置（比重），如果为空，则表示上述颜色的集合在渐变中均匀出现
 * 第七个参数为渲染器平铺的模式，一共有三种
 * -CLAMP 边缘拉伸
 * -REPEAT 在水平和垂直两个方向上重复，相邻图像没有间隙
 * -MIRROR 以镜像的方式在水平和垂直两个方向上重复，相邻图像有间隙
 * 2、
 * public LinearGradient(float x0, float y0, float x1, float y1, int color0, int color1, TileMode tile)
 * 其他参数同上
 * int color0 表示渐变起始颜色
 * int color1 表示渐变终止颜色
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    Paint mPaint1;
    Paint mPaint2;
    Paint mPaint;
    int mViewWidth;
    LinearGradient mLinearGradient;
    Matrix mGradientMatrix;
    int mTranslate;

    // 在代码里面 new 的时候调用
    public CustomTextView(Context context) {
        super(context);
    }

    // 在布局 layout 中使用时调用
    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // 在布局 layout 中使用时调用，但是会有 style
    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //初始化 （画笔等）
        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);

        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint1);
        // 绘制内层矩形
        canvas.drawRect(10, 10, getMeasuredWidth() - 10, getMeasuredHeight() - 10, mPaint2);
        canvas.save();
        // 绘制文字前平移 10 像素
        canvas.translate(10, 0);
        // 在回调父类方法前，实现自己的逻辑，对  TextView 来说既是绘制文本内容之前
        super.onDraw(canvas);   // 父类完成的方法，即绘制文本
        // 在回调父类方法后，实现自己的逻辑，对  TextView 来说既是绘制文本内容之后
        if (mGradientMatrix != null) {
            mTranslate += mViewWidth / 5;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(10);  // 闪动间隔
        }
        canvas.restore();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(
                        0,
                        0,
                        mViewWidth,
                        0,
                        new int[]{Color.BLUE, 0xffffffff, Color.BLUE},
                        null,
                        Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }
}
