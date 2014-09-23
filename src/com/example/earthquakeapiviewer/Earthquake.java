package com.example.earthquakeapiviewer;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

//@SuppressWarnings("serial")
public class Earthquake implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3113032570331702850L;
	
	public String eqid;	
	public String magnitude;
	public String longitude;
	public String latitude;
	public String source;
	public Date datetime;
	public String address;
	public String depth;
	
	public Earthquake (JSONObject json) {
		try{
			eqid = json.getString("eqid");
			magnitude = json.getString("magnitude");
			longitude = json.getString("lng");
			latitude = json.getString("lat");
			source = json.getString("src");
			datetime = parseDate(json.getString("datetime"));
			depth = json.getString("depth");
		}catch(JSONException e) {
			Log.e("Earthquake", e.getMessage());
		}catch(ParseException e) {
			Log.e("Earthquake", e.getMessage());
		}
	}
	
	
	public void getAddresses(Context context) {
		List<Address> addresses = null;
		Geocoder g = new Geocoder(context);
		
		try {
			addresses = g.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);
		} catch (NumberFormatException e) {
			Log.e("Earthquake", e.getMessage());
		} catch (IOException e) {
			Log.e("Earthquake", e.getMessage());
		}
		
		if(addresses != null && !addresses.isEmpty())
			address = getAddress(addresses.get(0));
		else
			address = String.format("Latitude: %s Longitude: %s",this.latitude, this.longitude);
			
	}
	
	private Date parseDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("y-M-d H:m:s");
		
		return sdf.parse(date);
	}
	
	private String getAddress(Address address) {
		
		if(address != null)
		{
			String add = address.getFeatureName();
			if(add == null)
				add = address.getLocality();
			if(add == null)
				add = address.getCountryName();
			if(add != null)
				return add;
		}
		
		return String.format("Latitude: %s Longitude: %s",this.latitude, this.longitude);
		
	}

}
