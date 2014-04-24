

package com.spinach.apkmanager.activity;

import android.app.Dialog; 
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.*;
import android.content.pm.PackageManager.NameNotFoundException;
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
import java.util.*;
import android.view.*;
import android.content.DialogInterface;
import com.spinach.apkmanager.R;
public class InstalledAppActivity extends UniversalActivity
{
	private static final int CONFIRM_BACKUP_DIALOG = 10005;
	private static final int CONFIRM_ONEKEY_UNINSTALL_DIALOG = 10006;
	private static final int CONFIRM_UNISTALL_DIALOG = 10004;
	private static final int HANDLER_BACKUP_FINISH_MSG = 10009;
	private static final int HANDLER_BACKUP_PROGRESS_MSG = 10008;
	private static final int HANDLER_ONEKEY_UNINSTALL_FINISH_MSG = 10011;
	private static final int HANDLER_ONEKEY_UNINSTALL_PROGRESS_MSG = 10010;
	private static final int HANDLER_SCAN_INSTALLED_APP_FINISH_MSG = 10001;
	private static final int HANDLER_UNINSTALL_MYSELF_WARNIGH_MSG = 10012;
	private static final int NO_SDCARD_WARNING_DIALOG = 10007;
	private static final int NO_SELECTED_BACKUP_DIALOG = 10003;
	private static final int NO_SELECTED_UNINSTALL_DIALOG = 10002;
	private static final String TAG = "InstalledAppActivity";
	private AppListAdapter adapter;
	private ProgressDialog backupProgressDialog;
	private ArrayList installedAppList;
	private FileOperator mFileOperator;
	private Handler mHandler;
	private boolean needRefresh;
	private boolean setAllchecked;
	private int apkListCount ;
	private ProgressDialog onekeyUninstallDialog;
	private ArrayList tempAppList;
	class BackupAppTask extends AsyncTask
	{

		protected Object doInBackground(Object aobj[])
		{
			String as[] = (String[])aobj;
			return doInBackground(((String []) (aobj)));
		}

		protected String doInBackground(String as[])
		{
			backupApp();
			return null;
		}

		protected void onPostExecute(Object obj)
		{
			String s = (String)obj;
			onPostExecute(((String) (obj)));
		}

		protected void onPostExecute(String s)
		{
			super.onPostExecute(s);
			mHandler.sendEmptyMessage(HANDLER_BACKUP_FINISH_MSG);
		}

		protected void onPreExecute()
		{
			super.onPreExecute();
			if (backupProgressDialog == null)
			{
				InstalledAppActivity installedappactivity = InstalledAppActivity.this;
				InstalledAppActivity installedappactivity1 = InstalledAppActivity.this;
				ProgressDialog progressdialog = new ProgressDialog(installedappactivity1);
				installedappactivity.backupProgressDialog = progressdialog;
				backupProgressDialog.setProgressStyle(0);
			}
			ProgressDialog progressdialog1 = backupProgressDialog;
			String s = res.getString(R.string.backup_start_text);
			progressdialog1.setMessage(s);
			backupProgressDialog.show();
			if (mFileOperator == null)
			{
				InstalledAppActivity installedappactivity2 = InstalledAppActivity.this;
				FileOperator fileoperator = new FileOperator(InstalledAppActivity.this);
				installedappactivity2.mFileOperator = fileoperator;
				mFileOperator.checkBackupFolderExist();
			}
		}

		private BackupAppTask()
		{
			super();
		}

		BackupAppTask(BackupAppTask backupapptask)
		{
			this();
		}
	}

	class OneKeyUninstallAppTask extends AsyncTask
	{


		protected  Object doInBackground(Object aobj[])
		{
			String as[] = (String[])aobj;
			return doInBackground(((String []) (aobj)));
		}

		protected  String doInBackground(String as[])
		{
			onekeyUninstallApp();
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
			mHandler.sendEmptyMessage(HANDLER_ONEKEY_UNINSTALL_FINISH_MSG);
		}

		protected void onPreExecute()
		{
			super.onPreExecute();
			if (onekeyUninstallDialog == null)
			{
				InstalledAppActivity installedappactivity = InstalledAppActivity.this;
				InstalledAppActivity installedappactivity1 = InstalledAppActivity.this;
				ProgressDialog progressdialog = new ProgressDialog(installedappactivity1);
				installedappactivity.onekeyUninstallDialog = progressdialog;
				onekeyUninstallDialog.setProgressStyle(0);
			}
			ProgressDialog progressdialog1 = onekeyUninstallDialog;
			String s = res.getString(R.string.onekey_uninstall_start_text);
			progressdialog1.setMessage(s);
			onekeyUninstallDialog.show();
		}

		private OneKeyUninstallAppTask()
		{

			super();
		}

		OneKeyUninstallAppTask(OneKeyUninstallAppTask onekeyuninstallapptask)
		{
			this();
		}
	}

	class ScanInstalledAppTask extends UniversalActivity.ScanAppTask
	{

	

		protected  String doInBackground(String as[])
		{
			Log.i("InstalledAppActivity", "####################ScanInstalledAppTask  doInBackground ###################");
			getInstalledApps();
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
			Log.i("InstalledAppActivity", "####################HANDLER_SCAN_INSTALLED_APP_FINISH_MSG###################");
			mHandler.sendEmptyMessage(HANDLER_SCAN_INSTALLED_APP_FINISH_MSG);
		}

		protected void onPreExecute()
		{
			super.onPreExecute();
			Log.i("InstalledAppActivity", "####################onPreExecute###################");
			ProgressDialog progressdialog = scanPorgressDialog;
			String s = res.getString(R.string.scan_installed_app_text);
			progressdialog.setMessage(s);
			scanPorgressDialog.show();
		}

		private ScanInstalledAppTask()
		{
			super();
		}

		ScanInstalledAppTask(ScanInstalledAppTask scaninstalledapptask)
		{
			this();
		}
	}
	public InstalledAppActivity()
	{
		handler hdl = new handler();
		mHandler = hdl;
	}

	private void backupApp()
	{


		Iterator iterator = tempAppList.iterator();
	do{
		String s2;
		String s3;
		if (!iterator.hasNext())
			return;
		AApplication aapplication = (AApplication)iterator.next();
		String s = aapplication.getName();
		String s1 = aapplication.getPackageName();
		s2 = aapplication.getPath();
		s3 = (new StringBuilder("/mnt/sdcard/spinach/backup/")).append(s1).append(".apk").toString();
		Message message = mHandler.obtainMessage();
		message.what = HANDLER_BACKUP_PROGRESS_MSG;
		message.obj = s;
		mHandler.sendMessage(message);
		File file = new File(s2);
		File file1 = new File(s3);
		//if (!file1.exists())
			//file1.createNewFile();
		//mFileOperator.copyFile(file, file1);
		}while(true);

	}

	private Dialog buildCBD()
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
		String s = res.getString(R.string.confirm_backup_dialog_text);
		builder.setMessage(s);
		String s1 = res.getString(R.string.app_name);
		builder.setTitle(s1);
		String s2 = res.getString(R.string.confirm_button_dialog_text);
		mListener7 listener7 = new mListener7();
		builder.setPositiveButton(s2, listener7);
		String s3 = res.getString(R.string.cancel_button_dialog_text);
		mListener2 listener = new mListener2();
		builder.setNegativeButton(s3, listener);
		return builder.create();
	}

	private Dialog buildCOUD()
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
		String s = res.getString(R.string.confirm_onekey_uninstall_dialog_text);
		builder.setMessage(s);
		String s1 = res.getString(R.string.app_name);
		builder.setTitle(s1);
		String s2 = res.getString(R.string.confirm_button_dialog_text);
		mListener9 listener9 = new mListener9();
		builder.setPositiveButton(s2, listener9);
		String s3 = res.getString(R.string.cancel_button_dialog_text);
		mListener2 listener = new mListener2();
		builder.setNegativeButton(s3, listener);
		return builder.create();
	}

	private Dialog buildCUD()
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
		String s = res.getString(R.string.confirm_uninstall_dialog_text);
		builder.setMessage(s);
		String s1 = res.getString(R.string.app_name);
		builder.setTitle(s1);
		String s2 = res.getString(R.string.confirm_button_dialog_text);
		mListener5 listener5 = new mListener5();
		builder.setPositiveButton(s2, listener5);
		String s3 = res.getString(R.string.cancel_button_dialog_text);
		mListener2 listener2 = new mListener2();
		builder.setNegativeButton(s3, listener2);
		return builder.create();
	}

	private Dialog buildNSBD()
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
		String s = res.getString(R.string.no_selected_backup_dialog_text);
		builder.setMessage(s);
		String s1 = res.getString(R.string.app_name);
		builder.setTitle(s1);
		String s2 = res.getString(R.string.confirm_button_dialog_text);
		mListener2 listener = new mListener2();
		builder.setPositiveButton(s2, listener);
		return builder.create();
	}

	private Dialog buildNSUD()
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
		String s = res.getString(R.string.no_selected_uninstall_dialog_text);
		builder.setMessage(s);
		String s1 = res.getString(R.string.app_name);
		builder.setTitle(s1);
		String s2 = res.getString(R.string.confirm_button_dialog_text);
		mListener2 listener = new mListener2();
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
		mListener2 listener2 = new mListener2();
		builder.setPositiveButton(s2, listener2);
		return builder.create();
	}

	private void getInstalledApps()
	{
		
		//int i = 0;
		PackageManager pm = getPackageManager();
		if (installedAppList == null)
		{
			installedAppList = new ArrayList();
		} else
		{
			try {  
                Thread.sleep(500);  
            } catch (InterruptedException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  	
			installedAppList.clear();
		}
	try{
		List<PackageInfo> packageInfoList = getPackageManager().getInstalledPackages(0); 
		Log.i("InstalledAppActivity","**************getInstalledApps packageInfoList.size() = "+packageInfoList.size());
		for(PackageInfo packageinfo : packageInfoList){
			ActivityInfo[] activityInfo = getPackageManager().getPackageInfo(packageinfo.packageName, PackageManager.GET_ACTIVITIES).activities;
			if(activityInfo!=null)
			{
				//for(int i=0; i<activityInfo.length; i++)
				{
					if ((packageinfo.applicationInfo.flags & 1) <= 0){
						AApplication aapplication = new AApplication();
						ApplicationInfo applicationinfo = packageinfo.applicationInfo;
						String s = applicationinfo.loadLabel(pm).toString();
						String s1 = packageinfo.packageName;
						String s2 = packageinfo.versionName;
						String s3 = packageinfo.applicationInfo.publicSourceDir;
						ApplicationInfo applicationinfo1 = packageinfo.applicationInfo;
						android.graphics.drawable.Drawable drawable = applicationinfo1.loadIcon(pm);
						String s5 = Utils.convertDateTime((new File(packageinfo.applicationInfo.sourceDir)).lastModified());
						long l = Utils.getPackageSize(packageinfo);
						String s6 = Utils.formatFileSize(l);
						aapplication.setName(s);
						aapplication.setPackageName(s1);
						aapplication.setVersionName(s2);
						aapplication.setPath(s3);
						aapplication.setIcon(drawable);
						aapplication.setCreateTime(s5);
						aapplication.setSize(s6);
						aapplication.setSizeValue(s6.length());
						installedAppList.add(aapplication);
				       }
				}
			}	
		}
	}catch( NameNotFoundException e)
	{
		
	}
	
	/*	List list = getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES);
		Log.i("InstalledAppActivity","**************getInstalledApps list.size() = "+list.size());
		do
		{
			if (list.size() <= i)
				return;
			PackageInfo packageinfo = (PackageInfo)list.get(i);
			if ((packageinfo.applicationInfo.flags & 1) <= 0)
			{
				AApplication aapplication = new AApplication();
				ApplicationInfo applicationinfo = packageinfo.applicationInfo;
				String s = applicationinfo.loadLabel(pm).toString();
				String s1 = packageinfo.packageName;
				String s2 = packageinfo.versionName;
				String s3 = packageinfo.applicationInfo.publicSourceDir;
				ApplicationInfo applicationinfo1 = packageinfo.applicationInfo;
				android.graphics.drawable.Drawable drawable = applicationinfo1.loadIcon(pm);
				String s5 = Utils.convertDateTime((new File(packageinfo.applicationInfo.sourceDir)).lastModified());
				long l = Utils.getPackageSize(packageinfo);
				String s6 = Utils.formatFileSize(l);
				aapplication.setName(s);
				aapplication.setPackageName(s1);
				aapplication.setVersionName(s2);
				aapplication.setPath(s3);
				aapplication.setIcon(drawable);
				aapplication.setCreateTime(s5);
				aapplication.setSize(s6);
				aapplication.setSizeValue(s6.length());
				installedAppList.add(aapplication);
			}
			i++;
		} while (true);*/
	}

	private void onekeyUninstallApp()
	{
		Iterator iterator = tempAppList.iterator();
		do
		{
			String s2;
			if (!iterator.hasNext())
				return;
			AApplication aapplication = (AApplication)iterator.next();
			String s = aapplication.getName();
			String s1 = aapplication.getPackageName();
			if (s1.equals("com.spinach.apkmanager"))
			{
				aapplication.setSelected(false);
				mHandler.sendEmptyMessage(HANDLER_UNINSTALL_MYSELF_WARNIGH_MSG);
				return; 
			}
			s2 = (new StringBuilder("pm uninstall ")).append(s1).toString();
			Message message = mHandler.obtainMessage();
			message.what = HANDLER_ONEKEY_UNINSTALL_PROGRESS_MSG;
			message.obj = s;
			mHandler.sendMessage(message);
			String s3 = Utils.excuteShellCmd(s2);
			String s4 = (new StringBuilder("ret=")).append(s3).toString();
			Log.v("InstalledAppActivity", s4);
		}while(true);
	}

	private void refreshAppList()
	{
		boolean flag = false;
		Iterator iterator = installedAppList.iterator();
		do
		{
			if (!iterator.hasNext())
			{
				adapter.notifyDataSetChanged();
				selectAllCheckBox.setChecked(flag);
				return;
			}
			((AApplication)iterator.next()).setSelected(flag);
		} while (true);
	}

	private void setCounterText()
	{
		int i = installedAppList.size();
		
		Object aobj[] = new Object[1];
		Integer integer = Integer.valueOf(i);
		aobj[0] = integer;
		String s = getString(R.string.installed_app_counter_text, aobj);
		showDetailCounterTextView.setText(s);
	}

	private void setupViews()
	{
	
		String s = res.getString(R.string.unInstall_text);
		firstButton.setText(s);
	
		String s1 = res.getString(R.string.backup_text);
		secondButton.setText(s1);
		secondButton.setVisibility(4);
		
		//String s2 = res.getString(R.string.onekey_uninstall_text);
		//thirdButton.setText(s2);
		//thirdButton.setVisibility(8);
		
		mListener11 listener11 = new mListener11();
		mainListView.setOnItemClickListener(listener11);
		
		mListener12 listener12 = new mListener12();
		firstButton.setOnClickListener(listener12);
	
		mListener13 listener13 = new mListener13();
		secondButton.setOnClickListener(listener13);
		
		//mListener14 listener14 = new mListener14();
		//thirdButton.setOnClickListener(listener14);
	
		mListener15 listener15 = new mListener15();
		selectAllCheckBox.setOnCheckedChangeListener(listener15);
		SharedPreferences prefs = getSharedPreferences("is from about activity", 0);
		SharedPreferences.Editor ed = prefs.edit();
		ed.putBoolean("isneedrefresh", false);
		ed.apply();
	}

	private void uninstallAppNormal(String s)
	{
		Uri uri = Uri.parse((new StringBuilder("package:")).append(s).toString());
		Intent intent = new Intent("android.intent.action.DELETE", uri);
		startActivity(intent);
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setupViews();
		needRefresh = true;
		
		setAllchecked = true;
		Log.i("InstalledAppActivity", "InstalledAppActivity------>>>>onCreate");

	}

	protected Dialog onCreateDialog(int i)
	{
		Dialog dialog = super.onCreateDialog(i);
		switch(i){
			case NO_SELECTED_UNINSTALL_DIALOG:
				dialog = buildNSUD();
				break;
			case CONFIRM_UNISTALL_DIALOG:
				dialog = buildCUD();
				break;
			case NO_SELECTED_BACKUP_DIALOG:
				dialog = buildNSBD();
			case CONFIRM_BACKUP_DIALOG:
				dialog = buildCBD();
				break;
			case CONFIRM_ONEKEY_UNINSTALL_DIALOG:
				dialog = buildCOUD();
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
		Log.i("InstalledAppActivity", "##########################onResume########################");
		SharedPreferences sh = getSharedPreferences("is from about activity", 0);
        	boolean isfromAboutActivity = sh.getBoolean("isneedrefresh", false);
		if(isfromAboutActivity){
			needRefresh = false;
			SharedPreferences prefs = getSharedPreferences("is from about activity", 0);
			SharedPreferences.Editor ed = prefs.edit();
			ed.putBoolean("isneedrefresh", false);
			ed.apply();
		}
		if (needRefresh)
		{
			ScanInstalledAppTask scaninstalledapptask = new ScanInstalledAppTask();
			String as[] = new String[0];
			scaninstalledapptask.execute(as);
			needRefresh = false;
		}
	}

	protected void onStop()
	{
		super.onStop();
		Log.i("InstalledAppActivity", "onStop");
		needRefresh = true;
	}


	private class handler extends Handler
	{

		

		public void handleMessage(Message message)
		{
		switch(message.what)
		{
			case HANDLER_SCAN_INSTALLED_APP_FINISH_MSG:
				if (scanPorgressDialog != null)
					scanPorgressDialog.dismiss();
				//setCounterText();
				//if (installedAppList.size() > 0)
				//{	
					Log.i("InstalledAppActivity", "InstalledAppActivity HANDLER_SCAN_INSTALLED_APP_FINISH_MSG");		
					adapter = new AppListAdapter(InstalledAppActivity.this, installedAppList);
					mainListView.setAdapter(adapter);


					bigDivider.setVisibility(0);
					emptyMessageIcon.setVisibility(8);
					emptyTextView.setVisibility(8);
				//} else
				if (installedAppList.size() == 0)
				{
					Log.i("InstalledAppActivity", "installedAppList.size() < 0");
					bigDivider.setVisibility(8);
					emptyMessageIcon.setVisibility(0);
					emptyTextView.setVisibility(0);

					emptyTextView.setText(R.string.no_app_text);
					
				}
				adapter.notifyDataSetChanged();
				selectAllCheckBox.setChecked(false);
				break;
			case HANDLER_BACKUP_PROGRESS_MSG:
				String s = (String)message.obj;
				String s1 = res.getString(R.string.backuping_text);
				
				String s2 = String.valueOf(s1);
				String s3 = (new StringBuilder(s2)).append(s).toString();
				backupProgressDialog.setMessage(s3);
				break;

			case HANDLER_BACKUP_FINISH_MSG:
				backupProgressDialog.dismiss();
				Toast.makeText(InstalledAppActivity.this, R.string.backup_finish_text, 1).show();
				refreshAppList();
				//boolean flag = AppBackupActivity.NEED_REFRESH;
				break;

			case HANDLER_ONEKEY_UNINSTALL_PROGRESS_MSG:
				String s4 = (String)message.obj;
				String s5 = res.getString(R.string.onekey_uninstalling_text);
				ProgressDialog progressdialog1 = onekeyUninstallDialog;
				String s6 = String.valueOf(s5);
				String s7 = (new StringBuilder(s6)).append(s4).toString();
				progressdialog1.setMessage(s7);				
				break;

			case HANDLER_ONEKEY_UNINSTALL_FINISH_MSG:
				onekeyUninstallDialog.dismiss();
				Toast.makeText(InstalledAppActivity.this, R.string.onekey_uninstall_finish_text, 1).show();
				Iterator iterator = tempAppList.iterator();
				do
				{
					if (!iterator.hasNext())
					{
						adapter.notifyDataSetChanged();
						setCounterText();
						return; 
					}
					AApplication aapplication = (AApplication)iterator.next();
					installedAppList.remove(aapplication);
				} while (true);
			

			case HANDLER_UNINSTALL_MYSELF_WARNIGH_MSG:
				adapter.notifyDataSetChanged();
				InstalledAppActivity installedappactivity2 = InstalledAppActivity.this;
				String s8 = res.getString(R.string.unistall_myself_warning_text);
				Toast.makeText(installedappactivity2, s8, 0).show();			
				break;
		}
	}

		handler()
		{
			
			super();
		}
	}


	private class mListener7 implements android.content.DialogInterface.OnClickListener
	{

		

		public void onClick(DialogInterface dialoginterface, int i)
		{
			if (Utils.checkSDCard())
			{
				InstalledAppActivity installedappactivity = InstalledAppActivity.this;
				BackupAppTask backupapptask = installedappactivity. new BackupAppTask(null);
				String as[] = new String[0];
				backupapptask.execute(as);
			} else
			{
				showDialog(10007);
			}
			dialoginterface.dismiss();
		}

		mListener7()
		{
			
			super();
		}
	}
	private class mListener9 implements android.content.DialogInterface.OnClickListener
	{

	
		public void onClick(DialogInterface dialoginterface, int i)
		{
			InstalledAppActivity installedappactivity = InstalledAppActivity.this;
			OneKeyUninstallAppTask onekeyuninstallapptask = installedappactivity. new OneKeyUninstallAppTask(null);
			String as[] = new String[0];
			onekeyuninstallapptask.execute(as);
			dialoginterface.dismiss();
		}

		mListener9()
		{
			
			super();
		}
	}
	private class mListener5 implements android.content.DialogInterface.OnClickListener
	{

	

		public void onClick(DialogInterface dialoginterface, int i)
		{
			boolean flag = false;
			Iterator iterator = tempAppList.iterator();
			do
			{
				if (!iterator.hasNext())
				{
					dialoginterface.dismiss();
					return;
				}
				AApplication aapplication = (AApplication)iterator.next();
				String s = aapplication.getPackageName();
				if (s.equals("com.spinach.apkmanager"))
				{
					InstalledAppActivity installedappactivity = InstalledAppActivity.this;
					String s1 = res.getString(R.string.unistall_myself_warning_text);
					Toast.makeText(installedappactivity, s1, Toast.LENGTH_SHORT).show();
					aapplication.setSelected(flag);
					adapter.notifyDataSetChanged();
				} else
				{
					uninstallAppNormal(s);
				}
			} while (true);
		}

		mListener5()
		{
			
			super();
		}
	}
	private class mListener2 implements android.content.DialogInterface.OnClickListener
	{

	

		public void onClick(DialogInterface dialoginterface, int i)
		{
			dialoginterface.dismiss();
		}

		mListener2()
		{
			super();
		}
	}


	private class mListener11 implements android.widget.AdapterView.OnItemClickListener
	{

		

		public void onItemClick(AdapterView adapterview, View view, int i, long l)
		{
			com.spinach.apkmanager.data.MainListViewAdapter.ViewHolder viewholder = (com.spinach.apkmanager.data.MainListViewAdapter.ViewHolder)view.getTag();
			viewholder.checkBox.toggle();
			AApplication aapplication = (AApplication)installedAppList.get(i);
			boolean isChecked = viewholder.checkBox.isChecked();
			aapplication.setSelected(isChecked);
			
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
			if(installedAppList.size()==apkListCount)
			{
				selectAllCheckBox.setChecked(true);
			}	
		}

		mListener11()
		{
			
			super();
		}
	}


	private class mListener12 implements android.view.View.OnClickListener
	{



		public void onClick(View view)
		{
			Object obj = tempAppList;
			AApplication aapplication;
			if (obj == null)
			{
				obj = InstalledAppActivity.this;
				ArrayList arraylist = new ArrayList();
				tempAppList = arraylist;
			} else
			{
				obj = tempAppList;
				((ArrayList) (obj)).clear();
			}
			obj = installedAppList.iterator();
			do
			{
				do
				{
					if (!((Iterator) (obj)).hasNext())
					{
						
						if (tempAppList.size() == 0)
							showDialog(10002);
						else
							showDialog(10004);
						return;
					}
					aapplication = (AApplication)((Iterator) (obj)).next();
				} while (!aapplication.isSelected());
				tempAppList.add(aapplication);
			} while (true);
		}

		mListener12()
		{
			
			super();
		}
	}


	private class mListener13 implements android.view.View.OnClickListener
	{

	

		public void onClick(View view)
		{
			
			AApplication aapplication;
			if (tempAppList == null)
			{
				
				ArrayList arraylist = new ArrayList();
				tempAppList = arraylist;
			} else
			{
				
				tempAppList.clear();
			}
			
			do
			{
				do
				{
					if (!((Iterator) (installedAppList.iterator())).hasNext())
					{
						
						if (tempAppList.size() == 0)
							showDialog(10003);
						else
							showDialog(10005);
						return;
					}
					aapplication = (AApplication)((Iterator) (installedAppList.iterator())).next();
				} while (!aapplication.isSelected());
				tempAppList.add(aapplication);
			} while (true);
		}

		mListener13()
		{
		
			super();
		}
	}


	private class mListener14 implements android.view.View.OnClickListener
	{



		public void onClick(View view)
		{
			Object obj = tempAppList;
			AApplication aapplication;
			if (obj == null)
			{
				obj = InstalledAppActivity.this;
				ArrayList arraylist = new ArrayList();
				tempAppList = arraylist;
			} else
			{
				obj = tempAppList;
				((ArrayList) (obj)).clear();
			}
			obj = installedAppList.iterator();
			do
			{
				do
				{
					if (!((Iterator) (obj)).hasNext())
					{
						
						if (tempAppList.size() == 0)
							showDialog(10002);
						else
							showDialog(10006);
						return;
					}
					aapplication = (AApplication)((Iterator) (obj)).next();
				} while (!aapplication.isSelected());
				tempAppList.add(aapplication);
			} while (true);
		}

		mListener14()
		{
		
			super();
		}
	}


	private class mListener15 implements android.widget.CompoundButton.OnCheckedChangeListener
	{


		public void onCheckedChanged(CompoundButton compoundbutton, boolean flag)
		{
			Iterator iterator = installedAppList.iterator();
			if(installedAppList.size()==0){
				selectAllCheckBox.setChecked(false);
				return;
			}
			if(!setAllchecked){
				setAllchecked = true;
				return;
			}
			if(flag)
				apkListCount = installedAppList.size();
			else
				apkListCount = 0;
			do
			{
				if (!iterator.hasNext())
				{
					adapter.notifyDataSetChanged();
					return;
				}
				((AApplication)iterator.next()).setSelected(flag);
			} while (true);
		}

		mListener15()
		{
			super();
		}
	}

}
