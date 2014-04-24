
package com.spinach.apkmanager.data;


import android.content.Context;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import com.spinach.apkmanager.R;



public class MoveListAdapter extends MainListViewAdapter
{

	public MoveListAdapter(Context context, ArrayList arraylist)
	{
		super(context, arraylist);
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		MainListViewAdapter.MoveItemViewHolder moveitemviewholder;
		AApplication aapplication;
		android.graphics.drawable.Drawable drawable;
		String s;
		String s1;
		String s2;
		TextView textview3;
		String s3;
		if (view == null)
		{
			view = LayoutInflater.from(getContext()).inflate(R.layout.move_list_item, null);
			moveitemviewholder = new MainListViewAdapter.MoveItemViewHolder();
			ImageView imageview = (ImageView)view.findViewById(R.id.app_move_icon);
			moveitemviewholder.iconImageView = imageview;
			TextView textview = (TextView)view.findViewById(R.id.app_move_name);
			moveitemviewholder.nameTextView = textview;
			TextView textview1 = (TextView)view.findViewById(R.id.app_move_version);
			moveitemviewholder.versionTextView = textview1;
			TextView textview2 = (TextView)view.findViewById(R.id.app_move_mem);
			moveitemviewholder.memTextView = textview2;
			view.setTag(moveitemviewholder);
		} 
		else
		{
			moveitemviewholder = (MainListViewAdapter.MoveItemViewHolder)view.getTag();
		}
		aapplication = (AApplication)getMList().get(i);
		drawable = aapplication.getIcon();
		s = aapplication.getName();
		s1 = aapplication.getVersionName();
		s2 = aapplication.getSize();
		moveitemviewholder.iconImageView.setImageDrawable(drawable);
		moveitemviewholder.nameTextView.setText(s);
		textview3 = moveitemviewholder.versionTextView;
		s3 = (new StringBuilder("锟�")).append(s1).toString();
		textview3.setText(s3);
		moveitemviewholder.memTextView.setText(s2);
		return view;
	}
}
