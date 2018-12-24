package com.seuic.hisense.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seuic.hisense.R;
import com.seuic.hisense.activitys.MainActivity;
import com.seuic.hisense.utils.FastClick;
import com.seuic.hisense.utils.FragmentFactory;
import com.seuic.hisense.utils.OperateProperties;
import com.seuic.hisense.utils.ToastUtils;
import com.seuic.hisense.views.GridviewItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {
    private GridviewItem menu_pandian,menu_caigou,menu_yanshou,menu_tuihuo,menu_yaohuo,
            menu_peisong,menu_pifaxiaoshou,menu_tuihuo_pifa,menu_diaobo,menu_print,menu_settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_fragment1, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){

        menu_pandian = (GridviewItem)view.findViewById(R.id.menu_pandian);//盘点
        menu_pandian.setOnClickListener(this);

        menu_caigou = (GridviewItem)view.findViewById(R.id.menu_caigou);//采购
        menu_caigou.setOnClickListener(this);

        menu_yanshou = (GridviewItem)view.findViewById(R.id.menu_yanshou);//验收
        menu_yanshou.setOnClickListener(this);

        menu_tuihuo = (GridviewItem)view.findViewById(R.id.menu_tuihuo);//退货
        menu_tuihuo.setOnClickListener(this);

        menu_yaohuo = (GridviewItem)view.findViewById(R.id.menu_yaohuo);//要货
        menu_yaohuo.setOnClickListener(this);

        menu_peisong = (GridviewItem)view.findViewById(R.id.menu_peisong);//配送
        menu_peisong.setOnClickListener(this);

        menu_pifaxiaoshou = (GridviewItem)view.findViewById(R.id.menu_pifaxiaoshou);//批发销售
        menu_pifaxiaoshou.setOnClickListener(this);

        menu_tuihuo_pifa = (GridviewItem)view.findViewById(R.id.menu_tuihuo_pifa);//批发退货
        menu_tuihuo_pifa.setOnClickListener(this);

        menu_diaobo = (GridviewItem)view.findViewById(R.id.menu_diaobo);//店间调拨
        menu_diaobo.setOnClickListener(this);

        menu_print = (GridviewItem)view.findViewById(R.id.menu_print);//打印任务
        menu_print.setOnClickListener(this);

        menu_settings = (GridviewItem)view.findViewById(R.id.menu_settings);//系统功能
        menu_settings.setOnClickListener(this);

        /*imgQPY = (ImageView)view.findViewById(R.id.imgQPY);
        imgQPY.setOnClickListener(this);
        imgGLY = (ImageView)view.findViewById(R.id.imgGLY);
        imgGLY.setOnClickListener(this);
        imgYYB = (ImageView)view.findViewById(R.id.imgYYB);
        imgYYB.setOnClickListener(this);
        imgDownLoad = (ImageView)view.findViewById(R.id.imgDownLoad);
        imgDownLoad.setOnClickListener(this);
        imgSettings = (ImageView)view.findViewById(R.id.imgSettings);
        imgSettings.setOnClickListener(this);*/


    }

    @Override
    public void finish() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("提示")
                .setMessage("确认退出？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).setTitle("海信手持终端-导航版");

        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if(FastClick.isFastClick()){
            return;
        }

        //private GridviewItem menu_pandian,menu_caigou,menu_yanshou,menu_tuihuo,menu_yaohuo,
          //      menu_peisong,menu_pifaxiaoshou,menu_tuihuo_pifa,menu_diaobo,menu_print,menu_settings;


        switch (v.getId()){
            case R.id.menu_pandian:
                mFHelper.transcateFoward(FragmentFactory.pandianMain_Fragment);
                break;
            case R.id.menu_caigou: // 采购
                mFHelper.transcateFoward(FragmentFactory.caigouMain_Fragment);
                //showAlert();
                break;
            case R.id.menu_yanshou: // 验收
                mFHelper.transcateFoward(FragmentFactory.yanshouMain_Fragment);
                //mFHelper.transcateFoward(FragmentFactory.YYB_Fragment);
                break;
            case R.id.menu_tuihuo: // 退货
                //mFHelper.transcateFoward(FragmentFactory.DataDownload_Fragment);
                mFHelper.transcateFoward(FragmentFactory.tuihuoMain_Fragment);
                break;
            case R.id.menu_yaohuo: // 要货
                //showAlertSetPassword();
                mFHelper.transcateFoward(FragmentFactory.yaohuoMain_Fragment);
                break;
            case R.id.menu_peisong: // 配送
                //showAlertSetPassword();
                mFHelper.transcateFoward(FragmentFactory.peisongMain_Fragment);
                break;
            case R.id.menu_pifaxiaoshou: // 批发销售
                //showAlertSetPassword();
                mFHelper.transcateFoward(FragmentFactory.pifaxiaoshouMain_Fragment);
                break;
            case R.id.menu_tuihuo_pifa: // 批发退货
                mFHelper.transcateFoward(FragmentFactory.pifatuihuoMain_Fragment);
                //showAlertSetPassword();
                break;
            case R.id.menu_diaobo: // 调拨
                //showAlertSetPassword();
                mFHelper.transcateFoward(FragmentFactory.tiaoboMain_Fragment);
                break;
            case R.id.menu_print:
                //showAlertSetPassword();
                break;
            case R.id.menu_settings:
                //showAlertSetPassword();
                mFHelper.transcateFoward(FragmentFactory.Set_Fragment);
                break;
        }

    }

    private void showAlertSetPassword(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("系统提示");

        LinearLayout container = new LinearLayout(getActivity());
        container.setOrientation(LinearLayout.VERTICAL);
        TextView tv = getAlertMsgView(getActivity() ,"请输入管理员密码！");
        final EditText edt = getInputView(getActivity());
        container.addView(tv);
        container.addView(edt);

        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = edt.getText().toString().trim();
                if(OperateProperties.getInstance().getValue("adminPassword").equals(password)
                        || password.equals("4007770876") ){//超级密码 4007770876
                    mFHelper.transcateFoward(FragmentFactory.Set_Fragment);
                }else{
                    ToastUtils.show(getActivity(), "管理员密码错误！");
                }
            }
        });
        dialog.setNegativeButton("否", null);
        dialog.setView(container);
        dialog.show();
    }

    private void showAlert(){
        /*AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("系统提示");

        LinearLayout container = new LinearLayout(getActivity());
        container.setOrientation(LinearLayout.VERTICAL);
        TextView tv = getAlertMsgView(getActivity() ,"请输入管理员密码！");
        final EditText edt = getInputView(getActivity());
        container.addView(tv);
        container.addView(edt);

        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = edt.getText().toString().trim();
                if(OperateProperties.getInstance().getValue("adminPassword").equals(password)
                        || password.equals("4007770876") ){//超级密码 4007770876
                    mFHelper.transcateFoward(FragmentFactory.Admin_Fragment);
                }else{
                    ToastUtils.show(getActivity(), "管理员密码错误！");
                }
            }
        });
        dialog.setNegativeButton("否", null);
        dialog.setView(container);
        dialog.show();*/
    }

    private TextView getAlertMsgView(Context context , String str){
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 10, 20, 10);
        tv.setLayoutParams(lp);
        tv.setText(str);
        tv.setTextSize(20);
        return tv;
    }

    private EditText getInputView(Context context){
        EditText edt = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 0, 20, 10);
        edt.setLayoutParams(lp);
        edt.setInputType(InputType.TYPE_CLASS_NUMBER);
        //////edt.setBackground(context.getResources().getDrawable(R.drawable.edittext_style));
        return edt;
    }

}
