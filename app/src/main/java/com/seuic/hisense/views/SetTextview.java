package com.seuic.hisense.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seuic.hisense.R;

/**
 * Created by Administrator on 2015/9/24.
 */
public class SetTextview extends LinearLayout{
    private View view;
    private TextView tv_tip;
    private TextView et_content;


    public SetTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.setTextviewTip);
        CharSequence text = a.getText(R.styleable.setTextviewTip_setTextviewTipText);
        if(text != null) tv_tip.setText(text);
        text = a.getText(R.styleable.setTextviewTip_setTextviewTipHint);
        if(text != null) et_content.setText(text);
        a.recycle();
    }

    public SetTextview(Context context) {
        super(context);
        initView();
    }

    private void initView(){
        view = LayoutInflater.from(getContext()).inflate(R.layout.set_textview, this, true);
        tv_tip = (TextView) view.findViewById(R.id.tv_tip);
        et_content = (TextView) view.findViewById(R.id.et_content);
    }


    public void setTip(String tip){
        tv_tip.setText(tip);
    }

    public void setContent(String content){
        et_content.setText(content);
    }

    public String getContent(){
        return et_content.getText().toString();
    }

}
