package com.seuic.hisense.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.seuic.hisense.R;

/**
 * Created by Administrator on 2015/10/22.
 */
public class QueryEditText  extends LinearLayout implements View.OnClickListener{
    private View view;
    private EditText view_et_data;
    private RelativeLayout view_rl;

    public QueryEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.queryEdittext);
        CharSequence text = a.getText(R.styleable.queryEdittext_queryEdittextText);
        if(text != null) view_et_data.setText(text);

        text = a.getText(R.styleable.queryEdittext_queryEdittextHint);
        if(text != null) view_et_data.setHint(text);
        a.recycle();
    }

    public QueryEditText(Context context) {
        super(context);
        initView();
    }


    private void initView(){
        view = LayoutInflater.from(getContext()).inflate(R.layout.view_query_edittext, this, true);
        view_rl = (RelativeLayout) view.findViewById(R.id.view_rl);
        view_rl.setOnClickListener(this);
        view_et_data = (EditText) view.findViewById(R.id.view_et_data);
        view_et_data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOnClickQueryEditText.afterTextChanged(s);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_rl:
                mOnClickQueryEditText.ClickQueryEditText();
                break;
        }
    }

    private OnClickQueryEditText mOnClickQueryEditText;
    public void setOnClickQueryEditText(OnClickQueryEditText mOnClickQueryEditText){
        this.mOnClickQueryEditText = mOnClickQueryEditText;
    }

    public interface OnClickQueryEditText{
        public void ClickQueryEditText();
        public void afterTextChanged(Editable s);
    }

    public String getQueryEditText(){
        return view_et_data.getText().toString();
    }

    public void setQueryEditText(String text){
        view_et_data.setText(text);
    }

    public void setQueryEditTextEnable(boolean enabled){
        view_et_data.setEnabled(enabled);
    }
}
