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
 * Created by Administrator on 2015/9/24.
 */
public class SubitemMenu extends LinearLayout{
    private View view;
    private ImageView iv_tip;
    private TextView tv_tip;


    public SubitemMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SubitemMenu);
        CharSequence text = a.getText(R.styleable.SubitemMenu_SubitemMenustartext);
        if(text != null) tv_tip.setText(text);
        Drawable mDrawable = a.getDrawable(R.styleable.SubitemMenu_SubitemMenulogo);
        if (mDrawable != null) iv_tip.setImageDrawable(mDrawable);
        a.recycle();
    }

    public SubitemMenu(Context context) {
        super(context);
        initView();
    }

    private void initView(){
        view = LayoutInflater.from(getContext()).inflate(R.layout.subitem_menu, this, true);
        tv_tip = (TextView) view.findViewById(R.id.tv_tip);
        iv_tip = (ImageView) view.findViewById(R.id.iv_tip);
    }

    public void setTip(String tip){
        tv_tip.setText(tip);
    }

}
