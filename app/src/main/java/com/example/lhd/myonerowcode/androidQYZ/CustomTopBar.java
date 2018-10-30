package com.example.lhd.myonerowcode.androidQYZ;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lhd.myonerowcode.R;

import static android.content.ContentValues.TAG;

/**
 * Created by dhc on 2018/10/26.
 * <p>
 * 自定义 CustomTopBar
 */

public class CustomTopBar extends RelativeLayout {

    private int mLeftTextColor;
    private Drawable mLeftBackground;
    private String mLeftText;
    private int mRightTextColor;
    private Drawable mRightBackground;
    private String mRightText;
    private int mRightTextPadding;
    private float mTitleSize;
    private int mTitleTextColor;
    private String mTitleText;
    private Button mLeftButton;
    private Button mRightButton;
    private TextView mTitleView;

    private LayoutParams mLeftParams;
    private LayoutParams mRightParams;
    private LayoutParams mTitleParams;
    private TopBarClickListener mTopBarClickListener;

//    public CustomTopBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    public CustomTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 通过这个方法，将在 attrs.xml 中定义 declare-styleable 的所有属性的值存储到 TypeArray 中
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomTopBar);
        // 从 TypeArray 中取出对应的值来为要设置的属性赋值
        mLeftTextColor = ta.getColor(R.styleable.CustomTopBar_leftTextColor, 0);
        mLeftBackground = ta.getDrawable(R.styleable.CustomTopBar_leftBackground);
        mLeftText = ta.getString(R.styleable.CustomTopBar_leftText);
        mRightTextColor = ta.getColor(R.styleable.CustomTopBar_rightTextColor, 0);
        mRightBackground = ta.getDrawable(R.styleable.CustomTopBar_rightBackground);
        mRightText = ta.getString(R.styleable.CustomTopBar_rightText);
        mRightTextPadding = (int) ta.getDimension(R.styleable.CustomTopBar_rightTextPadding, 0);
        mTitleSize = ta.getDimension(R.styleable.CustomTopBar_titleTextSize, 10);
        mTitleTextColor = ta.getColor(R.styleable.CustomTopBar_titleTextColor, 0);
        mTitleText = ta.getString(R.styleable.CustomTopBar_title);
        // 获取完 Type的Array 的值后，一般要调用 recycle() 方法来避免重新创建的时候的错误
        ta.recycle();   // 回收资源
        mLeftButton = new Button(context);
        mRightButton = new Button(context);
        mTitleView = new TextView(context);
        // 为创建的组件元素赋值   值就来源于我们在引用的 xml 文件中给对应属性的赋值
        mLeftButton.setTextColor(mLeftTextColor);
        mLeftButton.setBackground(mLeftBackground);
//        mLeftBackground = getResources().getDrawable(R.drawable.shouye_off);
//        mLeftBackground.setBounds(10, 10, 20, 20);
//        mLeftButton.setCompoundDrawables(null, mLeftBackground, null, null);
        mLeftButton.setText(mLeftText);
        mRightButton.setTextColor(mRightTextColor);
        mRightButton.setBackground(mRightBackground);
        mRightButton.setText(mRightText);
//        mRightButton.setPadding(mRightTextPadding, mRightTextPadding, mRightTextPadding, mRightTextPadding);
        mTitleView.setText(mTitleText);
        mTitleView.setTextColor(mTitleTextColor);
        mTitleView.setTextSize(mTitleSize);
        mTitleView.setGravity(Gravity.CENTER);

        // 为组件元素设置相应的布局元素
        mLeftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        mLeftParams = new LayoutParams(80, 80);
        mLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
//        mLeftParams.setMargins(10, 30, 10, 30);
        // 添加到 ViewGroup
        addView(mLeftButton, mLeftParams);

        mRightParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(mRightButton, mRightParams);
        mTitleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mTitleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(mTitleView, mTitleParams);

        // 按钮的点击事件，不需要具体的实现，只需要调用接口的方法，回调的时候，会有具体的实现
        mLeftButton.setOnClickListener(v -> mTopBarClickListener.leftClick(v));
        mRightButton.setOnClickListener(v -> mTopBarClickListener.rightClick(v));
        Log.d(TAG, "CustomTopBar:=== " + this);

    }

    public CustomTopBar(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 暴露一个方法给调用者来注册接口回调
     * 通过解耦来获得回调者对接口方法的实现
     *
     * @param mListener
     */
    public void setOnTopBarClickListener(TopBarClickListener mListener) {
        this.mTopBarClickListener = mListener;
    }

    /**
     * 设置按钮的显示与否通过 id 区分按钮，flag 区分是否显示
     * 是否显示按钮
     *
     * @param id   id
     * @param flag 是否显示
     */
    public void setButtonVisible(int id, boolean flag) {
        if (flag) {
            if (id == 0) {
                mLeftButton.setVisibility(View.VISIBLE);
            } else {
                mRightButton.setVisibility(View.VISIBLE);
            }
        } else {
            if (id == 0) {
                mLeftButton.setVisibility(View.GONE);
            } else {
                mRightButton.setVisibility(View.GONE);
            }
        }
    }
}
