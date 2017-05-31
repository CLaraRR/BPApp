package com.example.bpapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bpapp.bpapp.R;

/**
 * Created by 宁润 on 2017/5/31.
 */
public class CustomToolBar extends LinearLayout {
    private Boolean isLeftBtnVisible;
    private int leftResId;

    private Boolean isLeftTvVisible;
    private String leftTvText;

    private Boolean isRightBtnVisible;
    private int rightResId;

    private Boolean isRightTvVisible;
    private String rightTvText;

    private Boolean isRightBtnVisible2;
    private int rightResId2;

    private Boolean isRightTvVisible2;
    private String rightTvText2;

    private Boolean isTitleVisible;
    private String titleText;

    private int backgroundResId;

    public CustomToolBar(Context context) {
        this(context, null);
    }

    public CustomToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    /**
     * 初始化属性
     * @param attrs
     */
    public void initView(AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomToolBar);
        /**-------------获取左边按钮属性------------*/
        isLeftBtnVisible = typedArray.getBoolean(R.styleable.CustomToolBar_left_btn_visible, false);
        leftResId = typedArray.getResourceId(R.styleable.CustomToolBar_left_btn_src, -1);
        /**-------------获取左边文本属性------------*/
        isLeftTvVisible = typedArray.getBoolean(R.styleable.CustomToolBar_left_tv_visible, false);
        if (typedArray.hasValue(R.styleable.CustomToolBar_left_tv_text)) {
            leftTvText = typedArray.getString(R.styleable.CustomToolBar_left_tv_text);
        }
        /**-------------获取右边按钮属性------------*/
        isRightBtnVisible = typedArray.getBoolean(R.styleable.CustomToolBar_right_btn_visible, false);
        rightResId = typedArray.getResourceId(R.styleable.CustomToolBar_right_btn_src, -1);
        /**-------------获取右边文本属性------------*/
        isRightTvVisible = typedArray.getBoolean(R.styleable.CustomToolBar_right_tv_visible, false);
        if (typedArray.hasValue(R.styleable.CustomToolBar_right_tv_text)) {
            rightTvText = typedArray.getString(R.styleable.CustomToolBar_right_tv_text);
        }

        /**-------------获取右边按钮2属性------------*/
        isRightBtnVisible2 = typedArray.getBoolean(R.styleable.CustomToolBar_right_btn_visible2, false);
        rightResId2 = typedArray.getResourceId(R.styleable.CustomToolBar_right_btn_src2, -1);
        /**-------------获取右边文本2属性------------*/
        isRightTvVisible2 = typedArray.getBoolean(R.styleable.CustomToolBar_right_tv_visible2, false);
        if (typedArray.hasValue(R.styleable.CustomToolBar_right_tv_text2)) {
            rightTvText2 = typedArray.getString(R.styleable.CustomToolBar_right_tv_text2);
        }
        /**-------------获取标题属性------------*/
        isTitleVisible = typedArray.getBoolean(R.styleable.CustomToolBar_title_visible, false);
        if (typedArray.hasValue(R.styleable.CustomToolBar_title_text)) {
            titleText = typedArray.getString(R.styleable.CustomToolBar_title_text);
        }
        /**-------------背景颜色------------*/
        backgroundResId = typedArray.getResourceId(R.styleable.CustomToolBar_barBackground, -1);

        typedArray.recycle();

        /**-------------设置内容------------*/
        View barLayoutView = View.inflate(getContext(), R.layout.layout_common_toolbar, null);
        Button leftBtn = (Button) barLayoutView.findViewById(R.id.toolbar_left_btn);
        TextView leftTv = (TextView) barLayoutView.findViewById(R.id.toolbar_left_tv);
        TextView titleTv = (TextView) barLayoutView.findViewById(R.id.toolbar_title_tv);
        Button rightBtn = (Button) barLayoutView.findViewById(R.id.toolbar_right_btn);
        TextView rightTv = (TextView) barLayoutView.findViewById(R.id.toolbar_right_tv);
        Button rightBtn2 = (Button) barLayoutView.findViewById(R.id.toolbar_right_btn2);
        TextView rightTv2 = (TextView) barLayoutView.findViewById(R.id.toolbar_right_tv2);
        RelativeLayout barRlyt = (RelativeLayout) barLayoutView.findViewById(R.id.toolbar_content_rlyt);

        if (isLeftBtnVisible) {
            leftBtn.setVisibility(VISIBLE);
        }
        if (isLeftTvVisible) {
            leftTv.setVisibility(VISIBLE);
        }
        if (isRightBtnVisible) {
            rightBtn.setVisibility(VISIBLE);
        }
        if (isRightTvVisible) {
            rightTv.setVisibility(VISIBLE);
        }
        if (isRightBtnVisible2) {
            rightBtn2.setVisibility(VISIBLE);
        }
        if (isRightTvVisible2) {
            rightTv2.setVisibility(VISIBLE);
        }
        if (isTitleVisible) {
            titleTv.setVisibility(VISIBLE);
        }
        leftTv.setText(leftTvText);
        rightTv.setText(rightTvText);
        rightTv2.setText(rightTvText2);
        titleTv.setText(titleText);

        if (leftResId != -1) {
            leftBtn.setBackgroundResource(leftResId);
        }
        if (rightResId != -1) {
            rightBtn.setBackgroundResource(rightResId);
        }
        if (rightResId2 != -1) {
            rightBtn2.setBackgroundResource(rightResId2);
        }
        if (backgroundResId != -1) {
            barRlyt.setBackgroundColor(getResources().getColor(R.color.bg_toolbar));
        }
        //将设置完成之后的View添加到此LinearLayout中
        addView(barLayoutView, 0);
    }
}
