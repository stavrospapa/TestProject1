package com.example.com.stavros.test;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.com.stavros.*;

import java.io.IOException;
import java.util.Date;

public class MainActivity extends Activity  implements View.OnClickListener {

	private static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	Button btn;
	Button btn2;
	Button btn3;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       
        
        btn=(Button)findViewById(R.id.button1);
        btn.setOnClickListener(this);
        
        btn2=(Button)findViewById(R.id.button2);
        btn2.setOnClickListener(this);
        
        btn3=(Button)findViewById(R.id.button3);
        btn3.setOnClickListener(this);
        updateTime();
        
        createDatabase();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    public void onClick(View view) {
    	
    	//updateTime();
    	
    	switch (view.getId()) {
        case R.id.button1:
        	Intent intent = new Intent(this, DefaultMessageActivity.class);
        	intent.putExtra(EXTRA_MESSAGE, new String("hello"));
            startActivity(intent);    
            break;
        case R.id.button2:
        	Intent intent2 = new Intent(this, SettingsActivity.class);
        	startActivity(intent2);
           break;
        case R.id.button3:
        	Intent intent3 = new Intent(this, SensorActivity.class);
        	startActivity(intent3);
           break;
        }   
    	
    	}
    
    	private void updateTime() {
    	btn.setText(new Date().toString());
    	}
    
    
   
    	public void createDatabase() {
    		
            MySQLiteHelper myDbHelper = new MySQLiteHelper(this);
     
            try {
     
            	myDbHelper.createDataBase();
     
     	} catch (IOException ioe) {
     
     		Log.v("Main --","Unable to create database");
     
     	}
     
            /*
     	try {
     
     		myDbHelper.openDataBase();
     
     	}catch(SQLException sqle){
     
     		Log.v("Main --","Unable to open database");
     
     	}*/
    	}
}
