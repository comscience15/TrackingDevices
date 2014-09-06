package com.comscience15.trackingdevices;

import java.util.ArrayList;

import com.comscience15.trackingdevices.DBAdapter.DatabaseHelper;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class UserList extends ListActivity{
/*
 * 	previous code
 */
//	private DBAdapter dbAdapter;
//	private SimpleCursorAdapter sca;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.userslist);
//		
//		dbAdapter = new DBAdapter(this);
//		openDB();
//		displayUserList();
//	}
//
//	@SuppressLint("NewApi")
//	private void displayUserList() {
//		Cursor cursor = dbAdapter.getAllUsersRows();
//		
//		//data from DB
//		// @TODO need to add more columns for table relation between Users and Devices table
//		String fullname = DBAdapter.KEY_U_FIRSTNAME + " " + DBAdapter.KEY_U_LASTNAME;
//		String[] fromDB = new String[] { fullname, DBAdapter.KEY_U_TITLE};
//		
//		//mapping data with userlayout.xml
//		int[] toListView = new int[] {R.id.fn, R.id.tt};
//		
//		sca = new SimpleCursorAdapter(this, R.layout.userlayout, cursor, fromDB, toListView, 0);
//		ListView lv = (ListView) findViewById(R.id.usrList);
//		lv.setAdapter(sca);
//
//	}
//
//	private void openDB() {
//		dbAdapter = new DBAdapter(this);
//		dbAdapter.open();
//	}
	
	private ArrayList<String> results = new ArrayList<String>();
	private String usersTable = DBAdapter.USERSDB_TABLE;
	private SQLiteDatabase newDB;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		openAndQueryDatabase();
		displayResultList();
	}

	private void displayResultList() {
		TextView tView = new TextView(this);
        tView.setText("This is Users data from database, click to edit");
        getListView().addHeaderView(tView);
        
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results));
        getListView().setTextFilterEnabled(true);
	}

	private void openAndQueryDatabase() {
		try{
			DBAdapter.DatabaseHelper dbHelper = new DBAdapter.DatabaseHelper(this.getApplicationContext());
			newDB = dbHelper.getWritableDatabase();
			Cursor c = newDB.rawQuery("SELECT Firstname, Lastname FROM " + usersTable, null);
			
			if (c != null) {
				if (c.moveToFirst()) {
					do {
						String fn = c.getString(c.getColumnIndex("Firstname"));
						String ln = c.getString(c.getColumnIndex("Lastname"));
						results.add("First Name: " + fn + "\n" + "Last Name: " + ln);
					} while (c.moveToNext());
				}
			}
		} catch (SQLiteException sqlite){
			Log.e(getClass().getSimpleName(), "Could not create or Open the database");
		} finally {
			if (newDB != null)
				newDB.execSQL("DELETE FROM " + usersTable);
				newDB.close();
		}
	}
}
