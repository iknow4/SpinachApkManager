

package com.spinach.apkmanager.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.*;
import android.util.Log;
import android.widget.*;

import com.spinach.apkmanager.data.AApplication;
import com.spinach.apkmanager.data.AppListAdapter;
import com.spinach.apkmanager.util.FileOperator;
import com.spinach.apkmanager.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import android.view.*;
import android.content.DialogInterface;
import com.spinach.apkmanager.R;



public class ApkActivity extends UniversalActivity
{
	private static final int CONFIRM_DELETE_DIALOG = 10005;
	private static final int CONFIRM_ISTALL_DIALOG = 10004;
	private static final int CONFIRM_ONEKEY_INSTALL_DIALOG = 10006;
	private static final int HANDLER_DELETE_FINISH_MSG = 10009;
	private static final int HANDLER_DELETE_PROGRESS_MSG = 10008;
	private static final int HANDLER_ONEKEY_INSTALL_FINISH_MSG = 10011;
	private static final int HANDLER_ONEKEY_INSTALL_PROGRESS_MSG = 10010;
	private static final int HANDLER_SCAN_APK_FINISH_MSG = 10001;
	private static final int NO_SDCARD_WARNING_DIALOG = 10007;
	private static final int NO_SELECTED_DELETE_DIALOG = 10003;
	private static final int NO_SELECTED_INSTALL_DIALOG = 10002;
	private static final String TAG = "ApkActivity";
	private AppListAdapter apkadapter;
	private ArrayList apkList;
	private ProgressDialog deleteProgressDialog;
	private FileOperator mFileOperator;
	private Handler mHandler;
	private boolean needRefresh;
	private boolean setAllchecked;
	private int apkListCount ;
	private ProgressDialog onekeyInstallDialog;
	private ArrayList tempAppList;
	class DeleteApkTask extends AsyncTask
	{

	

		protected   Object doInBackground(Object aobj[])
		{
			String as[] = (String[])aobj;
			return doInBackground(((String []) (aobj)));
		}

		protected  String doInBackground(String as[])
		{
			deleteApkFile();
			return null;
		}

		protected  void onPostExecute(Object obj)
		{
			String s = (String)obj;
			onPostExecute(((String) (obj)));
		}

		protected void onPostExecute(String s)
		{
			super.onPostExecute(s);
			mHandler.sendEmptyMessage(HANDLER_DELETE_FINISH_MSG);
		}

		protected void onPreExecute()
		{
			super.onPreExecute();
			if (deleteProgressDialog == null)
			{
				ApkActivity apkactivity = ApkActivity.this;
				ApkActivity apkactivity1 = ApkActivity.this;
				ProgressDialog progressdialog = new ProgressDialog(apkactivity1);
				apkactivity.deleteProgressDialog = progressdialog;
				deleteProgressDialog.setProgressStyle(0);
			}
			ProgressDialog progressdialog1 = deleteProgressDialog;
			String s = res.getString(R.string.delete_start_text);
			progressdialog1.setMessage(s);
			deleteProgressDialog.show();
			if (mFileOperator == null)
			{
				
				ApkActivity apkactivity3 = ApkActivity.this;
				FileOperator fileoperator = new FileOperator(apkactivity3);
				mFileOperator = fileoperator;
			}
		}

		private DeleteApkTask()
		{
			
			super();
		}

		DeleteApkTask(DeleteApkTask deleteapktask)
		{
			this();
		}
	}

	class OneKeyInstallAppTask extends AsyncTask
	{

	

		protected   Object doInBackground(Object aobj[])
		{
			String as[] = (String[])aobj;
			return doInBackground(((String []) (aobj)));
		}

		protected  String doInBackground(String as[])
		{
			//onekeyInstallApk();
			installAllAPKFiles();
			return null;
		}

		protected  void onPostExecute(Object obj)
		{
			String s = (String)obj;
			onPostExecute(((String) (obj)));
		}

		protected void onPostExecute(String s)
		{
			super.onPostExecute(s);
			mHandler.sendEmptyMessage(HANDLER_ONEKEY_INSTALL_FINISH_MSG);
		}

		protected void onPreExecute()
		{
			super.onPreExecute();
			if (onekeyInstallDialog == null)
			{
				
				ApkActivity apkactivity1 = ApkActivity.this;
				ProgressDialog progressdialog = new ProgressDialog(apkactivity1);
				onekeyInstallDialog = progressdialog;
				onekeyInstallDialog.setProgressStyle(0);
			}
			String s = res.getString(R.string.onekey_install_start_text);
			onekeyInstallDialog.setMessage(s);
			onekeyInstallDialog.show();
		}

		private OneKeyInstallAppTask()
		{
			
			super();
		}

		OneKeyInstallAppTask(OneKeyInstallAppTask onekeyinstallapptask)
		{
			this();
		}
	}

	class ScanApkTask extends UniversalActivity.ScanAppTask
	{

		

		protected  String doInBackground(String as[])
		{
			Handler handler = mHandler;
			apkList = mFileOperator.scanAllApkFile(handler);
			if(apkList == null)
				apkList = new ArrayList();
			return null;
		}

		protected  void onPostExecute(Object obj)
		{
			String s = (String)obj;
			onPostExecute(((String) (obj)));
		}

		protected void onPostExecute(String s)
		{
			super.onPostExecute(s);
			mHandler.sendEmptyMessage(HANDLER_SCAN_APK_FINISH_MSG);
		}

		protected void onPreExecute()
		{
			super.onPreExecute();
			ProgressDialog progressdialog = scanPorgressDialog;
			String s = res.getString(R.string.scan_sdcard_apk_text);
			progressdialog.setMessage(s);
			scanPorgressDialog.show();
			if (mFileOperator == null)
			{
				ApkActivity apkactivity1 = ApkActivity.this;
				FileOperator fileoperator = new FileOperator(apkactivity1);
				mFileOperator = fileoperator;
			}
		}

		private ScanApkTask()
		{
			
			super();
		}

		ScanApkTask(ScanApkTask scanapktask)
		{
			this();
		}
	}




	public ApkActivity()
	{
		handler hdl = new handler();
		mHandler = hdl;
	}

	private Dialog buildCBD()
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
		String s = res.getString(R.string.confirm_delete_dialog_text);
		builder.setMessage(s);
		String s1 = res.getString(R.string.app_name);
		builder.setTitle(s1);
		String s2 = res.getString(R.string.confirm_button_dialog_text);
		mlistener listener = new mlistener();
		builder.setPositiveButton(s2, listener);
		String s3 = res.getString(R.string.cancel_button_dialog_text);
		mlistener1 listener1 = new mlistener1();
		builder.setNegativeButton(s3, listener1);
		return builder.create();
	}

	private Dialog buildCOUD()
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
		String s = res.getString(R.string.confirm_onekey_install_dialog_text);
		builder.setMessage(s);
		String s1 = res.getString(R.string.app_name);
		builder.setTitle(s1);
		String s2 = res.getString(R.string.confirm_button_dialog_text);
		mlistener2 listener2 = new mlistener2();
		builder.setPositiveButton(s2, listener2);
		String s3 = res.getString(R.string.cancel_button_dialog_text);
		mlistener1 listener = new mlistener1();
		builder.setNegativeButton(s3, listener);
		return builder.create();
	}

	private Dialog buildCUD()
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
		String s = res.getString(R.string.confirm_install_dialog_text);
		builder.setMessage(s);
		String s1 = res.getString(R.string.app_name);
		builder.setTitle(s1);
		String s2 = res.getString(R.string.confirm_button_dialog_text);
		mlistener4 listener4 = new mlistener4();
		builder.setPositiveButton(s2, listener4);
		String s3 = res.getString(R.string.cancel_button_dialog_text);
		mlistener1 listener = new mlistener1();
		builder.setNegativeButton(s3, listener);
		return builder.create();
	}

	private Dialog buildNSBD()
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
		String s = res.getString(R.string.no_selected_delete_dialog_text);
		builder.setMessage(s);
		String s1 = res.getString(R.string.app_name);
		builder.setTitle(s1);
		String s2 = res.getString(R.string.confirm_button_dialog_text);
		mlistener1 listener = new mlistener1();
		builder.setPositiveButton(s2, listener);
		return builder.create();
	}

	private Dialog buildNSUD()
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
		String s = res.getString(R.string.no_selected_install_dialog_text);
		builder.setMessage(s);
		String s1 = res.getString(R.string.app_name);
		builder.setTitle(s1);
		String s2 = res.getString(R.string.confirm_button_dialog_text);
		mlistener1 listener = new mlistener1();
		builder.setPositiveButton(s2, listener);
		return builder.create();
	}

	private Dialog buildNSWD()
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
		String s = res.getString(R.string.confirm_sdcard_insert_dialog_text);
		builder.setMessage(s);
		String s1 = res.getString(R.string.app_name);
		builder.setTitle(s1);
		String s2 = res.getString(R.string.confirm_button_dialog_text);
		mlistener1 listener = new mlistener1();
		builder.setPositiveButton(s2, listener);
		return builder.create();
	}

	private void deleteApkFile()
	{
		Iterator iterator = tempAppList.iterator();
	do{
		File file;
		if (!iterator.hasNext())
			break;
		AApplication aapplication = (AApplication)iterator.next();
		String s = aapplication.getName();
		file = aapplication.getFile();
		Log.i("ApkActivity", "deleteApkFile : s ="+ aapplication.getName());
		Log.i("ApkActivity", "deleteApkFile : file ="+ aapplication.getFile());
		Message message = mHandler.obtainMessage();
		message.what = HANDLER_DELETE_PROGRESS_MSG;
		message.obj = s;
		mHandler.sendMessage(message);
		mFileOperator.deleteFile(file);
	  }while(true);

	}

	private void installAPKFile(String s)
	{
		Intent intent = new Intent("android.intent.action.VIEW");
		Uri uri = Uri.fromFile(new File(s));
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		startActivity(intent);
	}

	private void onekeyInstallApk()
	{
		Iterator iterator = tempAppList.iterator();
	do{
		//String s2;
		if (!iterator.hasNext())
			break;
		AApplication aapplication = (AApplication)iterator.next();
		//String s = aapplication.getName();
		String s1 = aapplication.getFile().getAbsolutePath();
		String s2 = (new StringBuilder("cp ")).append(s1).append(" /data/app").toString();
		Message message = mHandler.obtainMessage();
		message.what = HANDLER_ONEKEY_INSTALL_PROGRESS_MSG;
		message.obj = aapplication.getName();
		mHandler.sendMessage(message);
		String s3 = Utils.excuteShellCmd(s2);
		String s4 = (new StringBuilder("ret=")).append(s3).toString();
		Log.i("ApkActivity", "s4 = "+s4);
	 }while(true);
	}

	private void refreshAppList()
	{
		Iterator iterator = apkList.iterator();
		Log.i("ApkActivity","refreshAppList");
		do
		{
			if (!iterator.hasNext())
			{
				apkadapter.notifyDataSetChanged();
				Log.i("ApkActivity","refreshAppList1");
				return;
			}
			((AApplication)iterator.next()).setSelected(false);
		} while (true);
	}

	private void setupViews()
	{
		
		String s = res.getString(R.string.install_text);
		firstButton.setText(s);
		
		String s1 = res.getString(R.string.delete_text);
		secondButton.setText(s1);

		mlistener9 listener9 = new mlistener9();
		mainListView.setOnItemClickListener(listener9);
		
		mlistener10 listener10 = new mlistener10();
		firstButton.setOnClickListener(listener10);
		
		mlistener11 listener11 = new mlistener11();
		secondButton.setOnClickListener(listener11);
		
		//mlistener12 listener12 = new mlistener12();
		//thirdButton.setOnClickListener(listener12);
		
		mlistener13 listener13 = new mlistener13();
		selectAllCheckBox.setOnCheckedChangeListener(listener13);
		SharedPreferences prefs = getSharedPreferences("is from about activity", 0);
		SharedPreferences.Editor ed = prefs.edit();
		ed.putBoolean("isneedrefresh", false);
		ed.apply();
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setupViews();
		needRefresh = true;
		
		setAllchecked = true;
	}

	protected Dialog onCreateDialog(int i)
	{
		Dialog dialog = super.onCreateDialog(i);
		switch(i)
		{
			case NO_SELECTED_INSTALL_DIALOG:
				dialog = buildNSUD();
				break;
			case NO_SELECTED_DELETE_DIALOG: 
				dialog = buildNSBD();
				break;
			case CONFIRM_ISTALL_DIALOG: 
				dialog = buildCUD();
				break;
			case CONFIRM_DELETE_DIALOG: 
				dialog =  buildCBD();
				break;
			case CONFIRM_ONEKEY_INSTALL_DIALOG: 
				dialog =  buildCOUD();
				break;
			case NO_SDCARD_WARNING_DIALOG: 
				dialog = buildNSWD();
				break;		
		}
		return dialog;
	}

	protected void onResume()
	{
		super.onResume();
		apkListCount = 0;
		Log.i("ApkActivity", "Utils.checkSDCard() = "+Utils.checkSDCard());
		SharedPreferences sh = getSharedPreferences("is from about activity", 0);
        	boolean isfromAboutActivity = sh.getBoolean("isneedrefresh", false);
		if(isfromAboutActivity){
			needRefresh = false;
			SharedPreferences prefs = getSharedPreferences("is from about activity", 0);
			SharedPreferences.Editor ed = prefs.edit();
			ed.putBoolean("isneedrefresh", false);
			ed.apply();
		}
		if (!(Utils.checkSDCard()))
		{	
			Log.i("ApkActivity", "NO_SDCARD_WARNING_DIALOG");
			//showDialog(NO_SDCARD_WARNING_DIALOG);
			//return;
		}
		if (needRefresh)
		{
			Log.i("ApkActivity", "canapktask = new ScanApkTask()");
			ScanApkTask scanapktask = new ScanApkTask();
			String as[] = new String[0];
			scanapktask.execute(as);
			needRefresh = false;
		}
	}

	protected void onStop()
	{
		super.onStop();
		Log.i("ApkActivity", "onStop");
		needRefresh = true;
	}

	private class handler extends Handler
	{
		public void handleMessage(Message message)
		{
		switch(message.what)
		{
			case HANDLER_SCAN_APK_FINISH_MSG:
				Log.i("ApkActivity", "ApkActivity HANDLER_SCAN_APK_FINISH_MSG");
				if (scanPorgressDialog != null)
					scanPorgressDialog.dismiss();
				
				//Object aobj[] = new Object[1];
				//Integer integer = Integer.valueOf(apkList.size());
				//aobj[0] = integer;
				//String s = getString(R.string.sdcard_apk_counter_text, integer);
				//showDetailCounterTextView.setText(s);
				
				//if (apkList.size() > 0)
				//{
					
					apkadapter = new AppListAdapter(ApkActivity.this, apkList);
				
					mainListView.setAdapter(apkadapter);

					bigDivider.setVisibility(0);
					emptyMessageIcon.setVisibility(8);
					emptyTextView.setVisibility(8);
				//} else
				if (apkList.size() == 0)
				{	
					bigDivider.setVisibility(8);
					emptyMessageIcon.setVisibility(0);
					emptyTextView.setVisibility(0);
					if(!Utils.checkSDCard()){
		        			emptyTextView.setText(R.string.apk_no_SDcard_text);
					}else{
						emptyTextView.setText(R.string.no_sdcard_apk_text);
					}
				}
				apkadapter.notifyDataSetChanged();
	
					break;
			case HANDLER_DELETE_PROGRESS_MSG:
				String s5 = (String)message.obj;
				String s6 = res.getString(R.string.deleteing_text);
				
				String s7 = String.valueOf(s6);
				String s8 = (new StringBuilder(s7)).append(s5).toString();
				deleteProgressDialog.setMessage(s8);
					break;
			case HANDLER_DELETE_FINISH_MSG:

				deleteProgressDialog.dismiss();
				Toast.makeText(ApkActivity.this, R.string.delete_finish_text, 1).show();
				Iterator iterator = tempAppList.iterator();
				do
				{
					if (!iterator.hasNext())
					{
						apkadapter.notifyDataSetChanged();
						
						//ApkActivity apkactivity3 = ApkActivity.this;
						//Object aobj1[] = new Object[1];
						//Integer integer1 = Integer.valueOf(apkList.size());
						//aobj1[0] = integer1;
						//String s9 = apkactivity3.getString(R.string.sdcard_apk_counter_text, integer1);
						//showDetailCounterTextView.setText(s9);
						if(apkList.size()==0){
							bigDivider.setVisibility(8);
							emptyMessageIcon.setVisibility(0);
							emptyTextView.setVisibility(0);
							if(!Utils.checkSDCard()){
				        			emptyTextView.setText(R.string.apk_no_SDcard_text);
							}else{
								emptyTextView.setText(R.string.no_sdcard_apk_text);
							}

						}
							
						return; 
					}
					AApplication aapplication = (AApplication)iterator.next();
					apkList.remove(aapplication);
				} while (true);
					
			case HANDLER_ONEKEY_INSTALL_PROGRESS_MSG:
				String s10 = (String)message.obj;
				String s11 = res.getString(R.string.onekey_installing_text);
				String s12 = String.valueOf(s11);
				String s13 = (new StringBuilder(s12)).append(s10).toString();
				onekeyInstallDialog.setMessage(s13);
					break;

			case HANDLER_ONEKEY_INSTALL_FINISH_MSG:
				onekeyInstallDialog.dismiss();
				Toast.makeText(ApkActivity.this, R.string.onekey_install_finish_text, 1).show();
				refreshAppList();
					break;
			case Utils.HANDLER_SCAN_APK_PROGRESS_MSG:
				String s1 = (String)message.obj;
				String s2 = res.getString(R.string.scan_sdcard_apk_progress_text);
				
				String s3 = String.valueOf(s2);
				String s4 = (new StringBuilder(s3)).append(s1).toString();
				scanPorgressDialog.setMessage(s4);
					break;
				
		}
		
	}

		handler()
		{

			super();
		}
	}


	private class mlistener implements android.content.DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface dialoginterface, int i)
		{
			if (Utils.checkSDCard())
			{
				ApkActivity apkactivity = ApkActivity.this;
				DeleteApkTask deleteapktask = apkactivity. new DeleteApkTask(null);
				String as[] = new String[0];
				deleteapktask.execute(as);
			} else
			{
				showDialog(NO_SDCARD_WARNING_DIALOG);
			}
			dialoginterface.dismiss();
		}

		mlistener()
		{
		
			super();
		}
	}


	private class mlistener1 implements android.content.DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface dialoginterface, int i)
		{
			dialoginterface.dismiss();
		}

		mlistener1()
		{

			super();
		}
	}

	private class mlistener2 implements android.content.DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface dialoginterface, int i)
		{
			ApkActivity apkactivity = ApkActivity.this;
			OneKeyInstallAppTask onekeyinstallapptask = apkactivity. new OneKeyInstallAppTask(null);
			String as[] = new String[0];
			onekeyinstallapptask.execute(as);
			dialoginterface.dismiss();
		}

		mlistener2()
		{
	
			super();
		}
	}
	class InstallAllAppTask extends AsyncTask
	{

	

		protected   Object doInBackground(Object aobj[])
		{
			String as[] = (String[])aobj;
			return doInBackground(((String []) (aobj)));
		}

		protected  String doInBackground(String as[])
		{
			installAllAPKFiles();
			return null;
		}

		protected  void onPostExecute(Object obj)
		{
			String s = (String)obj;
			onPostExecute(((String) (obj)));
		}

		protected void onPostExecute(String s)
		{
			super.onPostExecute(s);
		}

		protected void onPreExecute()
		{
			super.onPreExecute();

		}

		private InstallAllAppTask()
		{
			
			super();
		}

		InstallAllAppTask(InstallAllAppTask onekeyinstallapptask)
		{
			this();
		}
	}

	protected void installAllAPKFiles()
	{
		Iterator iterator = tempAppList.iterator();
		do
		{
			if (!iterator.hasNext())
			{
				//dialoginterface.dismiss();
				return;
			}
			String s = ((AApplication)iterator.next()).getFile().getAbsolutePath();
			installAPKFile(s);
		} while (true);
	}
	private class mlistener4 implements android.content.DialogInterface.OnClickListener
	{

		public void onClick(DialogInterface dialoginterface, int i)
		{
			ApkActivity apkactivity = ApkActivity.this;
			InstallAllAppTask installapptask = apkactivity. new InstallAllAppTask(null);
			String as[] = new String[0];
			installapptask.execute(as);
			dialoginterface.dismiss();
		}

		mlistener4()
		{
			super();
		}
	}
	private class mlistener9 implements android.widget.AdapterView.OnItemClickListener
	{
		public void onItemClick(AdapterView adapterview, View view, int i, long l)
		{
			com.spinach.apkmanager.data.MainListViewAdapter.ViewHolder viewholder = (com.spinach.apkmanager.data.MainListViewAdapter.ViewHolder)view.getTag();
			viewholder.checkBox.toggle();
			AApplication aapplication = (AApplication)apkList.get(i);
			boolean isChecked = viewholder.checkBox.isChecked();
			aapplication.setSelected(isChecked);
			Log.i("mlistener9","mlistener9 :i= "+i);
		
			boolean isAllChecked = selectAllCheckBox.isChecked();
			if(isAllChecked&&!isChecked){
				setAllchecked = false;
				selectAllCheckBox.setChecked(false);
				apkListCount--;
				return;
			}
			if(isChecked)
				apkListCount++;
			else
				apkListCount--;
			Log.i("mlistener9","apkListCount = "+apkListCount);
			if(apkList.size()==apkListCount)
			{
				selectAllCheckBox.setChecked(true);
			}	
			
		}

		mlistener9()
		{
		
			super();
		}
	}
	private class mlistener10 implements android.view.View.OnClickListener
	{
		public void onClick(View view)
		{

			AApplication aapplication;
			Iterator iterator = apkList.iterator();
			if (tempAppList == null)
			{
				
				tempAppList = new ArrayList();
				
			} else
			{
				
				tempAppList.clear();
			}
			do{
				do
				{
					if (!iterator.hasNext())
					{
						
						if (tempAppList.size() == 0)
							showDialog(NO_SELECTED_INSTALL_DIALOG);
						else
							showDialog(CONFIRM_ISTALL_DIALOG);
						return;
					}
					aapplication = (AApplication)iterator.next();
				} while (!aapplication.isSelected());
				tempAppList.add(aapplication);
			}while(true);
		}
		mlistener10()
		{
			
			super();
		}
	}
	private class mlistener11 implements android.view.View.OnClickListener
	{
		public void onClick(View view)
		{
			AApplication aapplication;
			Iterator iterator = apkList.iterator();
			Log.i("ApkActivity", "mlistener11 : tempAppList ="+ tempAppList);
			if (tempAppList == null)
			{
				
				tempAppList = new ArrayList();
				
			} else
			{
				tempAppList.clear();
			}
			do{
				do
				{	Log.i("ApkActivity", "mlistener11 : while");
					if (!iterator.hasNext())
					{
						Log.i("ApkActivity", "mlistener11 : tempAppList.size() = "+tempAppList.size());
						if (tempAppList.size() == 0)
							showDialog(NO_SELECTED_DELETE_DIALOG);
						else
							showDialog(CONFIRM_DELETE_DIALOG);
						return;
					}
					 aapplication = (AApplication)iterator.next();
					
				} while (!aapplication.isSelected());
				 tempAppList.add(aapplication);
			}while(true);
		}

		mlistener11()
		{
			
			super();
		}
	}


	private class mlistener12 implements android.view.View.OnClickListener
	{
		public void onClick(View view)
		{
			
			AApplication aapplication;
			Iterator iterator = apkList.iterator();
			if (tempAppList == null)
			{
				
				tempAppList = new ArrayList();
				
			} else
			{
			
				tempAppList.clear();
			}
			
			do{
				do
				{
					if (!iterator.hasNext())
					{
						
						if (tempAppList.size() == 0)
							showDialog(NO_SELECTED_INSTALL_DIALOG);
						else
							showDialog(CONFIRM_ONEKEY_INSTALL_DIALOG);
						return;
					}
					aapplication = (AApplication)iterator.next();
					
				} while (!aapplication.isSelected());
				tempAppList.add(aapplication);
			    }while(true);
				
		}

		mlistener12()
		{
			
			super();
		}
	}


	private class mlistener13 implements android.widget.CompoundButton.OnCheckedChangeListener
	{
		public void onCheckedChanged(CompoundButton compoundbutton, boolean flag)
		{
			Iterator iterator = apkList.iterator();
			Log.i("ApkActivity", "mlistener13 flag = "+flag);
			
			if(apkList.size()==0)
			{
				selectAllCheckBox.setChecked(false);
				return;
			}
			if(!setAllchecked){
				setAllchecked = true;
				return;
			}
			if(flag)
				apkListCount = apkList.size();
			else
				apkListCount = 0;
			do
			{
				if (!iterator.hasNext())
				{
					apkadapter.notifyDataSetChanged();
					return;
				}
				((AApplication)iterator.next()).setSelected(flag);
			} while (true);
		}

		mlistener13()
		{
		
			super();
		}
	}

}
