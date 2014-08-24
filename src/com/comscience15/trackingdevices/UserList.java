package com.comscience15.trackingdevices;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class UserList extends Activity{
	private DBAdapter dbAdapter;
	private SimpleCursorAdapter sca;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userslist);
		
		dbAdapter = new DBAdapter(this);
		openDB();
		displayUserList();
	}

	@SuppressLint("NewApi")
	private void displayUserList() {
		Cursor cursor = dbAdapter.getAllUsersRows();
		
		//data from DB
		// @TODO need to add more columns for table relation between Users and Devices table
		String[] fromDB = new String[] { DBAdapter.KEY_U_FIRSTNAME, DBAdapter.KEY_U_TITLE};
		
		//mapping data with userlayout.xml
		int[] toListView = new int[] {R.id.fn, R.id.tt};
		
		sca = new SimpleCursorAdapter(this, R.layout.userlayout, cursor, fromDB, toListView, 0);
		ListView lv = (ListView) findViewById(R.id.usrList);
		lv.setAdapter(sca);
		
//		lv.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> listView, View view,
//					int position, long id) {
//				
//			}
//		});
	}

	private void openDB() {
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
	}
}
