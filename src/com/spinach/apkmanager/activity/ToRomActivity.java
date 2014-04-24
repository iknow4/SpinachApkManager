
package com.spinach.apkmanager.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.*;
import android.content.res.*;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.spinach.apkmanager.data.AApplication;
import com.spinach.apkmanager.data.AppListAdapter;
import com.spinach.apkmanager.data.MoveListAdapter;
import com.spinach.apkmanager.util.Utils;

import com.spinach.apkmanager.R;
import java.util.*;

import org.xmlpull.v1.XmlPullParser;

public class ToRomActivity extends UniversalActivity
{
	class AnalysisAppTask extends AsyncTask
	{

		protected  Object doInBackground(Object aobj[])
		{
			String as[] = (String[])aobj;
			return doInBackground(((String []) (aobj)));
		}

		protected  String doInBackground(String as[])
		{
			Log.i("ToRomActivity", " doInBackground .......................");
			analysisApplications();
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
			Log.i("ToRomActivity", " sendEmptyMessage .............HANDLER_ANALYSIS_INSTALLED_APP_FINISH_MSG..........");
			mHandler.sendEmptyMessage(HANDLER_ANALYSIS_INSTALLED_APP_FINISH_MSG);
			
		}

		protected void onPreExecute()
		{
			super.onPreExecute();
	
			analysisProgressDialog = new ProgressDialog(ToRomActivity.this);
			
			analysisProgressDialog.setProgressStyle(0);
	
			String s = res.getString(R.string.move_analysis_start_text);
			analysisProgressDialog.setMessage(s);
			analysisProgressDialog.show();
		}

		private AnalysisAppTask()
		{
			super();
		}

		AnalysisAppTask(AnalysisAppTask analysisapptask)
		{
			this();
		}
	}


	private static final int HANDLER_ANALYSIS_INSTALLED_APP_FINISH_MSG = 10002;
	private static final int HANDLER_ANALYSIS_INSTALLED_APP_PROGRESS_MSG = 10001;
	public static final int INSTALL_LOCATION_AUTO = 0;
	public static final int INSTALL_LOCATION_INTERNAL_ONLY = 1;
	public static final int INSTALL_LOCATION_PREFER_EXTERNAL = 2;
	private static final int CONFIRM_MOVE_TO_PHONE_DIALOG = 10001;
	private static final int NO_SELECTED_ITEM_DIALOG = 10002;
	private static final String TAG = "ToRomActivity";
	private ProgressDialog analysisProgressDialog;
	private Handler mHandler;
	private boolean needRefresh;
	private boolean setAllchecked;
	private int apkListCount ;
	private com.spinach.apkmanager.views.TabSwitcher.OnItemClickLisener onItemClickLisener;
	private ArrayList phoneList;
	private Resources res;
	private ArrayList sdCardList;
	private AppListAdapter sdCardListAdapter;

	private TextView showMessageText;
	private ArrayList tempAppList;

	public ToRomActivity()
	{
		Myhandler mhandler = new Myhandler();
		mHandler = mhandler;
		lisener onItemClickLisener = new lisener();
		
	}

	private void analysisApplications()
	{
	
		Iterator iterator;
		
		boolean flag;
		Log.i(TAG,"analysisApplications..................");
		if (sdCardList == null)
		{
			
			sdCardList = new ArrayList();
			Log.i(TAG,"sdCardList == null");
			
		} else
		{
			try {  
                Thread.sleep(500);  
            } catch (InterruptedException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  	
			sdCardList.clear();
			Log.i(TAG,"sdCardList.clear()");
		}
	try{
		PackageManager packagemanager = getPackageManager();
		//iterator = packagemanager.getInstalledPackages(0).iterator();
		List<PackageInfo> packageInfoList = getPackageManager().getInstalledPackages(0); 
		Log.i(TAG, "packageInfoList............................");
		//PackageInfo packageinfo;
		String packageName;
		boolean flag3;
		for(PackageInfo packageinfo : packageInfoList){
			Log.i(TAG, "PackageInfo............................");
			ActivityInfo[] activityInfo = getPackageManager().getPackageInfo(packageinfo.packageName, PackageManager.GET_ACTIVITIES).activities;
			if(activityInfo!=null){
				if ((packageinfo.applicationInfo.flags & 1) <= 0){
					packageName = packageinfo.packageName;
					Log.i(TAG,"packageName = "+packageName);
					long size = Utils.getPackageSize(packageinfo);
					String s4 = Utils.formatFileSize(size);

					//jason add for get app which can move to sd card at 2012-09-22 15:14
					distributeAppToList(packageinfo, s4, INSTALL_LOCATION_AUTO, packagemanager);
					/*AssetManager am = createPackageContext(packageName, 0).getAssets();
					XmlResourceParser xml = am.openXmlResourceParser("AndroidManifest.xml");
					int eventType = xml.getEventType();
					while (eventType != XmlPullParser.END_DOCUMENT) 
					{
						switch (eventType) 
						{
						            case XmlPullParser.START_TAG:
						            if (! xml.getName().matches("manifest")) {
						            	//Log.i(TAG,"xmlloop");
						                break ;
						            } else 
						            {
						  
						                for (int j = 0; j < xml.getAttributeCount(); j++) 
						                {
						                    if (xml.getAttributeName(j).matches("installLocation")) 
						                    {
						                        switch (Integer.parseInt(xml.getAttributeValue(j))) 
						                        {
						                            case INSTALL_LOCATION_AUTO:
						                                // Do stuff
						                            	ToRomActivity moveactivity1 = this;
						                                	moveactivity1.distributeAppToList(packageinfo, s4, INSTALL_LOCATION_AUTO, packagemanager);
						                                	break;
						                            case INSTALL_LOCATION_INTERNAL_ONLY:
						                                // Do stuf
						                              	  	String s5 = packageinfo.applicationInfo.loadLabel(packagemanager).toString();
											android.graphics.drawable.Drawable drawable = packageinfo.applicationInfo.loadIcon(packagemanager);
											String pkName = packageinfo.packageName;
											String versionName = packageinfo.versionName;
											AApplication aapplication = new AApplication();
										
											aapplication.setName(pkName);
											android.graphics.drawable.Drawable drawable1 = drawable;
											aapplication.setIcon(drawable1);
											aapplication.setSize(s4);
											aapplication.setSizeValue(s4.length());
										
											aapplication.setPackageName(pkName);
									
											aapplication.setVersionName(versionName);
											phoneList.add(aapplication);
											Message message = mHandler.obtainMessage();
											message.what = HANDLER_ANALYSIS_INSTALLED_APP_PROGRESS_MSG;
										
											message.obj = s5;
											
											Message message1 = message;
											mHandler.sendMessage(message1);
						                                	break;
						                            case INSTALL_LOCATION_PREFER_EXTERNAL:
						                                // Do stuff
						                            	ToRomActivity moveactivity2 = this;
						                               		moveactivity2.distributeAppToList(packageinfo, s4, INSTALL_LOCATION_PREFER_EXTERNAL, packagemanager);
						                                	break;
						                            default:
						                                // Shouldn't happen
						                                // Do stuff
						                                	break;
						                        }
						                      //  break ;
						                    }
						                }
						            }
						            break;
						        }
						eventType = xml.nextToken();
					}*/
				}

			}else{

				Log.i("ToRomActivity", " activityInfo = null  ..................");	
			}



		}
	   }catch( Exception e){
		Log.i("ToRomActivity", " ToRomActivity   Exception ..................");	
	}
	
	}

	private void distributeAppToList(PackageInfo packageinfo, String size, long l, PackageManager packagemanager)
	{
		Log.i("ToRomActivity", "distributeAppToList start");
		String s1 = packageinfo.applicationInfo.loadLabel(packagemanager).toString();
		android.graphics.drawable.Drawable drawable = packageinfo.applicationInfo.loadIcon(packagemanager);
		String pkgname = packageinfo.packageName;
		String versionname = packageinfo.versionName;
		AApplication aapplication = new AApplication();
		aapplication.setName(s1);
		aapplication.setIcon(drawable);
		aapplication.setSize(size);
		aapplication.setSizeValue(l);
		aapplication.setPackageName(pkgname);
		aapplication.setVersionName(versionname);
		Message message;
		if ((packageinfo.applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0)
		{
			Log.i("ToRomActivity", "packageinfo.applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0");
			sdCardList.add(aapplication);
		}

		message = mHandler.obtainMessage();
		message.what = HANDLER_ANALYSIS_INSTALLED_APP_PROGRESS_MSG;
		message.obj = s1;
		mHandler.sendMessage(message);
	}
	
	
	private void setupViews()
	{
		Resources resources = getResources();
		res = resources;
		String s = res.getString(R.string.move_to_rom);
		firstButton.setText(s);
		secondButton.setVisibility(4);
		selectAllCheckBox = (CheckBox)findViewById(R.id.btn_do_select_all);

		mainListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView adapterview, View view, int i,long arg3) {
				// TODO Auto-generated method stub
				//String pkgName = ((AApplication)sdCardList.get(i)).getPackageName();
				//ToRomActivity moveactivity = ToRomActivity.this;
				//Utils.startInstalledAppDetailsAction(pkgName, moveactivity);
				com.spinach.apkmanager.data.MainListViewAdapter.ViewHolder viewholder = (com.spinach.apkmanager.data.MainListViewAdapter.ViewHolder)view.getTag();
				viewholder.checkBox.toggle();
				AApplication aapplication = (AApplication)sdCardList.get(i);
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
				if(sdCardList.size()==apkListCount)
				{
					selectAllCheckBox.setChecked(true);
				}	
				
			}
		} );
		firstButton.setOnClickListener(new android.view.View.OnClickListener(){
			public void onClick(View view)
			{
				Object obj = tempAppList;
				AApplication aapplication;
				if (tempAppList == null)
				{
					
					tempAppList = new ArrayList();
					
				} else
				{
					
					tempAppList.clear();
				}
				obj = sdCardList.iterator();
				do
				{
					do
					{
						if (!((Iterator) (obj)).hasNext())
						{
							
							if (tempAppList.size() == 0)
								showDialog(NO_SELECTED_ITEM_DIALOG);
							else
								showDialog(CONFIRM_MOVE_TO_PHONE_DIALOG);
							return;
						}
						aapplication = (AApplication)((Iterator) (obj)).next();
					} while (!aapplication.isSelected());
					tempAppList.add(aapplication);
				} while (true);
			}
		});
		SharedPreferences prefs = getSharedPreferences("is from about activity", 0);
		SharedPreferences.Editor ed = prefs.edit();
		ed.putBoolean("isneedrefresh", false);
		ed.apply();
	}

	protected void onCreate(Bundle bundle)
	{
		Log.i("ToRomActivity", "onCreate");
		super.onCreate(bundle);
		//requestWindowFeature(1);
		//setContentView(R.layout.to_sd_card);
		setupViews();
		needRefresh = true;
		setAllchecked = true;
		
	}

	protected void onResume()
	{
		super.onResume();
		apkListCount = 0;
		Log.i("ToRomActivity", "onResume");
		SharedPreferences sh = getSharedPreferences("is from about activity", 0);
        	boolean isfromAboutActivity = sh.getBoolean("isneedrefresh", false);
		//if(sdCardListAdapter !=null)
		//sdCardListAdapter.notifyDataSetChanged();
		if(isfromAboutActivity){
			needRefresh = false;
			SharedPreferences prefs = getSharedPreferences("is from about activity", 0);
			SharedPreferences.Editor ed = prefs.edit();
			ed.putBoolean("isneedrefresh", false);
			ed.apply();
		}
		if (needRefresh)
		{
			Log.i("ToRomActivity", "onResume needRefresh = true");
			AnalysisAppTask analysisapptask = new AnalysisAppTask(null);
			String as[] = new String[0];
			analysisapptask.execute(as);
			//if(sdCardListAdapter !=null)
			//sdCardListAdapter.notifyDataSetChanged();
			needRefresh = false;
		}
	}

	protected void onStop()
	{
		super.onStop();
		Log.i("ToRomActivity", "onStop");
		needRefresh = true;
	}


	private class Myhandler extends Handler
	{

		public void handleMessage(Message message)
		{
			switch(message.what)
			{
				case HANDLER_ANALYSIS_INSTALLED_APP_PROGRESS_MSG:
					String s = (String)message.obj;
					String s1 = res.getString(R.string.analysising_text);
					ProgressDialog progressdialog = analysisProgressDialog;
					String s2 = String.valueOf(s1);
					String s3 = (new StringBuilder(s2)).append(s).toString();
					progressdialog.setMessage(s3);
					break;
				case HANDLER_ANALYSIS_INSTALLED_APP_FINISH_MSG:
					analysisProgressDialog.dismiss();
		
					//if (sdCardList.size() > 0)
					//{	
						Log.i("ToRomActivity", "ToRomActivity HANDLER_ANALYSIS_INSTALLED_APP_FINISH_MSG");
						sdCardListAdapter = new AppListAdapter(ToRomActivity.this, sdCardList);
						mainListView.setAdapter(sdCardListAdapter);


						bigDivider.setVisibility(0);
						emptyMessageIcon.setVisibility(8);
						emptyTextView.setVisibility(8);
					//}else
					if(sdCardList.size()== 0){
						bigDivider.setVisibility(8);
						emptyMessageIcon.setVisibility(0);
						emptyTextView.setVisibility(0);
						if(!Utils.checkSDCard()){
			        			emptyTextView.setText(R.string.apk_no_SDcard_text);
						}else{
			        			emptyTextView.setText(R.string.no_movable_app);
						}
					}
					sdCardListAdapter.notifyDataSetChanged();
					break;
			}
		
		}

		Myhandler()
		{
			super();
		}
	}


	private class lisener implements com.spinach.apkmanager.views.TabSwitcher.OnItemClickLisener
	{
		public void onItemClickLisener(View view, int i)
		{
			switch(i)
			{
				case 0:
					String s = (new StringBuilder("position=")).append(i).toString();
					Log.v("MoveActivity", s);
					
					mainListView.setVisibility(8);
					showDetailCounterTextView.setText(R.string.analysis_app_counter_text);
					break;
				case 1:
					String s1 = (new StringBuilder("position=")).append(i).toString();
					Log.v("MoveActivity", s1);
					
					mainListView.setVisibility(0);
					showDetailCounterTextView.setText(R.string.analysis_app_counter_text2);
					break;
			
			}
		}

		lisener()
		{
			super();
		}
	}

	//“Ï≤Ω¥¶¿Ì
	class MoveToPhoneTask extends AsyncTask
	{

	

		protected   Object doInBackground(Object aobj[])
		{
			String as[] = (String[])aobj;
			return doInBackground(((String []) (aobj)));
		}

		protected  String doInBackground(String as[])
		{
			MoveToPhone();
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

		private MoveToPhoneTask()
		{
			
			super();
		}

		MoveToPhoneTask(MoveToPhoneTask movetophonetask)
		{
			this();
		}
	}

	private Dialog buildCOUD()
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
		String s = res.getString(R.string.confirm_move_dialog_text);
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

	private Dialog buildNSUD()
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
		String s = res.getString(R.string.no_selected_move_dialog_text);
		builder.setMessage(s);
		String s1 = res.getString(R.string.app_name);
		builder.setTitle(s1);
		String s2 = res.getString(R.string.confirm_button_dialog_text);
		mlistener1 listener = new mlistener1();
		builder.setPositiveButton(s2, listener);
		return builder.create();
	}


	protected Dialog onCreateDialog(int i)
	{
		Dialog dialog = super.onCreateDialog(i);
		switch(i)
		{
			case NO_SELECTED_ITEM_DIALOG:
				dialog = buildNSUD();
				break;
			case CONFIRM_MOVE_TO_PHONE_DIALOG: 
				dialog =  buildCOUD();
				break;
		}
		return dialog;
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
			MoveToPhoneTask task = new MoveToPhoneTask(null);
			String as[] = new String[0];
			task.execute(as);
			dialoginterface.dismiss();
		}

		mlistener2()
		{
	
			super();
		}
	}
	protected void MoveToPhone()
	{
		Iterator iterator = tempAppList.iterator();
		do
		{
			if (!iterator.hasNext())
			{
				//dialoginterface.dismiss();
				return;
			}
			String pkgName = ((AApplication)iterator.next()).getPackageName();
			Log.i("ToSdCardActivity", "pkgName = "+pkgName);
			Utils.startInstalledAppDetailsAction(pkgName, ToRomActivity.this);
		} while (true);
	}
}
