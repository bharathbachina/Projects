package com.route.routesync.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.route.routesync.common.ApplicationConstants;
import com.route.routesync.webservice.UrlPost;

public class LocationSync extends Service{

	private boolean isLocationServiceInterrupt=true;
	private UrlPost objpost;
	private final IBinder mIbinder = new SkyedexBinder();
	final Handler handler = new Handler ();
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mIbinder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private class SkyedexBinder extends Binder
	{
		LocationSync getService() 	
		{
			return LocationSync.this;
		}
	}
	

	Thread thred;
	public static boolean mBreak= false;
	
	public static void stopSync(){
		
		mBreak= true;
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//return super.onStartCommand(intent, flags, startId);
		//Toast.makeText(getApplicationContext(),"Location:"+RouteActivity.dblLatitude,Toast.LENGTH_LONG).show();
		thred = new Thread() {
			public void run() {
				
				while(isLocationServiceInterrupt)
				{
					try
					{
						//Toast.makeText(getApplicationContext(),"Location:"+RouteActivity.dblLatitude,Toast.LENGTH_LONG).show();
						Log.i("LocationService", "Syn process triggered");
						mBreak= false;
						sendLocationtoServer();
						Thread.sleep(10000); // 1000 * 60
						
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
				
			}
		};
		thred.start();
		return START_STICKY;
	}
	
	private void sendLocationtoServer()
	{
		 handler.post (new Runnable (){
	            @Override
	            public void run() {
	            	Toast.makeText(getApplicationContext(),"Location:"+ApplicationConstants.getInstance().getDbllatitude(),Toast.LENGTH_LONG).show();
	            	
	            	new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try
							{
								if(checkInternetConnection())
								{
									objpost=new UrlPost();
									String strResult=objpost.strCallWebService(new String[]{"PdaId","Latitude","Longitude"}, new String[]{ApplicationConstants.getInstance().getStrPDAID(),Double.toString(ApplicationConstants.getInstance().getDbllatitude()),Double.toString(ApplicationConstants.getInstance().getDbllongitude())},ApplicationConstants.getInstance().getStrPostLocationtoserver(), 0,getApplicationContext());
									System.out.println("Result:::::"+strResult);
								}
								
							}catch(Exception e){System.out.println("Error:::::"+e.getMessage());}
						}
					}).start();;
	            	
	            }
	         });
		
	}

	public boolean checkInternetConnection()
	{
		boolean isConnected=false;
	    ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean connected = (connec.getActiveNetworkInfo() != null &&
							 connec.getActiveNetworkInfo().isAvailable() &&
							 connec.getActiveNetworkInfo().isConnected());
		 
		if(!connected)
		{
			isConnected=false;
		}
		else
		{
			isConnected=true;
		}
		return isConnected;
	}
	
}
