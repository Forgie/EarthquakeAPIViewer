package com.example.earthquakeapiviewer;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayEarthquakeActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_earthquake);

		Earthquake eq = (Earthquake) getIntent().getSerializableExtra("Earthquake");
		
		TextView location_tv = (TextView)findViewById(R.id.location_tv);
		location_tv.setText(eq.address.trim());
		
		TextView date_tv = (TextView)findViewById(R.id.date_tv);
		date_tv.setText(eq.datetime.toString().trim());
		
		TextView magnitude_tv = (TextView)findViewById(R.id.magnitude_tv);
		magnitude_tv.setText(eq.magnitude);
		
		TextView depth_tv = (TextView)findViewById(R.id.depth_tv);
		depth_tv.setText(String.format("%sft", eq.depth));
		
		TextView source_tv = (TextView)findViewById(R.id.src_tv);
		source_tv.setText(eq.source);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_earthquake, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
