package com.spinach.apkmanager.feedback;

import java.util.ArrayList;

import android.content.Context;
import android.telephony.SmsManager;
import com.android.internal.telephony.ITelephony;
import android.widget.Toast;
import android.util.Log;

public class FeedbackAction {
	
	private Context mContext = null;
	private static final int SUCCESS = 0;
	private static final int FAILURE = 1;
	private static final String AuthorPhoneNumber= "13671924552";//feedback message to me
	private String Messagetag ="意见反馈:";
	private static final String TAG = "FeedbackAction";
	
	public FeedbackAction(Context context) {
		mContext = context;
	}

	/**
	 * 此处用于将反馈信息发送给服务器
	 * @param content
	 * @param contact
	 * @return
	 */
	public int sendFeedbackMessage(String content, String destinationAddress) {
	Log.i(TAG, "sendFeedbackMessage.....");
	content = Messagetag+content;
	destinationAddress = AuthorPhoneNumber;
	SmsManager manager_sms = SmsManager.getDefault();
	 ArrayList<String> texts = manager_sms.divideMessage(content);
	for(String text : texts)
	{
		Log.i(TAG, "sendFeedbackMessage.....divideMessage.....");
		manager_sms.sendTextMessage(destinationAddress, null, text, null, null);
	}
		return SUCCESS;
	}

}
