package com.example.com.stavros.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.NavUtils;
import android.text.format.DateFormat;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.com.stavros.test.StackOverflowXmlParser.Entry;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DefaultMessageActivity extends Activity {

	public final static String EXTRA_MESSAGE1 = "com.example.com.stavros.MESSAGE1";
	public final static String EXTRA_MESSAGE2 = "com.example.com.stavros.MESSAGE2";
	public final static String EXTRA_MESSAGE3 = "com.example.com.stavros.MESSAGE3";
	

	private URLDataSource datasource;
	
	ArrayAdapter<URLEntry> arrayAdapter;
	List<URLEntry> listOfURLEntryObjects; 
	ListView listView;
	
	//URLs
	Resources rs1,rs2;
	String[] listOfFeedNames, listOfFeedURLs;
	ArrayList<String> arrayWithFeedNames = new ArrayList<String>();
	ArrayList<String> arrayWithFeedURLs = new ArrayList<String>();
	
	// Create a message handling object as an anonymous class.
	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
	    public void onItemClick(AdapterView parent, View v, int position, long id) {
	        
	    		loadFeed(position);
	    }
	};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_defailt_message);
		// Show the Up button in the action bar.
		setupActionBar();
		
 		
 		// DB STAFF
		 datasource = new URLDataSource(this);
		 datasource.open();
	 
		 
		 listOfURLEntryObjects = datasource.getAllComments();

		 
		 // new UI
		 listView = (ListView) findViewById(R.id.listView1);
		 
		 arrayAdapter = new ArrayAdapter<URLEntry>(this,
			        android.R.layout.simple_list_item_1, listOfURLEntryObjects);
		 listView.setAdapter(arrayAdapter);
		

		 //URLEntry urlentry= datasource.createURL("Philenews Top Stories","http://www.philenews.com/Publications/RssModule/rss.aspx?CategoryId=399");
		 //arrayAdapter.add(urlentry);
		 // use arrayAdapter.add(URLEntry) or delete(URLENtry)
		 
		 arrayAdapter.notifyDataSetChanged();
		 listView.setOnItemClickListener(mMessageClickedHandler); 
		 
		/*
		rs1 = getResources();
		
		listOfFeedNames = rs1.getStringArray(R.array.listOfFeedNames);
		listOfFeedURLs = rs1.getStringArray(R.array.listOfFeedURLs);
		
		arrayWithFeedNames = new ArrayList<String>(Arrays.asList(listOfFeedNames));
		arrayWithFeedURLs = new ArrayList<String>(Arrays.asList(listOfFeedURLs));
				
		
		adapter = new ArrayAdapter<String>(this, 
		        android.R.layout.simple_list_item_1, arrayWithFeedNames);
		listView = (ListView) findViewById(R.id.listView1);
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(mMessageClickedHandler); 
		
		adapter.notifyDataSetChanged();
		*/
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.defailt_message, menu);
		return true;
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

	
	
	public void loadFeed(int p) 
	{
		Intent intent = new Intent(this, ShowStories.class);
	    
		URLEntry u = listOfURLEntryObjects.get(p);
		intent.putExtra(EXTRA_MESSAGE1, new String(u.getURLname()));
		intent.putExtra(EXTRA_MESSAGE2, new String(u.getURLaddress()));
		intent.putExtra(EXTRA_MESSAGE3, new String("available"));
        startActivity(intent);
		
	}
	
 
    
   
}

