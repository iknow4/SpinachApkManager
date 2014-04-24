

package com.spinach.apkmanager.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ADUtils
{

	public static final int LIMIT_DIALOG = 10001;
	public static final String LIMIT_PREFERENCE = "LimitPreference";
	private Context mContext;
	private Dialog mLimitDialog;
	private android.view.View.OnClickListener mOCL;
	private Button recommendBtn;
	private Button returnBtn;
	private Button unLockBtn;

	public ADUtils(Context context, android.view.View.OnClickListener onclicklistener)
	{
		mContext = context;
		mOCL = onclicklistener;
		initLimitDialog();
	}

	private void initLimitDialog()
	{
		
		mLimitDialog = new Dialog(mContext);
	
		View view = LayoutInflater.from(mContext).inflate(0x7f030007, null);
		mLimitDialog.setContentView(view);
		TextView textview = (TextView)view.findViewById(0x7f0a001e);
		textview.setText("");
		textview.setLineSpacing(0x4059999a, 0x3f800000);
		unLockBtn= (Button)view.findViewById(0x7f0a0020);
		
		recommendBtn = (Button)view.findViewById(0x7f0a0021);
		
		returnBtn = (Button)view.findViewById(0x7f0a0022);

		unLockBtn.setOnClickListener(mOCL);
		
		recommendBtn.setOnClickListener(mOCL);
	
		returnBtn.setOnClickListener(mOCL);
	}

	public Dialog getLimitDialog()
	{
		return mLimitDialog;
	}
}
