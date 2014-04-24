

package com.spinach.apkmanager.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.*;

public class TabSwitcher extends FrameLayout
{
	public interface OnItemClickLisener
	{

		public abstract void onItemClickLisener(View view, int i);
	}


	private static final String TAG = "TabSwitcher";
	private int arrayId;
	private Context context;
	private ImageView imageView;
	private int imageView_width;
	private LinearLayout layout;
	android.view.View.OnClickListener listener;
	private int oldPosition;
	private OnItemClickLisener onItemClickLisener;
	private android.widget.LinearLayout.LayoutParams params;
	private int selectedPosition;
	private TextView textViewArray[];
	private String texts[];
	android.view.View.OnTouchListener touchListener;

	public TabSwitcher(Context context1)
	{
		super(context1);
		selectedPosition = 0;
		int i = selectedPosition;
		oldPosition = i;
		mlisener listener = new mlisener();

		mTouchListener touchListener = new mTouchListener();
		
		init();
	}

	public TabSwitcher(Context context1, AttributeSet attributeset)
	{
		super(context1, attributeset);
		selectedPosition = 0;
		int i = selectedPosition;
		oldPosition = i;
		mlisener listener = new mlisener();

		mTouchListener touchListener = new mTouchListener();
	
		Log.v("TabSwitcher", "--------------------TabSwitcher--------------------");
		init();
		//int ai[] = com.spinach.apkmanager.activity.R.styleable.custom;
		//TypedArray typedarray = context1.obtainStyledAttributes(attributeset, ai);
		//int j = typedarray.getResourceId(0, 0);
		//arrayId = j;
		//typedarray.recycle();
	}

	private void doAnimation()
	{
		Log.v("TabSwitcher", "--------------------doAnimation--------------------");
		int i = selectedPosition;
		int j = imageView_width;
		int k = i * j;
		int l = imageView.getTop();
		int i1 = selectedPosition;
		int j1 = imageView_width;
		int k1 = i1 * j1;
		int l1 = imageView.getWidth();
		int i2 = k1 + l1;
		int j2 = imageView.getBottom();
		String s = String.valueOf(k);
		String s1 = (new StringBuilder(s)).append(",").append(l).append(",").append(i2).append(",").append(j2).toString();
		Log.v("TabSwitcher", s1);
		imageView.layout(k, l, i2, j2);
		int k2 = oldPosition;
		int l2 = selectedPosition;
		int i3 = k2 - l2;
		int j3 = imageView_width;
		float f = i3 * j3;
		TranslateAnimation translateanimation = new TranslateAnimation(f, 0, 0, 0);
		LinearInterpolator linearinterpolator = new LinearInterpolator();
		translateanimation.setInterpolator(linearinterpolator);
		translateanimation.setDuration(300L);
		translateanimation.setFillAfter(true);
		imageView.startAnimation(translateanimation);
	}

	private void init()
	{
		Log.v("TabSwitcher", "--------------------init--------------------");
		Context context1 = getContext();
		context = context1;
		android.widget.FrameLayout.LayoutParams layoutparams = new android.widget.FrameLayout.LayoutParams(-1, -1);
		setLayoutParams(layoutparams);
		setBackgroundResource(0x7f020043);
	}

	private void setBestPosition()
	{
		Log.v("TabSwitcher", "setBestPosition");
		int i = imageView.getLeft();
		float f = i;
		float f1 = 0x3f800000 * f;
		float f2 = imageView_width;
		int j = Math.round(f1 / f2);
		selectedPosition = j;
		int k = selectedPosition;
		int l = imageView_width;
		int i1 = k * l;
		ImageView imageview = imageView;
		int j1 = selectedPosition;
		int k1 = imageView_width;
		int l1 = j1 * k1;
		int i2 = imageView.getTop();
		int j2 = selectedPosition;
		int k2 = imageView_width;
		int l2 = j2 * k2;
		int i3 = imageView.getWidth();
		int j3 = l2 + i3;
		int k3 = imageView.getBottom();
		imageview.layout(l1, i2, j3, k3);
		float f3 = i - i1;
		TranslateAnimation translateanimation = new TranslateAnimation(f3, 0, 0, 0);
		LinearInterpolator linearinterpolator = new LinearInterpolator();
		translateanimation.setInterpolator(linearinterpolator);
		translateanimation.setDuration(300L);
		translateanimation.setFillAfter(true);
		imageView.startAnimation(translateanimation);
	}

	protected void onFinishInflate()
	{
		super.onFinishInflate();
		Log.v("TabSwitcher", "--------------------onFinishInflate--------------------");
		TextView atextview[];
		if (arrayId != 0)
		{
			Resources resources = getResources();
			int i = arrayId;
			String as[] = resources.getStringArray(i);
			texts = as;
		} else
		{
			String as1[] = new String[0];
			texts = as1;
		}
		atextview = new TextView[texts.length];
		textViewArray = atextview;
	}

	protected void onSizeChanged(int i, int j, int k, int l)
	{
		boolean flag = true;
		super.onSizeChanged(i, j, k, l);
		Log.v("TabSwitcher", "--------------------onSizeChanged--------------------");
		int i1 = selectedPosition;
		int j1 = texts.length;
		int k1;
		//k1--;
		if (i1 > j1)
			throw new IllegalArgumentException("The selectedPosition can't be > texts.length.");
		Context context1 = context;
		LinearLayout linearlayout = new LinearLayout(context1);
		layout = linearlayout;
		int l1 = getMeasuredHeight();
		android.widget.LinearLayout.LayoutParams layoutparams = new android.widget.LinearLayout.LayoutParams(-1, l1);
		params = layoutparams;
		params.weight = 0x3f800000;
		params.gravity = 16;
		int i2 = 0;
		do
		{
			int j2 = texts.length;
			if (i2 >= j2)
			{
				int k2 = selectedPosition;
				oldPosition = k2;
				int l2 = getMeasuredWidth();
				int i3 = texts.length;
				int j3 = l2 / i3;
				imageView_width = j3;
				int k3 = imageView_width;
				int l3 = getMeasuredHeight();
				android.widget.LinearLayout.LayoutParams layoutparams1 = new android.widget.LinearLayout.LayoutParams(k3, l3);
				Context context2 = context;
				ImageView imageview = new ImageView(context2);
				imageView = imageview;
				imageView.setBackgroundResource(0x7f020044);
				imageView.setClickable(flag);
				ImageView imageview1 = imageView;
				addView(imageview1, layoutparams1);
				LinearLayout linearlayout1 = layout;
				android.widget.LinearLayout.LayoutParams layoutparams2 = params;
				addView(linearlayout1, layoutparams2);
				return;
			}
			Context context3 = context;
			TextView textview = new TextView(context3);
			Integer integer = Integer.valueOf(i2);
			textview.setTag(integer);
			String s = texts[i2];
			textview.setText(s);
			textview.setTextSize(0x41800000);
			textview.setTextColor(0xff000000);
			textview.setGravity(17);
			android.view.View.OnClickListener onclicklistener = listener;
			textview.setOnClickListener(onclicklistener);
			int i4 = selectedPosition;
			LinearLayout linearlayout2;
			android.widget.LinearLayout.LayoutParams layoutparams3;
			if (i2 == i4)
				textview.setClickable(false);
			else
				textview.setClickable(flag);
			textViewArray[i2] = textview;
			linearlayout2 = layout;
			layoutparams3 = params;
			linearlayout2.addView(textview, layoutparams3);
			i2++;
		} while (true);
	}

	public void setOnItemClickLisener(OnItemClickLisener onitemclicklisener)
	{
		Log.v("TabSwitcher", "setOnItemClickLisener");
		onItemClickLisener = onitemclicklisener;
	}

	public void setTexts(String as[])
	{
		Log.v("TabSwitcher", "setTexts");
		texts = as;
	}









	private class mlisener implements android.view.View.OnClickListener
	{

		public void onClick(View view)
		{
			Log.v("TabSwitcher", "OnClickListener");
			StringBuilder stringbuilder = new StringBuilder("selectedPosition=");
			int i = selectedPosition;
			StringBuilder stringbuilder1 = stringbuilder.append(i).append(" oldPosition=");
			int j = oldPosition;
			String s = stringbuilder1.append(j).toString();
			Log.v("TabSwitcher", s);
			TabSwitcher tabswitcher = TabSwitcher.this;
			int k = ((Integer)view.getTag()).intValue();
			tabswitcher.selectedPosition = k;
			StringBuilder stringbuilder2 = new StringBuilder("selectedPosition=");
			int l = selectedPosition;
			StringBuilder stringbuilder3 = stringbuilder2.append(l).append(" oldPosition=");
			int i1 = oldPosition;
			String s1 = stringbuilder3.append(i1).toString();
			Log.v("TabSwitcher", s1);
			TextView atextview[] = textViewArray;
			int j1 = selectedPosition;
			if (atextview[j1].isClickable())
			{
				TextView atextview1[] = textViewArray;
				int k1 = oldPosition;
				atextview1[k1].setClickable(true);
				TextView atextview2[] = textViewArray;
				int l1 = selectedPosition;
				atextview2[l1].setClickable(false);
				doAnimation();
				TabSwitcher tabswitcher1 = TabSwitcher.this;
				int i2 = selectedPosition;
				tabswitcher1.oldPosition = i2;
				if (onItemClickLisener != null)
				{
					OnItemClickLisener onitemclicklisener = onItemClickLisener;
					int j2 = selectedPosition;
					onitemclicklisener.onItemClickLisener(view, j2);
				}
			}
		}

		mlisener()
		{
			super();
		}
	}


	private class mTouchListener implements android.view.View.OnTouchListener
	{

		int temp[];
		public boolean onTouch(View view, MotionEvent motionevent)
		{
		/*	boolean flag;
			int i;
			int l;
			flag = false;
			Log.v("TabSwitcher", "OnTouchListener");
			i = motionevent.getAction();
			int j = (int)motionevent.getRawX();
			int k = getLeft();
			l = j - k;
			i;
			JVM INSTR tableswitch 0 2: default 68
		//		               0 70
		//		               1 218
		//		               2 96;
			   goto _L1 _L2 _L3 _L4
_L1:
			return flag;
_L2:
			int ai[] = temp;
			int i1 = (int)motionevent.getX();
			ai[flag] = i1;
			view.postInvalidate();
			continue; 
_L4:
			int j1 = temp[flag];
			int k1 = l - j1;
			int l1 = view.getWidth();
			int i2 = k1 + l1;
			int j2 = 0;
			int k2 = view.getHeight();
			int l2 = j2 + k2;
			if (k1 < 0)
			{
				k1 = 0;
				int i3 = view.getWidth();
				i2 = k1 + i3;
			}
			int j3 = getMeasuredWidth();
			if (i2 > j3)
			{
				i2 = getMeasuredWidth();
				int k3 = view.getWidth();
				k1 = i2 - k3;
			}
			view.layout(k1, j2, i2, l2);
			view.postInvalidate();
			continue; 
_L3:
			TextView atextview[] = textViewArray;
			int l3 = oldPosition;
			atextview[l3].setClickable(true);
			setBestPosition();
			int i4 = oldPosition;
			int j4 = selectedPosition;
			if (i4 != j4 && onItemClickLisener != null)
			{
				OnItemClickLisener onitemclicklisener = onItemClickLisener;
				TextView atextview1[] = textViewArray;
				int k4 = selectedPosition;
				TextView textview = atextview1[k4];
				int l4 = selectedPosition;
				onitemclicklisener.onItemClickLisener(textview, l4);
			}
			TabSwitcher tabswitcher = TabSwitcher.this;
			int i5 = selectedPosition;
			tabswitcher.oldPosition = i5;
			TextView atextview2[] = textViewArray;
			int j5 = selectedPosition;
			atextview2[j5].setClickable(flag);
			if (true) goto _L1; else goto _L5
_L5: */
			return false;
		}

		mTouchListener()
		{
			super();
			int ai[] = new int[2];
			temp = ai;
		}
	}

}
