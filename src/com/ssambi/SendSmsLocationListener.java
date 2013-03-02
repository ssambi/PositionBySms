package com.ssambi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.ssambi.services.SmsSenderService;

public class SendSmsLocationListener implements LocationListener {
	
	private Context context;
	private String phoneNumber;

	public SendSmsLocationListener(Context context, String phoneNumber) {
		this.context = context;
		this.phoneNumber = phoneNumber;
	}
	
	public void onLocationChanged(Location location) {
  	  Log.d("SendSmsLocationListener", "location found, accuracy " + location.getAccuracy());
  	  
  	  if (location.getAccuracy() < 100) {
  		  
  		  sendSms(location);
  		  
  		  LocationManager locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
  		  locManager.removeUpdates(this);
  	  }
    }

    private void sendSms(Location location) {
		String text = getText(location);
    	
		Intent intent = new Intent(this.context, SmsSenderService.class);
		intent.putExtra(SmsSenderService.PHONENUMBER_KEY, this.phoneNumber);
		intent.putExtra(SmsSenderService.TEXT_KEY, text);
		this.context.startService(intent);
		
	}
    
    private String getText(Location loc) {
    	Date d = new Date(loc.getTime());
    	    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	DecimalFormat locFormat = new DecimalFormat("0.00000");
    	
		return "date: " + dateFormat.format(d)
				+ "\nlat: " + locFormat.format(loc.getLatitude())
				+ "\nlng: " + locFormat.format(loc.getLongitude())
				+ "\nspeed: " + (loc.getSpeed() * 3.6) + "km/h"
				+ "\nerror: " + loc.getAccuracy() + "m";
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {}

    public void onProviderEnabled(String provider) {}

    public void onProviderDisabled(String provider) {}
}
