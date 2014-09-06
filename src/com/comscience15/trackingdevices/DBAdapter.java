package com.comscience15.trackingdevices;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	private static final String TAG = "DBAdapter";
	private static final String LOGTAG = "TRACKINGDEVICES";
	//User Tables
	public static final String KEY_U_ROWID = "ID";
	public static final int COL_U_ROWID = 0;
	public static final String KEY_U_FIRSTNAME = "Firstname";
	public static final String KEY_U_LASTNAME = "Lastname";
	public static final String KEY_U_TITLE = "Title";
	public static final String KEY_U_TEAM = "Team";
	
	//Device Tables
	public static final String KEY_D_ROWID = "ID";
	public static final int COL_D_ROWID = 0;
	public static final String KEY_D_BRAND = "BrandModel";
	public static final String KEY_D_OS = "OS";
	public static final String KEY_D_PLATFORM = "Platform";
	public static final String KEY_D_ZTAG = "zTag";
	public static final String KEY_D_DCHKOUT = "DateChkOut";
	public static final String KEY_D_DRETURN = "DateChkIn";
	public static final String KEY_D_PCHKOUT = "ChkOutPerson";
	public static final String KEY_D_PCHKIN = "ChkInPerson";
	public static final String KEY_D_DFIRSTIN = "DeviceFirstIn";
	public static final String KEY_D_PROVISIONED = "ProvisionedDevice";
	
	public static final String[] ALL_U_KEYS = new String[] {
									KEY_U_ROWID, 
									KEY_U_FIRSTNAME, 
									KEY_U_LASTNAME, 
									KEY_U_TITLE, 
									KEY_U_TEAM};
	public static final String[] ALL_D_KEYS = new String[] {
									KEY_D_ROWID,
									KEY_D_BRAND, 
									KEY_D_OS, 
									KEY_D_PLATFORM, 
									KEY_D_ZTAG, 
									KEY_D_DCHKOUT, 
									KEY_D_DRETURN, 
									KEY_D_PCHKOUT, 
									KEY_D_PCHKIN, 
									KEY_D_DFIRSTIN, 
									KEY_D_PROVISIONED};
	public static final String DATABASE_NAME = "TrackingDevicesDB";
	public static final String USERSDB_TABLE = "Users";
	public static final String DEVICESDB_TABLE = "Devices";
	public static final int DATABASE_VERSION = 2;
	
	static final String CREATE_USERSDB_SQL = 
			"CREATE TABLE IF NOT EXISTS " + USERSDB_TABLE + " (" + KEY_U_ROWID + " INTEGER primary key autoincrement, "
			+ KEY_U_FIRSTNAME + " TEXT not null, "
			+ KEY_U_LASTNAME + " TEXT not null, "
			+ KEY_U_TITLE + " TEXT not null, "
			+ KEY_U_TEAM + " TEXT not null" + ");";
	
	static final String CREATE_DEVICESDB_SQL = 
			"CREATE TABLE IF NOT EXISTS " + DEVICESDB_TABLE + " (" + KEY_D_ROWID + " INTEGER primary key autoincrement, "
			+ KEY_D_BRAND + " TEXT not null, "
			+ KEY_D_OS + " TEXT not null, "
			+ KEY_D_PLATFORM + " TEXT not null, "
			+ KEY_D_ZTAG + " TEXT not null, "
			+ KEY_D_DCHKOUT + " TEXT not null, "
			+ KEY_D_DRETURN + " TEXT not null, "
			+ KEY_D_PCHKOUT + " TEXT not null, "
			+ KEY_D_PCHKIN + " TEXT not null, "
			+ KEY_D_DFIRSTIN + " TEXT not null, "
			+ KEY_D_PROVISIONED + " TEXT not null" + ");";
	
	private final Context context;
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;
	
	public DBAdapter(Context ctx){
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		myDBHelper.close();
	}
	
	public static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_USERSDB_SQL);
			Log.i(LOGTAG, "User table has been created");
			db.execSQL(CREATE_DEVICESDB_SQL);
			Log.i(LOGTAG, "Devices table has been created");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's employee database from version " + oldVersion
					+ " to" + newVersion + ", which will destroy all old data!");
			
			db.execSQL("DROP TABLE IF EXISTS " + USERSDB_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + DEVICESDB_TABLE);
			onCreate(db);
		}
	}
	
	public long insertUserRow(String firstName, String lastName, String title, String team) {
		ContentValues initValues = new ContentValues();
		initValues.put(KEY_U_FIRSTNAME, firstName);
		initValues.put(KEY_U_LASTNAME, lastName);
		initValues.put(KEY_U_TITLE, title);
		initValues.put(KEY_U_TEAM, team);
		
		return db.insert(USERSDB_TABLE, null, initValues);
	}
	
	public long insertDeviceRow(String DeviceBrand, String OS, String Platform, String zTAG, String datChkOut, String dateChkIn, String PersonChkOut, String PersonChkIn, String DeviceFirstIn, String ProDevice) {
//	public long insertDeviceRow(String DeviceBrand, String OS, String Platform, String zTAG, String datChkOut, String dateChkIn, String PersonChkOut, String PersonChkIn, String DeviceFirstIn) {
		getCurrentDateTime();
		
		ContentValues initValues = new ContentValues();
		initValues.put(KEY_D_BRAND, DeviceBrand);
		initValues.put(KEY_D_OS, OS);
		initValues.put(KEY_D_PLATFORM, Platform);
		initValues.put(KEY_D_ZTAG, zTAG);
		initValues.put(KEY_D_DCHKOUT, getCurrentDateTime());
		initValues.put(KEY_D_DRETURN, dateChkIn);
		initValues.put(KEY_D_PCHKOUT, PersonChkOut);
		initValues.put(KEY_D_PCHKIN, PersonChkIn);
		initValues.put(KEY_D_DFIRSTIN, DeviceFirstIn);
		initValues.put(KEY_D_PROVISIONED, ProDevice);
		
		return db.insert(DEVICESDB_TABLE, null, initValues);
	}
	
	private String getCurrentDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date(0);
		return sdf.format(date);
	}

	public boolean deleteUserRow(long rowId) {
		String where = KEY_U_ROWID + "=" + rowId;
		return db.delete(USERSDB_TABLE, where, null) != 0;
	}
	
	public void deleteAllUsers() {
		Cursor c = getAllUsersRows();
		long rowId = c.getColumnIndexOrThrow(KEY_U_ROWID);
		if(c.moveToFirst()) {
			do {
				deleteUserRow(c.getLong((int) rowId));
			} while (c.moveToNext());
		}
		c.close();
	}
	
	public Cursor getAllUsersRows() {
//		String where = null;
		Cursor c = db.query(USERSDB_TABLE, ALL_U_KEYS, null, null, null, null, null);
		
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	public Cursor getAllDevicesRows() {
//		String where = null;
		Cursor c = db.query(DEVICESDB_TABLE, ALL_D_KEYS, null, null, null, null, null);
		
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public Cursor getRow(long rowId) {
		String where = KEY_U_ROWID + "=" + rowId;
		Cursor c = db.query(true, USERSDB_TABLE, new String[] { KEY_U_ROWID, 
				KEY_U_FIRSTNAME, 
				KEY_U_TITLE, }, where, null, null, null, null, null);
		
		if(c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	public boolean updateRow(long rowId, String firstName, String lastName, String title, String team) {
		String where = KEY_U_ROWID + "=" + rowId;
		
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_U_FIRSTNAME, firstName);
		newValues.put(KEY_U_LASTNAME, lastName);
		newValues.put(KEY_U_TITLE, title);
		newValues.put(KEY_U_TEAM, team);
		
		return db.update(USERSDB_TABLE, newValues, where, null) != 0;
	}
}
