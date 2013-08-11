package com.route.routesync;

public interface GPSListener {
	
	/**
	 * This is used to send latitude and longitude
	 * @param latitude
	 * @param longitude
	 */
	public void getLocation(double latitude,double longitude);

}
