package com.example.myproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {
	public static final String TABLE_PROFILES = "profiles";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_PNAME = "pname";
	public static final String COLUMN_PLATI = "platitude";
	public static final String COLUMN_PNLONGI = "plongitude";
	public static final String COLUMN_PISPRO = "isproenable";
	public static final String COLUMN_PISREM = "isremenable";
	public static final String COLUMN_PVOL = "pvolume";
	public static final String COLUMN_PRADI = "pcoverage_radius";
	public static final String COLUMN_PREM = "preminder";
	public static final String COLUMN_PTONE = "pringtone";
	
	private final static String GPS_DATABASE="profile_data.db";
	private final static String GPS_CREATE= "create table "
		      + TABLE_PROFILES + "(" + COLUMN_ID
		      + " integer primary key autoincrement, " + COLUMN_PNAME
		      + " varchar(30), " + COLUMN_PLATI
		      + " double not null, " + COLUMN_PNLONGI
		      + " double not null, " + COLUMN_PISPRO
		      + " boolean default false, " + COLUMN_PISREM
		      + " boolean default false, " + COLUMN_PVOL
		      + " int not null, " + COLUMN_PRADI
		      + " int not null, " + COLUMN_PREM
		      + " varchar(300), " + COLUMN_PTONE
		      + " varchar(300) default null" +");";

	public SQLiteDataBaseHelper(Context context)
	{
		super(context, GPS_DATABASE, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
