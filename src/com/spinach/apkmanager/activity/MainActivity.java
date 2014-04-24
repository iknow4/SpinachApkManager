

package com.spinach.apkmanager.activity;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.spinach.apkmanager.AppManager;
import com.spinach.apkmanager.R;
import com.spinach.apkmanager.util.SdCardChecker;
public class MainActivity extends TabActivity implements TabHost.OnTabChangeListener
{
	private static final String TAG = "MainActivity";
	private RadioGroup group;
	private TabHost tabHost;
	private int defaulttab = 0;
	
	public static final String TAB_1="installActivity";
	public static final String TAB_2="uninstallActivity";
	public static final String TAB_3="movetoSdcardActivity";
	public static final String TAB_4="movetoRomActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);

		AppManager.getAppManager().addActivity(this);
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		group = (RadioGroup)findViewById(R.id.main_radio);
		tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);
		
		tabHost.addTab(tabHost.newTabSpec(TAB_1)
	                .setIndicator(TAB_1)
	                .setContent(new Intent(this,com.spinach.apkmanager.activity.InstalledAppActivity.class)));
		
	    tabHost.addTab(tabHost.newTabSpec(TAB_2)
	                .setIndicator(TAB_2)
	                .setContent(new Intent(this,com.spinach.apkmanager.activity.ApkActivity.class)));
		
	    tabHost.addTab(tabHost.newTabSpec(TAB_3)
	    		.setIndicator(TAB_3)
	    		.setContent(new Intent(this,com.spinach.apkmanager.activity.ToSdCardActivity.class)));
		
	    //add your Tab view,and when click the radio_button3,then will entry new view 
	    tabHost.addTab(tabHost.newTabSpec(TAB_4)
	    		.setIndicator(TAB_4)
	    		.setContent(new Intent(this,com.spinach.apkmanager.activity.ToRomActivity.class)));

	RadioButton radiobutton0 = (RadioButton)findViewById(R.id.radio_button0);
	RadioButton radiobutton1 = (RadioButton)findViewById(R.id.radio_button1);
	RadioButton radiobutton2 = (RadioButton)findViewById(R.id.radio_button2);
	RadioButton radiobutton3 = (RadioButton)findViewById(R.id.radio_button3);
	
	tabHost.setCurrentTab(defaulttab);
	
	int currenttab = tabHost.getCurrentTab();
	if(currenttab == defaulttab){
		radiobutton0.setChecked(true);
		//radiobutton0.setButtonDrawable(R.drawable.icon4_n);
	}
	else if(currenttab==1)
		radiobutton1.setChecked(true);		
	else if(currenttab==2)
		radiobutton2.setChecked(true);		
	else
		radiobutton3.setChecked(true);	

	  group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_button0:
					tabHost.setCurrentTabByTag(TAB_1);
					break;
				case R.id.radio_button1:
					tabHost.setCurrentTabByTag(TAB_2);
					break;
				case R.id.radio_button2:
					tabHost.setCurrentTabByTag(TAB_3);
					break;
				case R.id.radio_button3:
					tabHost.setCurrentTabByTag(TAB_4);
					break;
				default:
					//tabHost.setCurrentTabByTag(TAB_WORLDCLOCK);
					break;
				}
			}
		});
	}
	
    //@Override
    public void onTabChanged(String tabId) {
        // Because we're using Activities as our tab children, we trigger
        // onWindowFocusChanged() to let them know when they're active.  This may
        // seem to duplicate the purpose of onResume(), but it's needed because
        // onResume() can't reliably check if a keyguard is active.
        
        Log.i(TAG , "----------------------onTabChanged--------------------");
       // Activity activity = getLocalActivityManager().getActivity(tabId);
       // if (activity != null) {
            //activity.onWindowFocusChanged(true);
        //}
        //setTabsLabelColor(tabId);
    }
}
