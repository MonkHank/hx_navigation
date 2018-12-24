package com.seuic.hisense.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.seuic.hisense.R;


public class DialogUtils {

	private static AlertDialog oneDialog;
	protected static Dialog twoDialog;
	
	
	/**
	 * 两个按钮的提示框
	 * @param context
	 * @param text
	 */
	public static void showTwoButton(final Context context, String text) {
		Builder build = new Builder(context);
		build.setCancelable(false);
		View view = View.inflate(context, R.layout.dialog_whitess, null);
		TextView tv_dialog_title = (TextView) view.findViewById(R.id.tv_dialog_title);
		TextView tv_dialog_message = (TextView) view.findViewById(R.id.tv_dialog_message);
		Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

		tv_dialog_title.setText("提示");
		tv_dialog_message.setText(text);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				twoDialog.dismiss();
				((Activity) context).finish();
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				twoDialog.dismiss();
			}
		});
		build.setView(view);
		twoDialog = build.create();
		twoDialog.show();
	}

	public static void showTwoButtonFragment(final Context context, final FragmentHelper mFHelper,
											 String text) {
		Builder build = new Builder(context);
		build.setCancelable(false);
		View view = View.inflate(context, R.layout.dialog_whitess, null);
		TextView tv_dialog_title = (TextView) view.findViewById(R.id.tv_dialog_title);
		TextView tv_dialog_message = (TextView) view.findViewById(R.id.tv_dialog_message);
		Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

		tv_dialog_title.setText("提示");
		tv_dialog_message.setText(text);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				twoDialog.dismiss();
				mFHelper.transcateBack();
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				twoDialog.dismiss();
			}
		});
		build.setView(view);
		twoDialog = build.create();
		twoDialog.show();
	}
	
	/**
	 * 一个按钮的提示框
	 * @param context
	 * @param text
	 */
	public static  void showOneButton(Context context,String text){
		Builder build = new Builder(context);
		View inflate = View.inflate(context, R.layout.dialog_white, null);
		TextView tv_dialog_title = (TextView) inflate.findViewById(R.id.tv_dialog_title);
		TextView tv_dialog_message = (TextView) inflate.findViewById(R.id.tv_dialog_message);
		Button btn_ok = (Button) inflate.findViewById(R.id.btn_ok);
		tv_dialog_title.setText("提示");
		tv_dialog_message.setText(text);
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				oneDialog.dismiss();
			}
		});
		build.setView(inflate);
		oneDialog = build.create();
		oneDialog.show();
	}
}
