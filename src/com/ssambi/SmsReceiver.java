package com.ssambi;

import java.util.ArrayList;

import com.ssambi.services.SmsListenerService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();   
        
        if (bundle != null) {
        	ArrayList<String> phonenumbers = new ArrayList<String>();


            Object[] pdus = (Object[]) bundle.get("pdus");
           
            for (int i=0; i<pdus.length; i++){
                SmsMessage sms = SmsMessage.createFromPdu((byte[])pdus[i]);                
                
                phonenumbers.add(sms.getOriginatingAddress());
            }
            
            if (!phonenumbers.isEmpty()) {
	            Intent serviceIntent = new Intent(context, SmsListenerService.class);
	            serviceIntent.putStringArrayListExtra(SmsListenerService.RECEIVED_PHONENUMBERS_KEY, phonenumbers);
	            context.startService(serviceIntent);
            }
        }   

	}

}
