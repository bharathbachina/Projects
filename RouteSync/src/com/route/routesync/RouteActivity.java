package com.route.routesync;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

public class RouteActivity extends Activity {

	private Button btnstart,btnstop;
	LocationManager myLocationManager;
	LocationListener myLocationListener; 
	private LocationCapture myLocationCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnstart	=(Button)findViewById(R.id.start);
        btnstop		=(Button)findViewById(R.id.stop);
        myLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	myLocationListener = new MyLocationListener();
        btnstart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
				 if(!myLocationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER ))
				 {
					 displayalert();	
				 }
				 else
				 {	
					 myLocationCapture=new LocationCapture(RouteActivity.this);
					 myLocationCapture.startLocationCapture();
					 
				 }
				
			}
		});
        
      
    }
    
	public class MyLocationListener implements LocationListener
	{

		public void onLocationChanged(Location argLocation) 
		{
		}
		public void onProviderDisabled(String provider) {}
		public void onProviderEnabled(String provider) {
			 
		}
		public void onStatusChanged(String provider, int status, Bundle arg2) {}
	};
	
	private void displayalert() 
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Please enable GPS").setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent myIntent = new Intent( Settings.ACTION_SECURITY_SETTINGS );
				startActivity(myIntent);
			}
		});
		builder.show();
	}

}
