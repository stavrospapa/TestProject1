package com.example.com.stavros.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	 private static String DB_PATH = "/data/data/com.example.com.stavros.test/databases/";
	 private static String DB_NAME = "AppDB.db";
	 private final Context myContext;
	
	  public static final String TABLE_URL = "URLs";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_URLNAME = "URLName";
	  public static final String COLUMN_URLADDRESS = "URLAddress";
	  
	  private static final int DATABASE_VERSION = 1;
	  private SQLiteDatabase myDataBase; 
	  
	  
	  // Database creation sql statement
/*
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_URL + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_URLNAME
	      + " text not null, "+ COLUMN_URLADDRESS +" text not null);";
*/
	  public MySQLiteHelper(Context context) {
	    super(context, DB_NAME, null, 1); 
	    this.myContext = context;
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	   // database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	   
		  /*
		  Log.w(MySQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
	    onCreate(db);
	    */
	  }

	  public void createDataBase() throws IOException{
		  
	    	boolean dbExist = checkDataBase();
	 
	    	if(dbExist){
	    		//do nothing - database already exist
	    	}else{
	 
	    		//By calling this method and empty database will be created into the default system path
	               //of your application so we are gonna be able to overwrite that database with our database.
	        	this.getReadableDatabase();
	 
	        	try {
	 
	    			copyDataBase();
	 
	    		} catch (IOException e) {
	 
	        		throw new Error("Error copying database");
	 
	        	}
	    	}
	 
	    }
	 
	  private boolean checkDataBase(){
		  
	    	SQLiteDatabase checkDB = null;
	 
	    	try{
	    		String myPath = DB_PATH + DB_NAME;
	    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	    		
	 
	    	}catch(SQLiteException e){
	 
	    		//database does't exist yet.
	 
	    	}
	 
	    	if(checkDB != null){
	 
	    		checkDB.close();
	 
	    	}
	 
	    	return checkDB != null ? true : false;
	    }
	  
	  public void openDataBase() throws SQLException{
		  
	    	//Open the database
	        String myPath = DB_PATH + DB_NAME;
	    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	 
	    }
	 
	    @Override
		public synchronized void close() {
	 
	    	    if(myDataBase != null)
	    		    myDataBase.close();
	 
	    	    super.close();
	 
		}
	 
	    private void copyDataBase() throws IOException{
	    	 
	    	//Open your local db as the input stream
	    	InputStream myInput = myContext.getAssets().open(DB_NAME);
	 
	    	// Path to the just created empty db
	    	String outFileName = DB_PATH + DB_NAME;
	 
	    	//Open the empty db as the output stream
	    	OutputStream myOutput = new FileOutputStream(outFileName);
	 
	    	//transfer bytes from the inputfile to the outputfile
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = myInput.read(buffer))>0){
	    		myOutput.write(buffer, 0, length);
	    	}
	 
	    	//Close the streams
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
	 
	    }
	    
	  
	} 