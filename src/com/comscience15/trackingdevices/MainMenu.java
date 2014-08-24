package com.comscience15.trackingdevices;

import java.io.File;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends Activity{
	private String TAG = "TRACKINGDEVICES";
//	private Button scanBtn, deviceBtn, userBtn;
	private TextView formatTxt, contentTxt;
	private DBAdapter db;
	
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.mainmenu);
	    
//	    scanBtn = (Button)findViewById(R.id.scan_button);
	    formatTxt = (TextView)findViewById(R.id.scan_format);
	    contentTxt = (TextView)findViewById(R.id.scan_content);
	    
//	    deviceBtn = (Button)findViewById(R.id.viewDevices);
//	    userBtn = (Button)findViewById(R.id.viewUsers);
	    
	    //check if DB exists
	    checkDBExist();
	}

	private boolean checkDBExist() {
		SQLiteDatabase chkDB = null;
		try {
			File database = getApplicationContext().getDatabasePath("TrackingDevicesDB");
			if (database.exists()) {
				Log.i(TAG, "Found database");
				String myPath = database.getAbsolutePath();
				Log.i(TAG, "Database Path: " + myPath);
				chkDB = SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.OPEN_READONLY);
//				Cursor c = chkDB.rawQuery("SELECT * from Devices", null);
//				c.moveToFirst();
//				Log.d("1st Device's added", "\\\\\\\\ First Device is added \\\\\\\\"+ "\n" +
//								"ID: " + c.getString(c.getColumnIndex("ID")) + "\n" +
//								"Brand/Model: " + c.getString(c.getColumnIndex("BrandModel")) + "\n" +
//								"Platform: " + c.getString(c.getColumnIndex("Platform")) + "\n" +
//								"OS: " + c.getString(c.getColumnIndex("OS")) + "\n" + 
//								"zTAG: " + c.getString(c.getColumnIndex("zTag")) + "\n");
//				c = chkDB.rawQuery("SELECT * from Users", null);
//			    c.moveToFirst();
//			    Log.d("1st User's added", "\\\\\\\\ First User is added \\\\\\\\"+ "\n" +
//			    				"Full Name: " + c.getString(c.getColumnIndex("firstname")) + " " + c.getString(c.getColumnIndex("lastname")) + "\n" + 
//			    				"Title: " + c.getString(c.getColumnIndex("title")) + "\n" + 
//			    				"Team: " + c.getString(c.getColumnIndex("team")) + "\n");
			} else {
				// Database does not exist so create Databases for Devices and Employees
				Log.i(TAG, "Database is not Found but creating it.");
			    createDB();
			}
		} catch (SQLiteException e) {
			Log.i(TAG, "Database is not Found and there is SQLiteException.");
		} finally {
			if (chkDB != null) {
				chkDB.close();
			}
		}
		return chkDB != null ? true : false;
	}

	public void createDB() {
//		SQLiteDatabase db = openOrCreateDatabase("TrackingDevicesDB", MODE_PRIVATE, null);
		SQLiteDatabase db = openOrCreateDatabase(DBAdapter.DATABASE_NAME, MODE_PRIVATE, null);
	    db.execSQL("CREATE TABLE IF NOT EXISTS Devices (ID INTEGER primary key autoincrement, BrandModel TEXT, Platform TEXT, OS TEXT, zTag TEXT, DeviceFirstIn TEXT, DateChkOut TEXT, ChkOutPerson TEXT, DateChkIn TEXT, ChkInPerson TEXT, ProvisionedDevice TEXT);");
	    db.execSQL("CREATE TABLE IF NOT EXISTS Users (ID INTEGER primary key autoincrement, Firstname TEXT, Lastname TEXT, Title TEXT, Team TEXT);");
		Log.i(TAG, "Devices Table is created");
		Log.i(TAG, "Users Table is created");
//	    db.execSQL("INSERT INTO Users VALUES(null, 'Nat', 'Srileeannop', 'QA IV', 'QA Functional');");
//	    db.execSQL("INSERT INTO Devices VALUES(null, 'iPhone 5', '7.0.6', 'iOS', 'ZML12345', '8/8/2014', '8/9/2014', 'Nat', 'Nat', '7/18/2014', 'Yes');");
	    checkDBExist();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve scan result
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		
		if (scanningResult != null) {
			//we have a result
		}else{
		    Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
		    toast.show();
		}
		String scanContent = scanningResult.getContents();
		String scanFormat = scanningResult.getFormatName();
		
		formatTxt.setText("FORMAT: " + scanFormat);
		contentTxt.setText("CONTENT: " + scanContent);
	}
	
	// Scanner button click
	public void Scan(View V) {
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();
	}

	// Open devices list and details
	public void devicesInventory(View V) {
		Intent intent = new Intent(this, Devices.class);
		startActivity(intent);
	}

	// Open employees list and details
	public void usersInventory(View V) {
		//open Users class
		Intent intent = new Intent(this, Users.class);
		// open UserList class
//		Intent intent = new Intent(this, UserList.class);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDB();
	}

	private void closeDB() {
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		return super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}
