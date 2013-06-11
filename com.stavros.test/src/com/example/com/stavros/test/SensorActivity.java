package com.example.com.stavros.test;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class SensorActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mCompass;

    TextView textView1, textView2;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor);
		// Show the Up button in the action bar.
		setupActionBar();
		
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mCompass = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        
        textView1 = (TextView)findViewById(R.id.TextView01);
        textView2 = (TextView)findViewById(R.id.textView2);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sensor, menu);
		return true;
	}

	@Override
	 protected void onResume() {
         super.onResume();
         mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
         mSensorManager.registerListener(this, mCompass, SensorManager.SENSOR_DELAY_NORMAL);
     }
     
	 @Override
	 protected void onPause() {
         super.onPause();
         mSensorManager.unregisterListener(this);
     }

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
		
		
		
    }

    public void onSensorChanged(SensorEvent event) {
    	
    	float x=0,y=0,z=0,m;
    	
    	if ( event.sensor.getType() == Sensor.TYPE_ACCELEROMETER )
    	{
    		x= event.values[0];
    		y= event.values[1];
    		z= event.values[2];
    		textView1.setText("Acceleration: X:"+x+" Y: "+y+" Z:"+z);
    	}else
		if ( event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD )
    	{
			m = event.values[0];
    		textView2.setText("Compass: "+m);
    	}	
    }
	
}
