

package com.spinach.apkmanager.data;

import android.content.Context;
import android.content.pm.*;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import com.spinach.apkmanager.util.Utils;

import java.io.File;
import java.lang.reflect.*;
import android.util.Log;
import com.spinach.apkmanager.R;

public class AApplication
{

	private Context context;
	private String createTime;
	private File file;
	private Drawable icon;
	private boolean isInstalled;
	private boolean isSelected;
	private int location;
	private String name;
	private String packageName;
	private String path;
	private String size;
	private long sizeValue;
	private String versionName;

	public AApplication()
	{
		isInstalled = false;
		isSelected = false;
	}

	public AApplication(File file1, Context context1)
	{
		isInstalled = false;
		isSelected = false;
		file = file1;
		context = context1;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public File getFile()
	{
		return file;
	}

	public Drawable getIcon()
	{
		return icon;
	}

	public int getLocation()
	{
		return location;
	}

	public String getName()
	{
		return name;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public String getPath()
	{
		return path;
	}

	public String getSize()
	{
		return size;
	}

	public long getSizeValue()
	{
		return sizeValue;
	}

	public String getVersionName()
	{
		return versionName;
	}

	public boolean isInstalled()
	{
		return isInstalled;
	}

	public boolean isSelected()
	{
		return isSelected;
	}

	public void readAttribution()
	{

		Log.i("AApplication", "FileOperator readAttribution.......");

	try{
		String apkPath = file.getAbsolutePath();
		Class class1 = Class.forName("android.content.pm.PackageParser");
		Class aclass[] = new Class[1];
		aclass[0] = java.lang.String.class;
		Constructor constructor = class1.getConstructor(aclass);
		Object obj = constructor.newInstance(apkPath);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		displaymetrics.setToDefaults();
		Class aclass2[] = new Class[4];
		aclass2[0] = java.io.File.class;
		aclass2[1] = java.lang.String.class;
		aclass2[2] = android.util.DisplayMetrics.class;
		aclass2[3] = Integer.TYPE;
		Method method = class1.getDeclaredMethod("parsePackage", aclass2);
		Object aobj2[] = new Object[4];
		File file2 =  new File(apkPath);
	
		aobj2[0] = file2;
		aobj2[1] = apkPath;
		aobj2[2] = displaymetrics;
		aobj2[3] = Integer.valueOf(0);
		Object obj2 = method.invoke(obj, aobj2);
		Field field = obj2.getClass().getDeclaredField("applicationInfo");
		
		ApplicationInfo applicationinfo = (ApplicationInfo)field.get(obj2);
		Class assetMagCls = Class.forName("android.content.res.AssetManager");
		
		Constructor constructor2 = assetMagCls.getConstructor( null);
		
		Object obj5 = constructor2.newInstance((Object[])null);
		Class c[] = new Class[1];
		c[0] = String.class;
	
	
		Method method2 = assetMagCls.getDeclaredMethod("addAssetPath", c);
		Object aobj4[] = new Object[1];
		aobj4[0] = apkPath;
		
		method2.invoke(obj5, aobj4);
		Resources resources = context.getResources();
		Class typeArgs[] = new Class[3];
		typeArgs[0] = obj5.getClass();
		typeArgs[1] = resources.getDisplayMetrics().getClass();
		typeArgs[2] = resources.getConfiguration().getClass();
		Constructor resCt = Resources.class.getConstructor(typeArgs);
		Object aobj6[] = new Object[3];
		aobj6[0] = obj5;
		DisplayMetrics displaymetrics1 = resources.getDisplayMetrics();
		aobj6[1] = displaymetrics1;
		android.content.res.Configuration configuration = resources.getConfiguration();
		aobj6[2] = configuration;
	
		resources = (Resources)resCt.newInstance(aobj6);
		PackageManager pm = context.getPackageManager();
		PackageInfo packageinfo = pm.getPackageArchiveInfo(apkPath, 1);
		
		ApplicationInfo applicationinfo1 = packageinfo.applicationInfo;
	
		if (applicationinfo.labelRes != 0)
		{
			setName(resources.getText(applicationinfo.labelRes).toString());
		} else
		{
			String s = applicationinfo1.name;
			
			if (s == null)
				s = applicationinfo1.packageName;
			else
				s = applicationinfo1.name;
			
			this.setName(s);
		}
		this.setVersionName(packageinfo.versionName);

		this.setPackageName(packageinfo.packageName);
		PackageInfo pkgInfo = null;
		try{

			 //Log.i("AApplication", "~~~~~~~~~~~~~~~~~~~~packageinfo.packageName~~~~~~~~~~ = " + packageinfo.packageName);
			 pkgInfo = pm.getPackageInfo(packageinfo.packageName, 0);
		}catch (NameNotFoundException e)
		{
			 pkgInfo = null; 
			 //Log.i("AApplication", "Can't find plugin: " + packageinfo.packageName);
		}
		 if (pkgInfo == null) {
		 	
                  	//Log.i("AApplication", "Can't find appname: " + packageinfo.packageName);
			this.setInstalled(false);
		 }else{
		 
			this.setInstalled(true);
		 }
		if (applicationinfo.icon != 0)
		{

			Drawable drawable = resources.getDrawable(applicationinfo.icon);
		
			this.setIcon(drawable);
		} else
		{
			Drawable drawable2 = pm.getApplicationIcon(applicationinfo1);
			
			this.setIcon(drawable2);
		}
		long l1 = Utils.getFileSize(apkPath);
		String str = Utils.formatFileSize(l1);
		setSize(str);
		setSizeValue(str.length());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void setCreateTime(String s)
	{
		createTime = s;
	}

	public void setFile(File file1)
	{
		file = file1;
	}

	public void setIcon(Drawable drawable)
	{
		icon = drawable;
	}

	public void setInstalled(boolean flag)
	{
		isInstalled = flag;
	}

	public void setLocation(int i)
	{
		location = i;
	}

	public void setName(String s)
	{
		name = s;
	}

	public void setPackageName(String s)
	{
		packageName = s;
	}

	public void setPath(String s)
	{
		path = s;
	}

	public void setSelected(boolean flag)
	{
		isSelected = flag;
	}

	public void setSize(String s)
	{
		size = s;
	}

	public void setSizeValue(long l)
	{
		sizeValue = l;
	}

	public void setVersionName(String s)
	{
		versionName = s;
	}
}
