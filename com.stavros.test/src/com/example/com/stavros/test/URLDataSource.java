package com.example.com.stavros.test;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class URLDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
      MySQLiteHelper.COLUMN_URLNAME,  MySQLiteHelper.COLUMN_URLADDRESS };

  public URLDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public URLEntry createURL(String url, String address) {
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.COLUMN_URLNAME, url);
    values.put(MySQLiteHelper.COLUMN_URLADDRESS, address);
    
    long insertId = database.insert(MySQLiteHelper.TABLE_URL, null, values);
    Cursor cursor = database.query(MySQLiteHelper.TABLE_URL,
        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    URLEntry newURL = cursorToComment(cursor);
    cursor.close();
    return newURL;
  }

  public void deleteURL(URLEntry url) {
    long id = url.getId();
    System.out.println("Comment deleted with id: " + id);
    database.delete(MySQLiteHelper.TABLE_URL, MySQLiteHelper.COLUMN_ID
        + " = " + id, null);
  }

  public List<URLEntry> getAllComments() {
    List<URLEntry> urls = new ArrayList<URLEntry>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_URL,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      URLEntry urlname = cursorToComment(cursor);
      urls.add(urlname);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return urls;
  }

  private URLEntry cursorToComment(Cursor cursor) {
	  URLEntry urlname = new URLEntry();
	  urlname.setId(cursor.getLong(0));
	  urlname.setURLname(cursor.getString(1));
	  urlname.setURLaddress(cursor.getString(2));
    return urlname;
  }
} 
