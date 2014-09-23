package com.example.earthquakeapiviewer;

import org.json.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.util.Log;

public class CallApi extends AsyncTask<Void, Void, ArrayList<Earthquake>> {
	MainActivity _activity;
	ArrayList<Earthquake> _arrayList;
	ProgressDialog _progressDialog;
	boolean _hasInternetConnection;
	
	public CallApi(MainActivity activity){
		super();
		_activity = activity;
		_arrayList = new ArrayList<Earthquake>();

		 _hasInternetConnection = true;
	}
	
	@Override
	protected ArrayList<Earthquake> doInBackground(Void...params) {
		String apiUrl = "http://api.geonames.org/earthquakesJSON?formatted=true&north=44.1&south=-9.9&east=-22.4&west=55.2&username=mkoppelman";
		InputStream inputStream;
		String jsonString = "";
		
		try {
			 
			URL url = new URL(apiUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			
			inputStream = new BufferedInputStream(urlConnection.getInputStream());
		    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
		    StringBuilder sb = new StringBuilder();

		    String line = null;
		    while ((line = reader.readLine()) != null)
		    {
		        sb.append(line + "\n");
		    }
		    
		    jsonString = sb.toString();
		    inputStream.close();
		    

			parseJson(jsonString);
		} catch (IOException e) {
			
			Log.e("HTTP Request", e.getMessage());
			_hasInternetConnection = false;
			 
		} catch (JSONException je) {
			Log.e("JSON Parser", je.getMessage());
		}
		
        return _arrayList;
 
    }
	
	 @Override
	  protected void onPreExecute() {
		 super.onPreExecute();

		 _progressDialog = ProgressDialog.show(_activity, "Loading", "Fetching earthquake data!");
	  }
	
	@Override
	protected void onPostExecute(ArrayList<Earthquake> result) {
		super.onPostExecute(result);
		if(_hasInternetConnection) {
			
	    	for(Earthquake eq : result) {
	    		eq.getAddresses(_activity.getBaseContext());
	    	}
	    	
			_activity.updateData(result);
			_progressDialog.dismiss();
		} else {
			_progressDialog.dismiss();
			
			AlertDialog.Builder adBuilder = new AlertDialog.Builder(_activity);
			 
			adBuilder.setMessage("Check your connection and try again.")
				.setTitle("No Internet Access")
				.setPositiveButton("OK", new OnClickListener() {
			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				}).create();
			
			adBuilder.show();
		}
	}
	
	private void parseJson(String json) throws JSONException {
		
		JSONObject jObject = new JSONObject(json);
		JSONArray jArray = jObject.getJSONArray("earthquakes");
		
		for(int i = 0; i<jArray.length(); i++)
		{	
			_arrayList.add(new Earthquake(jArray.getJSONObject(i)));
		} 
	}
}
