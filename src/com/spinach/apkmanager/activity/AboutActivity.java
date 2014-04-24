//Author: Jason
//Time: 2012.07.18
// Source File Name:   AboutActivity.java

package com.spinach.apkmanager.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.spinach.apkmanager.R;

public class AboutActivity extends Activity
{
	

	private static final String TAG = "AboutActivity";
	private Context ctx;
	private String version;
	public AboutActivity()
	{
		//super();
		//ctx = context;
	}
 	 private String getVersionName() 
	 {
 		 	try{
	           // 閿熸枻鎷峰彇packagemanager閿熸枻鎷峰疄閿熸枻鎷�
	           PackageManager packageManager = getPackageManager();
	           // getPackageName()閿熸枻鎷烽敓濮愬綋鍓嶉敓鏂ゆ嫹闄岄敓鏂ゆ嫹閿燂拷閿熸枻鎷烽敓鏂ゆ嫹鑵旈敓楗衡槄鎷峰Ч鎾呮嫹閿熻緝锟�	           
	           PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
	           version = packInfo.versionName;
	           Log.i(TAG, "version = "+version); 
 		 	}catch(Exception e){
 				e.printStackTrace();
 			}
 		 	return version;
	}
	private void setupViews()
	{

		TextView app_verion = (TextView)findViewById(R.id.about_version);
		StringBuffer stringbuffer = new StringBuffer(getResources().getString(R.string.version_text));
		String version = stringbuffer.append(getVersionName()).toString();
		app_verion.setText(version);
		Button btn_ok = (Button)findViewById(R.id.about_ok);
		btn_ok.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			
	
		finish();
            }
        });
	
		//textview1.setText("閿熸枻鎷烽敓鑺傛枻鎷烽敓鏂ゆ嫹");
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		requestWindowFeature(1);
		setContentView(R.layout.about);
		setupViews();
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 Log.i(TAG, "AboutActivity -->onDestroy"); 
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferences prefs = getSharedPreferences("is from about activity", 0);
		SharedPreferences.Editor ed = prefs.edit();
		ed.putBoolean("isneedrefresh", true);
		ed.apply();
		Log.i(TAG, "AboutActivity -->onResume"); 
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(TAG, "AboutActivity -->onStop"); 
		
	}
}
