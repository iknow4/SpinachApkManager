
package com.spinach.apkmanager.feedback;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.spinach.apkmanager.R;

public class FeedbackRecordActivity extends ListActivity {

	private ListView mListView = null;

	private ListAdapter mAdapter = null;

	private ImageView mRightBtn = null;
	
	private TextView mTitle = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_feedback);
		initTitle();
		initAdapter();
		initListView();
		addListItem();
	}

	private void initTitle() {
		mRightBtn = (ImageView) findViewById(R.id.right_btn);
		mRightBtn.setImageResource(R.drawable.icon_message);
		mRightBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				FeedbackRecordActivity.this.finish();
			}
		});
		
		mTitle = (TextView) findViewById(R.id.title_name);
		mTitle.setText(R.string.feedback_record);
	}

	// 实际操作应该是从数据库中读取历史反馈记录
	private void addListItem() {
		ListItem item = new ListItem();
		item.mContent = getResources().getString(R.string.feedback_content_hint);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		item.mDateTime = sdf.format(new Date());
		mAdapter.getItems().add(item);
		
		ListItem item2 = new ListItem();
		item2.mContent = getResources().getString(R.string.feedback_content_hint);
		item2.mDateTime = sdf.format(new Date());
		mAdapter.getItems().add(item2);
		mAdapter.notifyDataSetChanged();
	}

	private void initListView() {
		mListView = getListView();
		mListView.setAdapter(mAdapter);
	}

	private void initAdapter() {
		mAdapter = new ListAdapter(this);
	}

}
