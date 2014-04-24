
package com.spinach.apkmanager.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.ArrayList;
import com.spinach.apkmanager.R;

public class MainListViewAdapter extends BaseAdapter
{
	public class MoveItemViewHolder
	{

		public ImageView iconImageView;
		public TextView memTextView;
		public TextView nameTextView;
		public TextView versionTextView;

		public MoveItemViewHolder()
		{
			
			super();
		}
	}

	public class ViewHolder
	{

		public CheckBox checkBox;
		public ImageView iconImageView;
		public TextView memTextView;
		public TextView nameTextView;
		public TextView installTextView;
		public TextView versionTextView;

		public ViewHolder()
		{
			super();
		}
	}


	private Context context;
	private ArrayList mList;

	public MainListViewAdapter(Context context1, ArrayList arraylist)
	{
		context = context1;
		mList = arraylist;
	}

	public Context getContext()
	{
		return context;
	}

	public int getCount()
	{
		return mList.size();
	}

	public Object getItem(int i)
	{
		return mList.get(i);
	}

	public long getItemId(int i)
	{
		return (long)i;
	}

	public ArrayList getMList()
	{
		return mList;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		return null;
	}

	public void setContext(Context context1)
	{
		context = context1;
	}

	public void setMList(ArrayList arraylist)
	{
		mList = arraylist;
	}
}
