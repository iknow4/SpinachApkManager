// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileOperator.java

package com.spinach.apkmanager.util;

import android.content.Context;
import android.os.*;
import android.os.storage.StorageManager;

import com.spinach.apkmanager.data.AApplication;
import android.os.storage.StorageVolume;
import java.io.*;
import java.util.ArrayList;
import android.util.Log;

public class FileOperator
{

	private ArrayList apkFilesList;
	private ArrayList retList = null;
	private ArrayList backupApkFileList;
	private Context mContext;
	private StorageManager mStorageManager = null;
	private String sdcardPath = null;//"/storage/sdcard1";

	public FileOperator()
	{
		//if (mStorageManager == null) {
            		//mStorageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);

	}

	public FileOperator(Context context)
	{
		mContext = context;
		if (mStorageManager == null) 
            		mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);

		

	}
	/**
	 * @param args
	 * scan sd card file
	 */	
	private void getFiles(File  root, Handler handler, ArrayList arraylist)
	{
		//Log.i("FileOperator", "root ="+ root);
		File files[] =  root.listFiles();
		//Log.i("FileOperator", "files ="+ files);
		if(files == null)
			return;

		for(File f:files)
		{
			//Log.i("FileOperator", "f ="+f);
			if (f.isDirectory()){
				getFiles(f, handler, arraylist);
			}else{		
				if (f.getName().toLowerCase().endsWith(".apk"))
				{
					//Log.i("FileOperator", "FileOperator getFiles.......");
					AApplication aapplication = new AApplication(f, mContext);
					aapplication.readAttribution();
					//Log.i("FileOperator", "aapplication.pkgname = "+aapplication.getPackageName());
					//Log.i("FileOperator", "aapplication.isInstalled() = "+aapplication.isInstalled());
					if (aapplication.getIcon() != null && aapplication.getName() != null && aapplication.getPackageName() != null && !aapplication.getName().equals("") && !aapplication.getPackageName().equals(""))
					{
						String s2 = aapplication.getName();
						Message message = handler.obtainMessage();
						message.what = Utils.HANDLER_SCAN_APK_PROGRESS_MSG;
						message.obj = s2;
						handler.sendMessage(message);
						arraylist.add(aapplication);			
					}
				}
		    }
		
	
		}
		retList = arraylist;
	}
	
	private void getFiles_ext(String path, Handler handler, ArrayList arraylist)
	{
		File file;
		File afile[] = (new File(path)).listFiles();
		if(afile == null)
			return;
		int l = afile.length;
		for(int i=0;i<l; i++ )
		{
			
			file = afile[i];
			if (!file.isDirectory())
					break; 
			if (!file.getName().toLowerCase().endsWith(".apk"))
			{
				String s1 = file.getPath();
				//getFiles(s1, handler, arraylist);
			}
			else
			{
				AApplication aapplication = new AApplication(file, mContext);
				aapplication.readAttribution();
				if (aapplication.getIcon() != null && aapplication.getName() != null && aapplication.getPackageName() != null && !aapplication.getName().equals("") && !aapplication.getPackageName().equals(""))
				{
					String s2 = aapplication.getName();
					Message message = handler.obtainMessage();
					message.what = Utils.HANDLER_SCAN_APK_PROGRESS_MSG;
					message.obj = s2;
					handler.sendMessage(message);
					arraylist.add(aapplication);			
				}
			}
			
		
	
		}
	}

	public void checkBackupFolderExist()
	{
		File file = new File("/mnt/sdcard/sunmontech");
		if (!file.exists())
			file.mkdir();
		File file1 = new File("/mnt/sdcard/sunmontech/backup");
		if (!file1.exists())
			file1.mkdir();
	}

	public void copyFile(File file, File file1)
		throws IOException
	{
		FileInputStream fileinputstream = new FileInputStream(file);
		FileOutputStream fileoutputstream = new FileOutputStream(file1);
		byte abyte0[] = new byte[0x200000];
		do
		{
			int i = fileinputstream.read(abyte0);
			if (i == -1)
			{
				fileinputstream.close();
				fileoutputstream.flush();
				fileoutputstream.close();
				return;
			}
			fileoutputstream.write(abyte0, 0, i);
		} while (true);
	}

	public boolean deleteFile(File file)
	{
		return file.delete();
	}

	public ArrayList scanAllApkFile(Handler handler)
	{
		boolean flag = Environment.getExternalStorageState().equals("mounted");
		Log.e("FileOperator", "scanAllApkFile  flag = "+flag);
		if (!flag)
		{
			apkFilesList = null;
		} else
		{
			/*jason add for second sd card*/
			StorageVolume[] volumes = mStorageManager.getVolumeList();
	        	if (volumes != null && volumes.length > 1) {
	            		sdcardPath = volumes[1].getPath();           
	        	}else
				sdcardPath = volumes[0].getPath();//Environment.getExternalStorageDirectory().getPath();
			
			Log.v("FileOperator", "scanAllApkFile  Path0 = "+volumes[0].getPath());
			Log.v("FileOperator", "scanAllApkFile  Path1 = "+volumes[1].getPath());
			if (apkFilesList == null)
			{
				
				apkFilesList = new ArrayList();
				
			} else
			{
				try {  
	                Thread.sleep(500);  
	            } catch (InterruptedException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            }  	
				apkFilesList.clear();
			}
			getFiles(new File(sdcardPath), handler, apkFilesList);
			
		}
		return retList;
	}

/*	public ArrayList scanAllApkFileBackup(Handler handler)
	{
		boolean flag = Environment.getExternalStorageState().equals("mounted");
		ArrayList arraylist;
		if (!flag)
		{
			arraylist = null;
		} else
		{
			String s = "/mnt/sdcard/sunmontech/backup";
			arraylist = backupApkFileList;
			if (arraylist == null)
			{
				arraylist = new ArrayList();
				backupApkFileList = arraylist;
			} else
			{
				ArrayList arraylist1 = backupApkFileList;
				arraylist1.clear();
			}
			arraylist = backupApkFileList;
			//getFiles(s, handler, arraylist);
			getFiles(new File("/mnt/sdcard/sunmontech/backup"),handler, arraylist);
			
			
		}
		return arraylist;
	}*/
}
