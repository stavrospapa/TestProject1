package com.example.com.stavros.test;

public class URLEntry {
	  private long id;
	  private String urlname;
	  private String urladdress;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getURLname() {
	    return urlname;
	  }

	  public void setURLname(String u) {
	    this.urlname = u;
	  }

	  public String getURLaddress() {
		    return urladdress;
		  }

		  public void setURLaddress(String u) {
		    this.urladdress = u;
		  }
	  
	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return urlname;
	  }
	} 
