

package com.spinach.apkmanager.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import com.spinach.apkmanager.util.Utils;
import com.spinach.apkmanager.AppManager;
import com.spinach.apkmanager.R;
import com.spinach.apkmanager.util.SdCardChecker;



public class UniversalActivity extends Activity
{
	public class ScanAppTask extends AsyncTask
	{
		protected Object doInBackground(Object aobj[])
		{
			String as[] = (String[])aobj;
			return doInBackground(((String []) (aobj)));
		}

		protected String doInBackground(String as[])
		{
			return null;
		}

		protected void onPreExecute()
		{
			super.onPreExecute();
			initScanDialog();
		}

		protected ScanAppTask()
		{
			
			super();
		}
	}

	protected ImageView emptyMessageIcon;
	protected TextView emptyTextView;
	protected TextView bigDivider;
	protected Button firstButton;
	protected ListView mainListView;
	protected Resources res;
	protected ProgressDialog scanPorgressDialog;
	protected Button secondButton;
	protected CheckBox selectAllCheckBox;
	protected TextView showDetailCounterTextView;
	private static final String TAG = "UniversalActivity";
	private SdCardChecker mSdCardChecker;
	
	public UniversalActivity()
	{
	}

	private void initScanDialog()
	{
		ProgressDialog progressdialog = new ProgressDialog(this);
		scanPorgressDialog = progressdialog;
		scanPorgressDialog.setProgressStyle(0);
	}

	private void setupViews()
	{
		showDetailCounterTextView = (TextView)findViewById(R.id.show_detail_counter);
		
		mainListView = (ListView)findViewById(R.id.list);

		emptyMessageIcon =(ImageView)findViewById(R.id.message);
		
		emptyTextView = (TextView)findViewById(R.id.textViewEmpty);

		bigDivider = (TextView)findViewById(R.id.big_divider);
		
		firstButton = (Button)findViewById(R.id.first_button);
		
		secondButton = (Button)findViewById(R.id.second_button);
	
		selectAllCheckBox = (CheckBox)findViewById(R.id.btn_do_select_all);
		
		mainListView.setItemsCanFocus(false);
		initScanDialog();
		res = getResources();
		
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		requestWindowFeature(1);
		setContentView(R.layout.universal);
		setupViews();
	}
	 @Override
	 protected void onResume() {
	        super.onResume();
	        Log.i(TAG , "--------------UniversalActivity--------onResume--------------------");
		mSdCardChecker = new SdCardChecker();
		mSdCardChecker.init(UniversalActivity.this);
		
	}
	public boolean onCreateOptionsMenu(Menu menu)
	{
		Log.i(TAG , "-------------------onCreateOptionsMenu-----------------------");
		MenuItem menuitem = menu.add(0, 0, 0, getString(R.string.about_text));
		android.graphics.drawable.Drawable drawable = getResources().getDrawable(R.drawable.about_icon);
		menuitem.setIcon(drawable);
		MenuItem menuitem1 = menu.add(0, 1, 0, getString(R.string.feedback_text));
		android.graphics.drawable.Drawable drawable1 = getResources().getDrawable(R.drawable.feedback_icon);
		menuitem1.setIcon(drawable1);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem menuitem)
	{
	switch(menuitem.getItemId()){
		case 0:
			Intent intent = new Intent();
			intent.setClass(this, com.spinach.apkmanager.activity.AboutActivity.class);
			startActivity(intent);
			break;
		case 1:
			Intent intent1 = new Intent();
			intent1.setClass(this, com.spinach.apkmanager.feedback.FeedbackActivity.class);
			startActivity(intent1);
			break;
	}
		return super.onOptionsItemSelected(menuitem);
	}
    public static void ExitApp(final Context cont) 
   {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_menu_surelogout);
		builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//閫�嚭
				AppManager.getAppManager().AppExit(cont);
			}
		});
		builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}
	/**
	 * 閻╂垵鎯夋潻鏂挎礀--閺勵垰鎯侀柅锟藉毉缁嬪绨�
	 */
/*    public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			Log.i(TAG , "-------------------ExitApp-----------------------");
			ExitApp(this);
		}else if(keyCode == KeyEvent.KEYCODE_SEARCH){
			;
		}
		return true;
	}*/
    @Override
    public void onBackPressed() {
        Log.i(TAG , "-------------------onBackPressed-----------------------");
        ExitApp(this);
        return;
    }
}
