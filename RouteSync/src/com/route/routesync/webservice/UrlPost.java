package com.route.routesync.webservice;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;

import com.route.routesync.common.ApplicationConstants;

public class UrlPost
{
	private HttpURLConnection connection;
	private InputStream inStream;
	private static final int TIMEOUT_CONNECT_MILLIS = 20000;
	private static final int TIMEOUT_READ_MILLIS = TIMEOUT_CONNECT_MILLIS - 5000;
	
	public InputStream soapPost(String xmlString,URL url,String soapUrl,Context context) throws Exception
	{
		try
		{
			connection = (HttpURLConnection)url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(TIMEOUT_CONNECT_MILLIS);
			connection.setReadTimeout(TIMEOUT_READ_MILLIS);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
			connection.setRequestProperty("Content-Length",""+xmlString.length() );
			connection.setRequestProperty("SOAPAction", soapUrl);
			DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
			outputStream.writeBytes(xmlString);
			outputStream.flush();
			
			inStream = (InputStream) connection.getInputStream();
		}
		catch(Exception e)
		{
			throw e;
		}
		return inStream;
	}
	

	public String strCallWebService(String[] arrNodes,String[] arrValues,String strMethodName,int Type,Context context) throws Exception
	{
		String strResultFromServer="";
		String strXmlString=createXMLForSending(arrNodes,arrValues,strMethodName);
		try
		{
			UrlPost urlPost=new UrlPost();
			
			InputStream inputStream =urlPost.soapPost(strXmlString, new URL(ApplicationConstants.getInstance().getServiceURL()),ApplicationConstants.getInstance().getSOAP_ACTION_URL()+strMethodName,context);
			switch (Type) {
			case 0:
				  
			    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		        factory.setNamespaceAware(true);
		        XmlPullParser xpp = factory.newPullParser();

		        xpp.setInput(inputStream, "UTF-8");
		        int eventType = xpp.getEventType();
		        while (eventType != XmlPullParser.END_DOCUMENT) 
		         {
			          if(eventType == XmlPullParser.TEXT) 
			          {
			        	  strResultFromServer = xpp.getText();
			              break;
			          }
			          eventType = xpp.next();
		         }
				break;
				
			case 1:
				 
		         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		         String response = bufferedReader.readLine();
		         while(response!= null)
		         {
		        	 strResultFromServer += response;
		        	 response = bufferedReader.readLine();
		         }
		          
		         if(strResultFromServer.contains("&lt;"))
		         {
		        	 strResultFromServer = strResultFromServer.replace("&lt;", "<");
		         }
		         if(strResultFromServer.contains("&gt;"))
		         {
		        	 strResultFromServer = strResultFromServer.replace("&gt;", ">");
		         }

				break;
			
			default:
				break;
			}
			
		}catch(Exception e){throw e;}
				
		return strResultFromServer;
		
	}
	
	public String createXMLForSending(String[] arrNodes,String[] arrValues,String strMethodName) throws Exception
	{
		StringBuffer sb=new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		sb.append("<soap:Body>");
		sb.append("<"+strMethodName+" xmlns=\"http://tempuri.org/\">");

		String strFianalXML="";
		try
		{
			if(arrNodes.length>0&&arrValues.length>0)
			{
				for(int i=0;i<arrNodes.length;i++)
				{
					sb.append("<"+ arrNodes[i]+">"+arrValues[i]+"</"+ arrNodes[i]+">");
				}
				
			}
			
			sb.append("</"+strMethodName+"> </soap:Body> </soap:Envelope>");
			strFianalXML=sb.toString();
			
		}catch(Exception e){throw e;}
		return strFianalXML;
	}
	

}

