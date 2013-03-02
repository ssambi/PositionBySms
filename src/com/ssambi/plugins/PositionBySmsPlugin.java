package com.ssambi.plugins;

import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.util.Log;

import com.ssambi.services.SmsListenerService;
import com.phonegap.api.Plugin;

public class PositionBySmsPlugin extends Plugin {
	private static final String TAG = "PositionBySmsPlugin";

	public static final String START_ACTION = "START";
	public static final String STOP_ACTION = "STOP";

	@Override
	public PluginResult execute(String action, JSONArray data, String callbackId) {
		Log.d(TAG, "Plugin Called, action: " + action);

		PluginResult result = null;

		if (START_ACTION.equals(action)) {

			try {
				JSONArray jObj = data.getJSONArray(0);
				String acceptedPhoneNumbers[] = new String[jObj.length()];
				for (int i = 0; i < jObj.length(); i++) {
					acceptedPhoneNumbers[i] = jObj.getString(i);
				}

				Intent intent = new Intent(this.ctx.getApplicationContext(),
						SmsListenerService.class);
				intent.putExtra(SmsListenerService.ACCEPTED_PHONENUMBERS_KEY,
						acceptedPhoneNumbers);
				this.ctx.getApplicationContext().startService(intent);

				result = new PluginResult(Status.OK, new JSONArray());
			} catch (JSONException e) {
				Log.d(TAG, "Got JSON Exception " + e.getMessage());

				result = new PluginResult(Status.JSON_EXCEPTION);
			}
		} else if (STOP_ACTION.equals(action)) {

			Intent intent = new Intent(this.ctx.getApplicationContext(),
					SmsListenerService.class);
			this.ctx.getApplicationContext().stopService(intent);
			result = new PluginResult(Status.OK, new JSONArray());
		}

		else {

			result = new PluginResult(Status.INVALID_ACTION);
			Log.d(TAG, "Invalid action : " + action + " passed");

		}

		return result;

	}

}
