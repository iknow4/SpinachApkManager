
package com.spinach.apkmanager.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.*;
import android.content.res.*;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.spinach.apkmanager.data.AApplication;
import com.spinach.apkmanager.data.MoveListAdapter;
import com.spinach.apkmanager.util.Utils;

import com.spinach.apkmanager.R;
import java.util.*;

import org.xmlpull.v1.XmlPullParser;

public class MoveActivity extends Activity
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
			mHandler.sendEmptyMessage(HANDLER_ANALYSIS_INSTALLED_APP_FINISH_MSG);
		}

		protected void onPreExecute()
		{
			super.onPreExecute();
			MoveActivity moveactivity = MoveActivity.this;
			MoveActivity moveactivity1 = MoveActivity.this;
			ProgressDialog progressdialog = new ProgressDialog(moveactivity1);
			moveactivity.analysisProgressDialog = progressdialog;
			analysisProgressDialog.setProgressStyle(0);
			ProgressDialog progressdialog1 = analysisProgressDialog;
			String s = res.getString(R.string.move_analysis_start_text);
			progressdialog1.setMessage(s);
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
	private static final String TAG = "MoveActivity";
	private ProgressDialog analysisProgressDialog;
	private Handler mHandler;
	private RadioGroup mRadioGroup;
	private ArrayList moveList;
	private MoveListAdapter moveListAdapter;
	private ListView moveListView;
	private RadioButton moveRadioButton;
	private boolean needRefresh;
	private com.spinach.apkmanager.views.TabSwitcher.OnItemClickLisener onItemClickLisener;
	private ArrayList phoneList;
	private MoveListAdapter phoneListAdapter;
	private ListView phoneListView;
	private Resources res;
	private ArrayList sdCardList;
	private MoveListAdapter sdCardListAdapter;
	private ListView sdcardListView;
	private TextView showMessageText;
	private PackageMoveObserver mPackageMoveObserver;
	private PackageManager mPm;

	public MoveActivity()
	{
		Myhandler mhandler = new Myhandler();
		mHandler = mhandler;
		lisener onItemClickLisener = new lisener();
		
	}

	private void analysisApplications()
	{
	
		Iterator iterator;
		ArrayList arraylist = moveList;
		
		boolean flag;
		if (arraylist == null)
		{
			arraylist = new ArrayList();
			moveList = arraylist;
		} else
		{
			ArrayList arraylist2 = moveList;
			arraylist2.clear();
		}
		arraylist = phoneList;
		if (arraylist == null)
		{
			arraylist = new ArrayList();
			phoneList = arraylist;
		} else
		{
			ArrayList arraylist3 = phoneList;
			arraylist3.clear();
		}
		arraylist = sdCardList;
		if (arraylist == null)
		{
			ArrayList arraylist1 = new ArrayList();
			sdCardList = arraylist1;
		} else
		{
			ArrayList arraylist4 = sdCardList;
			arraylist4.clear();
		}
	try{
		PackageManager packagemanager = getPackageManager();
		//iterator = packagemanager.getInstalledPackages(0).iterator();
		List<PackageInfo> packageInfoList = getPackageManager().getInstalledPackages(0); 

		//PackageInfo packageinfo;
		String packageName;
		boolean flag3;
		for(PackageInfo packageinfo : packageInfoList){
			ActivityInfo[] activityInfo = getPackageManager().getPackageInfo(packageinfo.packageName, PackageManager.GET_ACTIVITIES).activities;
			if(activityInfo!=null){
				if ((packageinfo.applicationInfo.flags & 1) <= 0){
					packageName = packageinfo.packageName;
					Log.i(TAG,"packageName = "+packageName);
					long size = Utils.getPackageSize(packageinfo);
					String s4 = Utils.formatFileSize(size);
					AssetManager am = createPackageContext(packageName, 0).getAssets();
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
						                            	MoveActivity moveactivity1 = this;
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
						                            	MoveActivity moveactivity2 = this;
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
					}
				}

			}



		}
	   }catch( Exception e){
			
	}


	/*
	do
	{
		do
		{
			flag = iterator.hasNext();
			if (!flag){
				Log.i(TAG,"****return********");
				return;
			}
				
			packageinfo = (PackageInfo)iterator.next();
			packageName = packageinfo.packageName;
			Log.i(TAG,"packageName = "+packageName);
			
			// -------------------- break line------------------------
			long size = Utils.getPackageSize(packageinfo);
			String s4 = Utils.formatFileSize(size);
		try{	
			AssetManager am = createPackageContext(packageName, 0).getAssets();
			XmlResourceParser xml = am.openXmlResourceParser("AndroidManifest.xml");
			int eventType = xml.getEventType();
			//xmlloop:
			while (eventType != XmlPullParser.END_DOCUMENT) 
			{
				//Log.i(TAG,"eventType != XmlPullParser.END_DOCUMENT");
				//Log.i(TAG,"eventType = "+eventType);
		    	switch (eventType) 
		    	{
		            case XmlPullParser.START_TAG:
		            if (! xml.getName().matches("manifest")) {
		            	Log.i(TAG,"xmlloop");
		                break ;
		            } else 
		            {
		                //attrloop:
		                for (int j = 0; j < xml.getAttributeCount(); j++) 
		                {
		                    if (xml.getAttributeName(j).matches("installLocation")) 
		                    {
		                        switch (Integer.parseInt(xml.getAttributeValue(j))) 
		                        {
		                            case INSTALL_LOCATION_AUTO:
		                                // Do stuff
		                                	MoveActivity moveactivity1 = this;
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
		                               		MoveActivity moveactivity2 = this;
		                               		moveactivity2.distributeAppToList(packageinfo, s4, INSTALL_LOCATION_PREFER_EXTERNAL, packagemanager);
		                                	break;
		                            default:
		                                // Shouldn't happen
		                                // Do stuff
		                                	break;
		                        }
		                      //  break ;//attrloop;
		                    }
		                }
		            }
		            break;
		        }//switch
		        eventType = xml.nextToken();
		    }//while
		}catch(Exception e){
			
		}
		
		} while (true);
		//flag1 = packageName.equals("");
	} while (packageName.equals(""));*/
	
	}

	private void distributeAppToList(PackageInfo packageinfo, String size, long l, PackageManager packagemanager)
	{
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
		if ((packageinfo.applicationInfo.flags & 0x40000) != 0)
			sdCardList.add(aapplication);
		else
			moveList.add(aapplication);
		message = mHandler.obtainMessage();
		message.what = HANDLER_ANALYSIS_INSTALLED_APP_PROGRESS_MSG;
		message.obj = s1;
		mHandler.sendMessage(message);
	}
	
	 class PackageMoveObserver extends IPackageMoveObserver.Stub {
	        public void packageMoved(String packageName, int returnCode) throws RemoteException {
	          //  final Message msg = mHandler.obtainMessage(PACKAGE_MOVE);
	          //  msg.arg1 = returnCode;
	          //  mHandler.sendMessage(msg);
	        }
	 }
	private void move(String pkgname){
		
		 if (mPackageMoveObserver == null) {
             mPackageMoveObserver = new PackageMoveObserver();
         }
		 mPm.movePackage(pkgname, mPackageMoveObserver, PackageManager.MOVE_EXTERNAL_MEDIA);
	}
	
	
	
	
	private void setupViews()
	{
		Resources resources = getResources();
		res = resources;
		showMessageText = (TextView)findViewById(R.id.show_detail_counter);
		//showMessageText = textview;
		
		
		moveListView = (ListView)findViewById(R.id.move_list);
		//moveListView = listview;
		moveListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int i,long arg3) {
				// TODO Auto-generated method stub
				String pkgName = ((AApplication)moveList.get(i)).getPackageName();
				MoveActivity moveactivity = MoveActivity.this;
				Utils.startInstalledAppDetailsAction(pkgName, moveactivity);
				move(pkgName);
			}
			
		});
		
		
		phoneListView = (ListView)findViewById(R.id.phone_list);
		//phoneListView = listview1;
		phoneListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int i,long arg3) {
				// TODO Auto-generated method stub
				String pkgName= ((AApplication)phoneList.get(i)).getPackageName();
				MoveActivity moveactivity = MoveActivity.this;
				Utils.startInstalledAppDetailsAction(pkgName, moveactivity);
			}
			
		});
		
		
		sdcardListView = (ListView)findViewById(R.id.sdcard_list);
		//sdcardListView = listview2;
		sdcardListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView adapterview, View view, int i,long arg3) {
				// TODO Auto-generated method stub
				String pkgName = ((AApplication)sdCardList.get(i)).getPackageName();
				MoveActivity moveactivity = MoveActivity.this;
				Utils.startInstalledAppDetailsAction(pkgName, moveactivity);
				
			}
		} );
		
		
		moveRadioButton = (RadioButton)findViewById(R.id.radio_button0);
		
		
		mRadioGroup = (RadioGroup)findViewById(R.id.main_radio);
		mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				switch(checkedId)
				{
					case  2131361813:
						moveListView.setVisibility(0);
						phoneListView.setVisibility(8);
						sdcardListView.setVisibility(8);
						showMessageText.setText(R.string.analysis_app_counter_text);
						break;
					case 2131361814:
						moveListView.setVisibility(8);
						phoneListView.setVisibility(8);
						sdcardListView.setVisibility(0);
						showMessageText.setText(R.string.analysis_app_counter_text2);
						break;
					case 2131361815:
						moveListView.setVisibility(8);
						phoneListView.setVisibility(0);
						sdcardListView.setVisibility(8);
						showMessageText.setText(R.string.analysis_app_counter_text3);	
						break;
				}
			}
		});
		//mRadioGroup = radiogroup;
		//RadioGroup radiogroup1 = mRadioGroup;
		/*3 3_1 = new 3();
		radiogroup1.setOnCheckedChangeListener(3_1);
		
		
		ListView listview3 = moveListView;
		4 4_1 = new 4();
		listview3.setOnItemClickListener(4_1);
		
		
		ListView listview4 = sdcardListView;
		5 5_1 = new 5();
		listview4.setOnItemClickListener(5_1);
		
		
		ListView listview5 = phoneListView;
		6 6_1 = new 6();
		listview5.setOnItemClickListener(6_1);*/
	}

	protected void onCreate(Bundle bundle)
	{
		Log.i("MoveActivity", "onCreate");
		super.onCreate(bundle);
		requestWindowFeature(1);
		setContentView(R.layout.move);
		setupViews();
		needRefresh = true;
		mPm = getPackageManager();  
	}

	protected void onResume()
	{
		boolean flag = false;
		super.onResume();
		Log.i("MoveActivity", "onResume");
		if (needRefresh)
		{
			AnalysisAppTask analysisapptask = new AnalysisAppTask(null);
			String as[] = new String[0];
			analysisapptask.execute(as);
			needRefresh = flag;
		}
	}

	protected void onStop()
	{
		super.onStop();
		Log.v("MoveActivity", "onStop");
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
					int i = moveList.size();
					int j = sdCardList.size();
					int k = phoneList.size();
					if (i > 0)
					{
						MoveActivity moveactivity = MoveActivity.this;
						MoveActivity moveactivity1 = MoveActivity.this;
						
						moveListAdapter = new MoveListAdapter(moveactivity1, moveList);
						moveListView.setAdapter(moveListAdapter);
					}
					if (j > 0)
					{
						MoveActivity moveactivity2 = MoveActivity.this;
						MoveActivity moveactivity3 = MoveActivity.this;
						
						sdCardListAdapter = new MoveListAdapter(moveactivity3, sdCardList);
						sdcardListView.setAdapter(sdCardListAdapter);
					}
					if (k > 0)
					{
						MoveActivity moveactivity4 = MoveActivity.this;
						MoveActivity moveactivity5 = MoveActivity.this;
						
						phoneListAdapter = new MoveListAdapter(moveactivity5, phoneList);
						phoneListView.setAdapter(phoneListAdapter);
					}
					moveRadioButton.setChecked(true);
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
					phoneListView.setVisibility(0);
					sdcardListView.setVisibility(8);
					showMessageText.setText(R.string.analysis_app_counter_text);
					break;
				case 1:
					String s1 = (new StringBuilder("position=")).append(i).toString();
					Log.v("MoveActivity", s1);
					phoneListView.setVisibility(8);
					sdcardListView.setVisibility(0);
					showMessageText.setText(R.string.analysis_app_counter_text2);
					break;
			
			}
		}

		lisener()
		{
			super();
		}
	}


}
