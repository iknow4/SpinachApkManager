package com.spinach.apkmanager.util;


import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityManager;

import com.android.settings.applications.ApplicationsState;
import com.android.settings.applications.ApplicationsState.AppEntry;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageMoveObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.app.Application;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;


import android.os.RemoteException;
import android.util.Log;
public class Moving 
		implements ApplicationsState.Callbacks{
	
	 private PackageMoveObserver mPackageMoveObserver;
	 private ApplicationsState mState;
	 private ApplicationsState.AppEntry mAppEntry;
	 private PackageManager mPm;
	 private static final int CLEAR_USER_DATA = 1;
	 private static final int CLEAR_CACHE = 3;
	 private static final int PACKAGE_MOVE = 4;
	 private static final String TAG = "Moving";
	 
	private static final int DLG_BASE = 0;
	private static final int DLG_CLEAR_DATA = DLG_BASE + 1;
	private static final int DLG_FACTORY_RESET = DLG_BASE + 2;
	private static final int DLG_APP_NOT_FOUND = DLG_BASE + 3;
	private static final int DLG_CANNOT_CLEAR_DATA = DLG_BASE + 4;
	private static final int DLG_FORCE_STOP = DLG_BASE + 5;
	private static final int DLG_MOVE_FAILED = DLG_BASE + 6;
	 
	 public Moving(Context ctx)
	 {
		 Log.i("Moving","Moving...........");
		mPm = ctx.getPackageManager();
	 }
	 
	 private Handler mHandler = new Handler() {
	        public void handleMessage(Message msg) {
	            // If the activity is gone, don't process any more messages.
	           // if (isFinishing()) {
	                //return;
	           // }
	            switch (msg.what) {
	                case CLEAR_USER_DATA:
	                   // processClearMsg(msg);
	                    break;
	                case CLEAR_CACHE:
	                    // Refresh size info
	                    //mState.requestSize(mAppEntry.info.packageName);
	                    break;
	                case PACKAGE_MOVE:
	                    processMoveMsg(msg);
	                    break;
	                default:
	                    break;
	            }
	        }
	    };
	 
	 class PackageMoveObserver extends IPackageMoveObserver.Stub {
	        public void packageMoved(String packageName, int returnCode) throws RemoteException {
	            final Message msg = mHandler.obtainMessage(PACKAGE_MOVE);
	            msg.arg1 = returnCode;
	            mHandler.sendMessage(msg);
	        }
	 }
	 
	 private void processMoveMsg(Message msg) {
	        int result = msg.arg1;
	       // String packageName = mAppEntry.info.packageName;
	        // Refresh the button attributes.
	       // mMoveInProgress = false;
	        if (result == PackageManager.MOVE_SUCCEEDED) {
	            Log.i(TAG, "Moved resources for ");
	            // Refresh size information again.
	          //  mState.requestSize(mAppEntry.info.packageName);
	        } else {
	           // mMoveErrorCode = result;
	           
	        }
	       // refreshUi();
	    }
	 
	 public void moveToSdcard(String pkgname)
	 {	
		// mState = ApplicationsState.getInstance(getApplication());
		// mAppEntry = mState.getEntry(pkgname);
		 if (mPackageMoveObserver == null) {
             mPackageMoveObserver = new PackageMoveObserver();
         }
		  //int moveFlags = (mAppEntry.info.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0 ?
                 // PackageManager.MOVE_INTERNAL : PackageManager.MOVE_EXTERNAL_MEDIA;
		 
		  mPm.movePackage(pkgname, mPackageMoveObserver, PackageManager.MOVE_EXTERNAL_MEDIA);
	 }
	 
	 
	    @Override
	    public void onAllSizesComputed() {
	    }

	    @Override
	    public void onPackageIconChanged() {
	    }

	    @Override
	    public void onPackageListChanged() {
	        //refreshUi();
	    }

	    @Override
	    public void onRebuildComplete(ArrayList<AppEntry> apps) {
	    }

	    @Override
	    public void onPackageSizeChanged(String packageName) {
	        //if (packageName.equals(mAppEntry.info.packageName)) {
	           // refreshSizeInfo();
	       // }
	    }

	    @Override
	    public void onRunningStateChanged(boolean running) {
	    }

}
