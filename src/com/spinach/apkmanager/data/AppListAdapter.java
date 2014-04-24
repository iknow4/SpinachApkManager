

package com.spinach.apkmanager.data;

import android.content.Context;
import android.content.pm.*;
import android.content.res.Resources;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;
import com.spinach.apkmanager.R;





public class AppListAdapter extends MainListViewAdapter
{

	private Context ctx;
	private ArrayList mList;
	public AppListAdapter(Context context, ArrayList arraylist)
	{
		super(context, arraylist);
		ctx = context ;
		mList = arraylist;
	}

	public View getView(int position, View convertView, ViewGroup viewgroup)
	{
		MainListViewAdapter.ViewHolder viewholder;
		android.graphics.drawable.Drawable drawable;
		if (convertView == null)
		{
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_list_item, null);
			
			viewholder = new MainListViewAdapter.ViewHolder();
			
			viewholder.iconImageView = (ImageView)convertView.findViewById(R.id.app_icon);
			
			viewholder.nameTextView = (TextView)convertView.findViewById(R.id.app_name);
			
			viewholder.versionTextView = (TextView)convertView.findViewById(R.id.app_version);
			
			viewholder.memTextView = (TextView)convertView.findViewById(R.id.app_mem);

			viewholder.installTextView = (TextView)convertView.findViewById(R.id.app_install);
			
			viewholder.checkBox = (CheckBox)convertView.findViewById(R.id.app_chk);
			
			convertView.setTag(viewholder);
		} else
		{
			viewholder = (MainListViewAdapter.ViewHolder)convertView.getTag();
		}
		Log.i("AppListAdapter", "AppListAdapter getView i = "+position);

	if((getMList()!=null&&getMList().size()>0)||(mList.size()>0)){
		Log.i("AppListAdapter", "getMList()!=null&&getMList().size()>0  11111111111111111111");
		AApplication aapplication = (AApplication)getMList().get(position);
		Log.i("AppListAdapter", "getMList()!=null&&getMList().size()>0  22222222222222222222");
		viewholder.iconImageView.setImageDrawable(aapplication.getIcon());
		viewholder.nameTextView.setText(aapplication.getName());

		StringBuffer stringbuffer = new StringBuffer(ctx.getResources().getString(R.string.version_name));
		String str = stringbuffer.append(aapplication.getVersionName()).toString();
		viewholder.versionTextView.setText(str);
		viewholder.memTextView.setText(aapplication.getSize());
		viewholder.checkBox.setChecked(aapplication.isSelected());
		if(aapplication.isInstalled())
			viewholder.installTextView .setText(R.string.installed_text);
		else
			viewholder.installTextView .setText("");
	 }else
	 {
	 	Log.i("AppListAdapter", "getMList().size() = "+getMList().size());
		Log.i("AppListAdapter", "getMList() = "+getMList());
	 }
	   return convertView;
	}
}
