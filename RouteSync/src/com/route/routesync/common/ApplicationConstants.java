package com.route.routesync.common;


public class ApplicationConstants {
	
	
	private String strActivatedPackageName;
	private String strPDAID="100";
	private String strServiceURL="http://infovijayawada.com/INVJWService/INVJWWebservice.asmx";
	private String strPostLocationtoserver="SaveLatitudeLongitude";
	private String strSOAP_ACTION_URL =  "http://tempuri.org/";
	private static ApplicationConstants instance;
	private double dbllatitude;
	private double dbllongitude;
	
	public static ApplicationConstants getInstance()
	{
        if(instance == null){
        	instance = new ApplicationConstants();
        }
        return instance;
    }

	public String getStrActivatedPackageName() {
		return strActivatedPackageName;
	}

	public String getStrPostLocationtoserver() {
		return strPostLocationtoserver;
	}

	public String getStrPDAID() {
		return strPDAID;
	}

	public void setStrPDAID(String strPDAID) {
		this.strPDAID = strPDAID;
	}

	public void setStrPostLocationtoserver(String strPostLocationtoserver) {
		this.strPostLocationtoserver = strPostLocationtoserver;
	}

	public String getSOAP_ACTION_URL() {
		return strSOAP_ACTION_URL;
	}
	public String getServiceURL() {
		return strServiceURL;
	}

	public double getDbllatitude() {
		return dbllatitude;
	}

	public void setDbllatitude(double dbllatitude) {
		this.dbllatitude = dbllatitude;
	}

	public double getDbllongitude() {
		return dbllongitude;
	}

	public void setDbllongitude(double dbllongitude) {
		this.dbllongitude = dbllongitude;
	}
	
	
	

}
