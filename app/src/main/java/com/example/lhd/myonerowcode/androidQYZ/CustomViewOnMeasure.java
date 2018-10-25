package com.example.lhd.myonerowcode.androidQYZ;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * View 的测量
 * View 的绘制
 * 在自定义 View 时，我们通常会去重写 onDraw() 方法来绘制 View 的显示内容。如果该 View 还需要使用 wrap_content 属性，那么还必须重写 onMeasure() 方法。
 * 另外，通过自定义 attrs 属性，还可以设置新的属性配置值。
 * 在 View 中通常有以下一些比较重要的回调方法。
 *      onFinishInflate (）：从 XML 加载组件后回调。
 *      onSizeChanged (）；组件大小改变时回调。
 *      onMeasure (）：回调该方法来进行测量。
 *      onLayout (）：回调该方法来确定显示的位置。
 *      onTouchEvent (）：监听到触摸事件时回调。
 *      当然，创建自定义 View 的时候，并不需要重写所有的方法，只需要重写特定条件的回调方法即可。这也是 Android 控件架构灵活性的体现。
 * 通常情况下，有以下三种方法来实现自定义的控件。
 *      对现有控件进行拓展
 *      通过组合来实现新的控件
 *      重写 View 来实现全新的控件

 * Created by dhc on 2018/10/24.
 */

public class CustomViewOnMeasure extends View {

    private static final String TAG = "CustomViewOnMeasure";

//    public CustomViewOnMeasure(Context context) {
//        super(context);
//    }

    public CustomViewOnMeasure(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 在回调父类方法前，实现自己的逻辑，对  TextView 来说既是绘制文本内容之前
        super.onDraw(canvas);
        // 在回调父类方法后，实现自己的逻辑，对  TextView 来说既是绘制文本内容之后
        Log.d(TAG, "onDraw: canvas 绘制");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ~~~~~~~~~~~~~~~~~~~~~~~~~~");
        setMeasuredDimension(
                measuredWidth(widthMeasureSpec), measuredHeight(heightMeasureSpec)
        );
    }

    /**
     * 自定义高度
     *
     * @param heightMeasureSpec
     * @return
     */
    private int measuredHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 自定义宽度
     * <p>
     * 测量的模式可以为以下三种:
     * EXACTLY ：精确值模式，当我们将控件的 layout_width 或者 layout_height 属性指定为具体数值时，或者指定为  match_parent（占据父 View 的大小） 时，系统使用的是 EXACTLY 模式。
     * <p>
     * android:layout_height="wrap_content"
     * AT_MOST ：最大值模式，当我们将控件的 layout_width 或者 layout_height 属性指定为 wrap_content 时，控件的大小一般随着控件的子控件活内容的变化而变化，此时控件的尺寸只要不超过父控件允许的最大尺寸即可。
     * UNSPECIFIED ：不指定其大小测量模式，View 想多大就多大，一般自定义 View 时才会用。
     *
     * @param widthMeasureSpec
     * @return
     */
    private int measuredWidth(int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
}
