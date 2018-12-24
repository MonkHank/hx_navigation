package com.seuic.hisense.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seuic.hisense.R;


/**
 * Created by Administrator on 2015/9/25.
 */
public class GridviewItem extends LinearLayout{

    private View view;
    private TextView tv_tip;
    private ImageView iv_logo;

    public GridviewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.gridviewitem);
        CharSequence text = a.getText(R.styleable.gridviewitem_gridtext);
        if (text != null) tv_tip.setText(text);
        Drawable mDrawable = a.getDrawable(R.styleable.gridviewitem_gridlogo);
        if (mDrawable != null) iv_logo.setImageDrawable(mDrawable);
        a.recycle();
    }

    public GridviewItem(Context context) {
        super(context);
        initView();
    }


    private void initView() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.grid_menu, this, true);
        tv_tip = (TextView) view.findViewById(R.id.tv_tip);
        iv_logo = (ImageView) view.findViewById(R.id.iv_logo);
    }

    /**
     * 设置图片资源
     */
    public void setImageResource(int resId) {
        iv_logo.setImageResource(resId);
    }

    /**
     * 设置显示的文字
     */
    public void setTextViewText(String text) {
        tv_tip.setText(text);
    }

    /**
     * 获得显示的文字
     */
    public String getTextViewText(String text) {
        return tv_tip.getText().toString();
    }

}
