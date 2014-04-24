
package com.spinach.apkmanager.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Environment;
//import com.sunmontech.apkmanager.RootTools.RootTools;
//import com.sunmontech.apkmanager.RootTools.RootToolsException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import android.util.Log;

public class Utils
{

	public static final String APK_EXTENSION = ".apk";
	public static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";
	public static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
	public static final String APP_FOLDER = "/mnt/sdcard/sunmontech";
	public static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
	public static final String APP_PKG_NAME_22 = "pkg";
	public static final String BACKUP_FOLDER = "/mnt/sdcard/sunmontech/backup";
	public static final String CHMOD_FILE_PRE = "chmod 777 ";
	public static final int HANDLER_SCAN_APK_PROGRESS_MSG = 10012;
	public static final int INSTALLLATION_AUTO = 0;
	public static final int INSTALLLATION_INTERNAL_ONLY = 1;
	public static final String INSTALLLATION_NAME = "installLocation";
	public static final int INSTALLLATION_PREFEREXT_ERNAL = 2;
	public static final String INSTALL_AFT = " /data/app";
	public static final String INSTALL_PRE = "cp ";
	public static final String MANIFEST = "manifest";
	public static final String MANIFEST_FILE_NAME = "AndroidManifest.xml";
	public static final String MOUNT_CMD = "mount -o remount,rw -t yaffs2 /dev/block/mtdblock0 /system";
	public static final String MY_PACKAGE_NAME = "com.sunmontech.apkmanager";
	public static final String SCHEME = "package";
	public static final String UNINSTALL_PRE = "pm uninstall ";
	public static final String UNINSTALL_SYSTEM_APP_PRE = "rm -rf ";

	public Utils()
	{
	}

	public static boolean checkSDCard()
	{
		boolean mount= Environment.getExternalStorageState().equals("mounted");
		return mount;
	}

	public static String convertDateTime(long l)
	{
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(l);
		calendar.setTime(date);
		StringBuffer stringbuffer = new StringBuffer();
	
		stringbuffer.append(calendar.get(5));
		stringbuffer.append('.');
	
		//int k;
		//k++;
		stringbuffer.append(calendar.get(2));
		stringbuffer.append('.');
		
		stringbuffer.append(calendar.get(1));
		stringbuffer.append(" ");
		
		stringbuffer.append(calendar.get(11));
		stringbuffer.append(':');
		
		stringbuffer.append(calendar.get(12));
		stringbuffer.append(':');
		
		stringbuffer.append(calendar.get(13));
		return stringbuffer.toString();
	}

	public static String excuteShellCmd(String s)
	{
	/*
		Object obj = 0;
		if (RootTools.isRootAvailable()) goto _L2; else goto _L1
_L1:
		return ((String) (obj));
_L2:
		List list = null;
		list = RootTools.sendShell(s);
_L4:
		if (list == null || list.size() <= 0) goto _L1; else goto _L3
_L3:
		StringBuilder stringbuilder;
		stringbuilder = new StringBuilder();
		obj = list.iterator();
_L5:
		if (((Iterator) (obj)).hasNext())
			break ;
		obj = stringbuilder.toString();
		  goto _L1
		printStackTrace();
		  goto _L4
		printStackTrace();
		  goto _L4
		printStackTrace();
		  goto _L4
		String s1 = (String)((Iterator) (obj)).next();
		if (!s1.equals("0") && !s1.equals("255"))
			stringbuilder.append(s1);
		 // goto _L5
		 */
		return null;
	}

	public static String formatFileSize(long fsize)
	{
		int i = 0;
		String filesize = null;
		if (fsize >= 1024*1024*1024){

			int j = String.valueOf((float)fsize /(1024*1024*1024)).indexOf(".");
			String s = String.valueOf((float)fsize /(1024*1024*1024));
			String s1 = (new StringBuilder(s)).append("000").toString();
			int k = j + 3;
			String s2 = String.valueOf(s1.substring(i, k));
			filesize = (new StringBuilder(s2)).append("GB").toString();
		}
		else{
			if (fsize >= 1024*1024)
			{
				filesize = String.valueOf((float)fsize /(1024*1024));	
				String subString = filesize.substring(filesize.indexOf(".")+1);
				if(subString.length()>2){
					subString = subString.substring(0,2);
					filesize = filesize.substring(0,filesize.indexOf(".")+1) + subString +"MB";
				}else{
					filesize = filesize + "MB";
				}
				Log.i("formatFileSize", "filesize = "+filesize);
			} else if (fsize >= 1024)
			{
				int k1 = String.valueOf((float)fsize/1024).indexOf(".");
				String s6 = String.valueOf((float)fsize/1024);
				String s7 = String.valueOf((new StringBuilder(s6)).append("000").toString().substring(i, k1));
				filesize = (new StringBuilder(s7)).append("KB").toString();
			} else if (fsize < 1024)
			{
				String size = String.valueOf(Long.toString(fsize));
				filesize = (new StringBuilder(size)).append("B").toString();
			}
		}
			return filesize;
	}

	public static long getFileSize(String s)
	{
		return (new File(s)).length();
	}

	public static long getPackageSize(PackageInfo packageinfo)
	{
		String s = packageinfo.applicationInfo.publicSourceDir;
		return (new File(s)).length();
	}

	public static void startInstalledAppDetailsAction(String pkgname, Context context)
	{
		Intent intent = new Intent();
		int i = android.os.Build.VERSION.SDK_INT;
		if (i >= 9)
		{
			Uri uri = Uri.fromParts("package", pkgname, null);
			intent.setData(uri);
		} else
		{
			String s1;
			if (i == 8)
				s1 = "pkg";
			else
				s1 = "com.android.settings.ApplicationPkgName";
			intent.setAction("android.intent.action.VIEW");
			intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
			intent.putExtra(s1, pkgname);
		}
		context.startActivity(intent);
	}
}
