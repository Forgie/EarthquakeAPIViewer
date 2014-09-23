package com.example.earthquakeapiviewer;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	ListView _listView;
	CallApi _callApi;
	EarthquakeAdapter _earthquakeAdapter;
    ArrayList<Earthquake> _list;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _listView = (ListView) findViewById(R.id.list);
        
        _callApi = new CallApi(this);
        _callApi.execute();
        
        _list = new ArrayList<Earthquake>();
        _earthquakeAdapter = new EarthquakeAdapter(this, _list);

        _listView.setAdapter(_earthquakeAdapter);
        _listView.setEmptyView(findViewById(R.id.empty_list));    
        _listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Earthquake eq = (Earthquake)parent.getItemAtPosition(position);

				Intent intent = new Intent()
					.setClass(getBaseContext(), DisplayEarthquakeActivity.class)
						 .putExtra("Earthquake", eq);

			    startActivity(intent);			
			}
        	
        });
    }
    
    public void updateData(ArrayList<Earthquake> data) {
    	_earthquakeAdapter.clear();
    	_earthquakeAdapter.addAll(data);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                _earthquakeAdapter.notifyDataSetChanged();
            }
     });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.item1) {
        	_callApi = new CallApi(this);
        	_callApi.execute();
        	
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    	
    	
	private class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    	private final Context context;
    	private final ArrayList<Earthquake> values;

    	public EarthquakeAdapter(Context context, ArrayList<Earthquake> values) {
    		super(context, android.R.layout.simple_list_item_1, values);
    			//R.layout.earthquake_row, values);
    		this.context = context;
    		this.values = values;
    	}
    	  
    	
    	public EarthquakeAdapter(Context context, int resource, int textViewResourceId, ArrayList<Earthquake> values) {
    		super(context, resource, textViewResourceId, values);
    		
    		this.context = context;
    		this.values = values;
    		
    	}
    	
		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.earthquake_row, parent, false);
		    
		    TextView tv1 = (TextView) rowView.findViewById(R.id.firstLine);
		    String text1;
		    if(this.values.isEmpty())
		    	text1 = "Location";
		    else
		    	//text1 = String.format("Latitude: %s Longitude: %s",this.values.get(position).latitude, this.values.get(position).longitude);
		    	//text1 = getAddress(this.values.get(position).address, position);//, this.values.get(position).longitude);///String.format("Latitude: %s Longitude: %s", this.values.get(position).latitude, this.values.get(position).longitude);
	    	text1 = this.values.get(position).address;
		    tv1.setText(text1);

		    TextView tv2 = (TextView) rowView.findViewById(R.id.secondLine);
		    String magnitude = String.format("Magnitude: %s", this.values.get(position).magnitude);
		    
		    float mag = Float.parseFloat(this.values.get(position).magnitude);
		    
		    if(mag >= 8)
		    	rowView.setBackgroundColor(Color.rgb(227, 77, 48));
		    else if(mag >= 5 && mag < 8)
		    	rowView.setBackgroundColor(Color.rgb(227, 143, 48));
		    else rowView.setBackgroundColor(Color.rgb(227, 206, 48));
		    
		    tv2.setText(magnitude);
		
		    return rowView;
		 }
    }   
}
