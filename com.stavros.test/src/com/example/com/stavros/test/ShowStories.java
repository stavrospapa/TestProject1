package com.example.com.stavros.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import com.example.com.stavros.test.StackOverflowXmlParser.Entry;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;

public class ShowStories extends Activity {
	
	 private static final boolean VERBOSE = true;
	 private static final String TAG = "------------------SampleActivity";
	 
	 public final static String EXTRA_MESSAGE4 = "com.example.com.stavros.MESSAGE4";
	public final static String EXTRA_MESSAGE5 = "com.example.com.stavros.MESSAGE5";
	public final static String EXTRA_MESSAGE6 = "com.example.com.stavros.MESSAGE6";
	
	int listIsPopulated = 0;
	int key1 = 0;
	
	ArrayList<String> arrayWithTitles = new ArrayList<String>();
	ArrayList<String> arrayWithDescriptions = new ArrayList<String>();
	ArrayList<String> arrayWithLinks = new ArrayList<String>();
	
	ArrayAdapter adapter;
	ListView listView;
	
	// Create a message handling object as an anonymous class.
	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
	    public void onItemClick(AdapterView parent, View v, int position, long id) {
		        
	    		showStory(position);
	    }
	};
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_stories);
		// Show the Up button in the action bar.
		setupActionBar();
		
		
	   if (VERBOSE) Log.v(TAG, "+++ ON CREATE +++");
	}

	
	
	@Override
	public void onResume() {
	    super.onResume();
	    
	    if (VERBOSE) Log.v(TAG, "+++ ON RESUME +++");
	    
	    if ( key1 == 0 )
	    {
	    	adapter = new ArrayAdapter<String>(this, 
	    			android.R.layout.simple_list_item_1, arrayWithTitles);
	    	listView = (ListView) findViewById(R.id.listView1);
	    		
	    	listView.setAdapter(adapter);
	    	listView.setOnItemClickListener(mMessageClickedHandler); 
	    		
	    	adapter.notifyDataSetChanged();
	    
	    	// load selected feed
	    	Intent intent = getIntent();
	    	String feedURL = intent.getStringExtra(DefaultMessageActivity.EXTRA_MESSAGE2);
	    	new DownloadXmlTask().execute(feedURL);
	    }
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
		getMenuInflater().inflate(R.menu.show_stories, menu);
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

	
	// Implementation of AsyncTask used to download XML feed from stackoverflow.com.
    private class DownloadXmlTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String [] urls) {
            try {
                loadXmlFromNetwork(urls[0]);
            } catch (IOException e) {
            	arrayWithTitles.add("network error");
            	adapter.notifyDataSetChanged();;
            	return "network error";
            	
            } catch (XmlPullParserException e) {
            	arrayWithTitles.add("xml error");
            	adapter.notifyDataSetChanged();;
            	return "xml error";
            }
            
            return "ok";
        }

        @Override
        protected void onPostExecute(String result) {  
            //setContentView(R.layout.main);
            // Displays the HTML string in the UI via a WebView
            //WebView myWebView = (WebView) findViewById(R.id.webview);
            //myWebView.loadData(result, "text/html", null);
        	adapter.notifyDataSetChanged();
        	listIsPopulated = 1;
    }

 // Uploads XML from stackoverflow.com, parses it, and combines it with
 // HTML markup. Returns HTML string.
 private void loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
     InputStream stream = null;
     // Instantiate the parser
     StackOverflowXmlParser stackOverflowXmlParser = new StackOverflowXmlParser();
     List<Entry> entries = null;
     String title = null;
     String link = null;
     String description = null;
       
     try {
         stream = downloadUrl(urlString);        
         entries = stackOverflowXmlParser.parse(stream);
     // Makes sure that the InputStream is closed after the app is
     // finished using it.
     } catch (Exception ex)
     {
    	 
     }
     finally {
         if (stream != null) {
             stream.close();
         } 
      }
     
     // StackOverflowXmlParser returns a List (called "entries") of Entry objects.
     // Each Entry object represents a single post in the XML feed.
     // This section processes the entries list to combine each entry with HTML markup.
     // Each entry is displayed in the UI as a link that optionally includes
     // a text summary.
     for (Entry entry : entries) {       
        
    	 arrayWithTitles.add(entry.title);
    	 arrayWithDescriptions.add(entry.description);
    	 arrayWithLinks.add(entry.link);
    	 
         // If the user set the preference to include summary text,
         // adds it to the display.
         //if (pref) {
         //    htmlString.append(entry.description);
        // }
     }
    
 }

 // Given a string representation of a URL, sets up a connection and gets
 // an input stream.
 private InputStream downloadUrl(String urlString) throws IOException {
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
    
    
} // class

    
    public void showStory(int p)
    {
    	Intent intent = new Intent(this, ShowStory2.class);
    
		intent.putExtra(EXTRA_MESSAGE4, new String(arrayWithTitles.get(p)));
		intent.putExtra(EXTRA_MESSAGE5, new String(arrayWithDescriptions.get(p)));
		intent.putExtra(EXTRA_MESSAGE6, new String(arrayWithLinks.get(p)));
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent,0);
    	
    }
    
   

    @Override
    public void onStart() {
        super.onStart();
        if (VERBOSE) Log.v(TAG, "++ ON START ++");
    }

    

    @Override
    public void onPause() {
        super.onPause();
        if (VERBOSE) Log.v(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (VERBOSE) Log.v(TAG, "-- ON STOP --");
    }

   @Override
    public void onDestroy() {
        super.onDestroy();
        if (VERBOSE) Log.v(TAG, "- ON DESTROY -");
    }
    

  
   
}
