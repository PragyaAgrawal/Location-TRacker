package com.example.myproject.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProfileData {
	private SQLiteDatabase database;
	 private SQLiteDataBaseHelper dbHelper;
	 private String[] allColumns = { SQLiteDataBaseHelper.COLUMN_ID,
			 SQLiteDataBaseHelper.COLUMN_PNAME,SQLiteDataBaseHelper.COLUMN_PLATI,
			 SQLiteDataBaseHelper.COLUMN_PNLONGI,SQLiteDataBaseHelper.COLUMN_PISPRO,
			 SQLiteDataBaseHelper.COLUMN_PISREM,SQLiteDataBaseHelper.COLUMN_PVOL,
			 SQLiteDataBaseHelper.COLUMN_PRADI,SQLiteDataBaseHelper.COLUMN_PREM,
			 SQLiteDataBaseHelper.COLUMN_PTONE};
	 
	 public ProfileData(Context context) {
		    dbHelper = new SQLiteDataBaseHelper(context);
		  }

	 public void open() throws SQLException {
		    database = dbHelper.getWritableDatabase();
		  }

	 public void close() {
		    dbHelper.close();
		  }
	
	public ProfileGps createProfile (String pname,double plati,double plongi,
			boolean pispro,boolean pisrem,int pvol,int pradi,String prem,String ptone) {
	    ContentValues values = new ContentValues();
	    values.put(SQLiteDataBaseHelper.COLUMN_PNAME, pname);
	    values.put(SQLiteDataBaseHelper.COLUMN_PLATI,plati);
	    values.put(SQLiteDataBaseHelper.COLUMN_PNLONGI, plongi);
	    values.put(SQLiteDataBaseHelper.COLUMN_PISPRO, pispro);
	    values.put(SQLiteDataBaseHelper.COLUMN_PISREM, pisrem);
	    values.put(SQLiteDataBaseHelper.COLUMN_PVOL, pvol);
	    values.put(SQLiteDataBaseHelper.COLUMN_PRADI,pradi);
	    values.put(SQLiteDataBaseHelper.COLUMN_PREM, prem);
	    values.put(SQLiteDataBaseHelper.COLUMN_PTONE, ptone);
	    
	    long insertId = database.insert(SQLiteDataBaseHelper.TABLE_PROFILES, null,
	        values);
	    Cursor cursor = database.query(SQLiteDataBaseHelper.TABLE_PROFILES,
	        allColumns, SQLiteDataBaseHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    ProfileGps newProfile = cursorToProfile(cursor);
	    cursor.close();
	    return newProfile;
	  }
	
	public long  updateProfile (ContentValues val,long  pid) {
	   // ContentValues values = new ContentValues();
	   // values.put(pro_colstr, prostr);
	  
	   
	    long  Id = database.update(SQLiteDataBaseHelper.TABLE_PROFILES, val, "_id" + "='" + pid
               + "'", null);
	   
	    
	    
	    
	    return Id;
	  }
	public void deleteProfile(ProfileGps profile) {
	   int id = profile.getPid();
	    System.out.println("profile deleted with id: " + profile);
	    database.delete(SQLiteDataBaseHelper.TABLE_PROFILES, SQLiteDataBaseHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<ProfileGps> getAllProfiles() {
	    List<ProfileGps> profiles = new ArrayList<ProfileGps>();

	    Cursor cursor = database.query(SQLiteDataBaseHelper.TABLE_PROFILES,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	ProfileGps profile = cursorToProfile(cursor);
	    	profiles.add(profile);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return profiles;
	  }
	  public ProfileGps getProfilewithid(long id) {
		   // List<ProfileGPS> profiles = new ArrayList<ProfileGPS>();
		  ProfileGps profile=null;
		    Cursor cursor = database.query(SQLiteDataBaseHelper.TABLE_PROFILES,
		        allColumns, "_id="+id, null, null, null, null);

		    cursor.moveToFirst();
		   // while (!cursor.isAfterLast()) {
		    	profile = cursorToProfile(cursor);
		    	//profiles.add(profile);
		      //cursor.moveToNext();
		  //  }
		    // Make sure to close the cursor
		    cursor.close();
		    return profile;
		  }

	  private ProfileGps cursorToProfile(Cursor cursor) {
		  ProfileGps profile = new ProfileGps();
		  profile.setPid(cursor.getInt(0));
		  profile.setPname(cursor.getString(1));
		  profile.setPlatitude(cursor.getFloat(2));
		  profile.setPlongitude(cursor.getFloat(3));
		  profile.setPro_enable(cursor.getInt(4)==1);
		  profile.setRem_enable(cursor.getInt(5)==1);
		  profile.setPvolume(cursor.getInt(6));
		  profile.setPcoverage_radius(cursor.getInt(7));
		  profile.setPreminder(cursor.getString(8));
		  profile.setPringtone(cursor.getString(9));
			 
	    return profile;
	  }

}
