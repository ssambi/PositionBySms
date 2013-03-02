package com.ssambi.services;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.ssambi.SendSmsLocationListener;

public class SmsListenerService extends Service {
	private static final String TAG = "SmsListenerService";
	
	public static final String RECEIVED_PHONENUMBERS_KEY = "RECEIVED_PHONENUMBERS_KEY";
	public static final String ACCEPTED_PHONENUMBERS_KEY = "ACCEPTED_PHONENUMBERS_KEY";

	private String acceptedPhonenumbers[];
	
	private LocationManager locManager;
	
	private boolean isStarted = false;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	

	  @Override
	  public void onCreate() {
	    Log.d(TAG, "onCreate");
	  }

	  @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
		  Log.d(TAG, "onStartCommand");
		  
		  
		  Bundle b = intent.getExtras();
		  if (b != null) {
			  
			  if (b.containsKey(ACCEPTED_PHONENUMBERS_KEY)) {
				  this.acceptedPhonenumbers = b.getStringArray(ACCEPTED_PHONENUMBERS_KEY);
				  
				  this.isStarted = true;
			  } else if (this.isStarted && this.acceptedPhonenumbers != null && b.containsKey(RECEIVED_PHONENUMBERS_KEY)) {
				  ArrayList<String> receivedPhoneNumbers = b.getStringArrayList(RECEIVED_PHONENUMBERS_KEY);
				  for (String receivedPhoneNumber : receivedPhoneNumbers) {
					  for (int i = 0; i < this.acceptedPhonenumbers.length; i++) {
						if (receivedPhoneNumber.endsWith(this.acceptedPhonenumbers[i])) {
							findLocation(receivedPhoneNumber);
							break;
						}
					  }
				  }	
			  }
		  }
		 
		  
	      // If we get killed, after returning from here, restart
	      return START_STICKY;
	  }

	  
	  private void findLocation(String receivedPhoneNumber) {
		  Log.d(TAG, "findLocation");
		  
		  this.locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		  
		  LocationListener locationListener = new SendSmsLocationListener(this.getApplicationContext(), receivedPhoneNumber);

		  // Register the listener with the Location Manager to receive location updates
		  locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
		  		
	}


	@Override
	  public void onDestroy() {
		  Log.d(TAG, "onDestroy");
		  
		  this.isStarted = false;
	  }


}
