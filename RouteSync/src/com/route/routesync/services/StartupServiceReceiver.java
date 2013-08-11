package com.route.routesync.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.route.routesync.LocationCapture;
import com.route.routesync.RouteActivity;

public class StartupServiceReceiver extends BroadcastReceiver {
	private LocationCapture myLocationCapture;
	@Override
	public void onReceive(Context context, Intent intent) {
		//Log.d("Detector", "Auto Start" + AppLockerPreference.getInstance(context).isAutoStart());
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
		{
			//context.startActivity(new Intent(context, RouteActivity.class));
			//myLocationCapture =new LocationCapture(context);
			//myLocationCapture.startLocationCapture();


		}
	}
}
