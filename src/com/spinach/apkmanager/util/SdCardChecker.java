package com.spinach.apkmanager.util;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.annotation.SuppressLint;
import android.content.pm.*;
import android.preference.ListPreference;
import android.preference.Preference;
import com.android.internal.content.PackageHelper;
import android.provider.Settings;
import android.content.Context;

public class SdCardChecker {
    final IPackageManager mPm;
    
    private static final String KEY_APP_INSTALL_LOCATION = "app_install_location";
    private static final String APP_INSTALL_SDCARD_ID = "sdcard";
    private static final int APP_INSTALL_SDCARD = 2;
    private ListPreference InstallLocation;
    private static int mInstallLocation;
    
    public SdCardChecker() {
        mPm = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
    }
    
     @SuppressLint("NewApi")
	public void init(Context context) {
	 if(Utils.checkSDCard())/*jason make change the store location*/
    		//Settings.System.putInt(context.getContentResolver(),Settings.Secure.DEFAULT_INSTALL_LOCATION, APP_INSTALL_SDCARD);
		  android.provider.Settings.Global.getInt(context.getContentResolver(),
	                android.provider.Settings.Global.DEFAULT_INSTALL_LOCATION,
	                PackageHelper.APP_INSTALL_EXTERNAL);
		 
		//InstallLocation = (ListPreference) findPreference(KEY_APP_INSTALL_LOCATION);
	    //InstallLocation.setValue(APP_INSTALL_SDCARD_ID);
        try {
            mInstallLocation = mPm.getInstallLocation();
        } catch (RemoteException e) {
            Log.i("CanBeOnSdCardChecker", "Is Package Manager running?");
            return;
        }
    }
    
    public static boolean check(ApplicationInfo info) {
        boolean canBe = false;
	    Log.i("SdCardChecker", "mInstallLocation = "+mInstallLocation);
        if ((info.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
        	Log.i("CanBeOnSdCardChecker", "check1");
            canBe = true;
        } else 
        {
            if ((info.flags & ApplicationInfo.FLAG_FORWARD_LOCK) == 0 &&
            		(info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                if (info.installLocation == PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL ||
                        info.installLocation == PackageInfo.INSTALL_LOCATION_AUTO) {
                	Log.i("CanBeOnSdCardChecker", "check2");
                    canBe = true;
                } else if (info.installLocation== PackageInfo.INSTALL_LOCATION_UNSPECIFIED) 
                {
                    if (mInstallLocation == PackageHelper.APP_INSTALL_EXTERNAL) 
                    {
                        // For apps with no preference and the default value set
                        // to install on sdcard.
                        canBe = true;
                        Log.i("CanBeOnSdCardChecker", "check3");
                    }
                }
            }
        }
        return canBe;
    }
}

