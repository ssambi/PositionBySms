package com.ssambi.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SmsSenderService extends IntentService {

	public SmsSenderService() {
		super("SmsSenderService");
	}

	public static final String TEXT_KEY = "TEXT_KEY";
	public static final String PHONENUMBER_KEY = "PHONENUMBER_KEY";

	private Handler handler;

	@Override
	public void onCreate() {
		super.onCreate();
		
		handler = new Handler();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String phoneNumber = intent.getExtras().getString(PHONENUMBER_KEY);
		String text = intent.getExtras().getString(TEXT_KEY);

		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(phoneNumber, null, text, null, null);

		final String logText = "send sms to " + phoneNumber + ", text: " + text;
		Log.d("SmsSenderService", logText);
		handler.post(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), logText, Toast.LENGTH_LONG).show();
			}
		});
	}

	

}
