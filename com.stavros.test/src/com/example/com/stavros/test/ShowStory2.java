package com.example.com.stavros.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;


import com.example.com.stavros.test.StackOverflowXmlParser.Entry;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class ShowStory2 extends Activity {

	WebView webView;
	String storyTitle = "N/A";
	String storyText = "N/A";
	String storyLink  = "N/A";
	String allHTML = "N/A";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_story2);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		storyTitle = intent.getStringExtra(ShowStories.EXTRA_MESSAGE4);
		storyText = intent.getStringExtra(ShowStories.EXTRA_MESSAGE5);
		storyLink = intent.getStringExtra(ShowStories.EXTRA_MESSAGE6);
		
		webView = (WebView) findViewById(R.id.webView1);
		
		webView.loadData(storyTitle+"<p>"+storyText,"text/html; charset=UTF-8", null);
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
		getMenuInflater().inflate(R.menu.show_story, menu);
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

	
	private class DownloadHTMLTask extends AsyncTask<String, Void, String> 
	{
		@Override
		protected String doInBackground(String [] urls) {
        try 
        {
        	downloadHTMLFromNetwork(urls[0]);
        	} catch (IOException e) 
        	{
           		return "network error";
        	} catch (XmlPullParserException e) 
        	{
	    	return "xml error";
	    	}
	            
        	return "ok";
		}

	        @Override
	        protected void onPostExecute(String result) 
	        {  
	        	webView.loadData(storyTitle+"<p>Full story Below<p>"+allHTML,"text/html; charset=UTF-8", null);
	        }

	        public String convertStreamToString(java.io.InputStream is) {
	            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	            return s.hasNext() ? s.next() : "";
	        }
	        
	 private void downloadHTMLFromNetwork(String urlString) throws XmlPullParserException, IOException 
	 {
	     InputStream stream = null;
	     
	       
	     try 
	     {
	         stream = downloadUrl(urlString);
	         
	         allHTML = convertStreamToString(stream);
	     } 
	     catch (Exception ex)
	     {
	    	 // show error
	     }
	     finally 
	     {
	         if (stream != null) 
	         {
	             stream.close();
	         } 
	      }
	 }

	 private InputStream downloadUrl(String urlString) throws IOException 
	 {
	     URL url = new URL(urlString);
	     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	     conn.setReadTimeout(10000 /* milliseconds */);
	     conn.setConnectTimeout(15000 /* milliseconds */);
	     conn.setRequestMethod("GET");
	     conn.setDoInput(true);
	     // Starts the query
	     conn.connect();
	     return conn.getInputStream();
	 }
	    
	    
	}

	
	public void loadFullStory(View view) 
	{
		 new DownloadHTMLTask().execute(storyLink);
	}
	
}
