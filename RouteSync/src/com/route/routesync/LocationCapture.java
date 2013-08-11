package com.route.routesync;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.route.routesync.common.ApplicationConstants;
import com.route.routesync.services.LocationSync;

public class LocationCapture{
	
	LocationManager myLocationManager;
	CurrentLocationListener myLocationListener; 
	private boolean hasLocation = false;
	GPSListener objGPSListener;
	private Context mContext;
	private LocationControl locationControlTask;
	
	public LocationCapture(Context ctx)
	{
		mContext=ctx;
		System.out.println("Cntext::::::::"+mContext);
		Toast.makeText(mContext, "Enter locationcapture...",Toast.LENGTH_LONG).show();
		
		myLocationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
		Toast.makeText(mContext, "Location manager initilized...",Toast.LENGTH_LONG).show();
		
		myLocationListener = new CurrentLocationListener();
		//objGPSListener=(GPSListener)mContext;
		
	
	}
	
	public void startLocationCapture()
	{
		try
		{
			Toast.makeText(mContext, "enter into start location...",Toast.LENGTH_LONG).show();
			hasLocation=false;
			myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
			Toast.makeText(mContext, "enter into request for location updates...",Toast.LENGTH_LONG).show();
			locationControlTask = new LocationControl();
			Toast.makeText(mContext, "location control reached...",Toast.LENGTH_LONG).show();
			locationControlTask.execute();
			
		}catch(Exception e){Toast.makeText(mContext, "Error:::..."+e.getMessage(),Toast.LENGTH_LONG).show();System.out.println("Error::::::"+e.getMessage());}
	}
	
	
	  @SuppressLint("NewApi")
	  public class LocationControl extends AsyncTask<Context, Void, Void>
	  {
		  private final ProgressDialog dialog = new ProgressDialog(mContext);

		  @SuppressWarnings("deprecation")
		  protected void onPreExecute()
		  {
			  this.dialog.setMessage("Please wait...");
			  this.dialog.setButton("Cancel",new DialogInterface.OnClickListener() 
			  {
				  public void onClick(DialogInterface dialog, int which) 
				  {             	
	                	dialog.dismiss();
	                	hasLocation=true;
				  }
			  });
			  this.dialog.show();
		  }

		  protected Void doInBackground(Context... params)
		  {
			  while (!hasLocation) {
				  try {
					  Thread.sleep(Toast.LENGTH_LONG);
				  } catch (InterruptedException e) {
						e.printStackTrace();
				  }
			  };
			  return null;
		  }
		  protected void onPostExecute(final Void unused)
		  {
			  if(this.dialog.isShowing())
			  {
				  this.dialog.dismiss();
			  }
					
			  if (ApplicationConstants.getInstance().getDbllatitude() !=0.0&& ApplicationConstants.getInstance().getDbllongitude()!=0.0)
			  {
				  mContext.startService(new Intent(mContext,LocationSync.class));
			  }
			  else
			  {
				  displayalert1();
			  }
			}
		  
		}
	
	public class CurrentLocationListener implements LocationListener
	{
		
		public void onLocationChanged(Location argLocation) 
		{
			
			if(argLocation != null)
			{	
				ApplicationConstants.getInstance().setDbllatitude(argLocation.getLatitude());
				ApplicationConstants.getInstance().setDbllongitude(argLocation.getLongitude()); 
				hasLocation=true;
			}
              
		}
		public void onProviderDisabled(String provider) {}
		public void onProviderEnabled(String provider) {
			 
		}
		public void onStatusChanged(String provider, int status, Bundle arg2) {}
		
	};
	

	public void displayalert1()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("Location not found do you want to start again...").setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				hasLocation=false;
				locationControlTask = new LocationControl();
				locationControlTask.execute();
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				
			}
		});
		builder.show();
	}

}
