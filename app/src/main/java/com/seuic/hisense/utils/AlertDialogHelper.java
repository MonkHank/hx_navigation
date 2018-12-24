package com.seuic.hisense.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.seuic.hisense.R;


public class AlertDialogHelper implements OnClickListener {
	
	private static AlertDialogHelper _dialog;
	private AlertDialogCallBack _callBack;
	
	public static AlertDialogHelper getInstance(){
		if(_dialog == null){
			_dialog = new AlertDialogHelper();
		}
		return _dialog;
	}
	
	public void showWithSelection(Context context ,String msg , AlertDialogCallBack callBack){
		
		_callBack = callBack;
		
		AlertDialog dialog = new AlertDialog.Builder(context)
		.setPositiveButton(context.getString(R.string.utils_dialog_ok), this)
		.setNegativeButton(context.getString(R.string.utils_dialog_cancel), this)
		.setMessage(msg)
		.setTitle(context.getString(R.string.utils_dialog_title))
		.create();
		dialog.show();
	}

    AlertDialog dialogShowMessage;
    public void showMessage(Context context ,String msg){
        _callBack = null;//因为是单例模式，所以有可能这个回调还是上次的
        closeMessage();
        dialogShowMessage = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setPositiveButton(context.getString(R.string.utils_dialog_ok), this)
                .setMessage(msg)
                .setTitle(context.getString(R.string.utils_dialog_title))
                .create();
        dialogShowMessage.setCanceledOnTouchOutside(true);
        dialogShowMessage.show();
    }

    public void closeMessage(){
        if(dialogShowMessage != null){
            dialogShowMessage.hide();
            dialogShowMessage.dismiss();
            dialogShowMessage = null;
            _callBack = null;
        }
    }

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(null == _callBack ){
			return;
		}
		
		switch (which) {
		case AlertDialog.BUTTON_POSITIVE:
			_callBack.dialogCallBack(true);
			break;
		case AlertDialog.BUTTON_NEGATIVE:
			_callBack.dialogCallBack(false);
			break;
		}
	}
}
