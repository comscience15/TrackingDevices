package com.comscience15.trackingdevices;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Users extends Activity{
	private String TAG = "TRACKINGDEVICES";
	private TextView tvFN, tvLN, tvTitle, tvTeam;
	private Button btUpdate, btAdd;
	private String sFN, sLN, sTitle, sTeam;
	DBAdapter db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users);
		
		tvFN = (TextView)findViewById(R.id.etFirstName);
		tvLN = (TextView)findViewById(R.id.etLastName);
		tvTitle = (TextView)findViewById(R.id.etTitle);
		tvTeam = (TextView)findViewById(R.id.etTeam);
		btUpdate = (Button)findViewById(R.id.updateEmpInfo);
		btAdd = (Button)findViewById(R.id.addUsers);
	}
	
	private void openDB() {
		db = new DBAdapter(this);
		db.open();
	}
	
	private void closeDB() {
		db.close();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDB();
	}

	//addUsers button click
	public void addUsers(View V){
		sFN = tvFN.getText().toString();
		sLN = tvLN.getText().toString();
		sTitle = tvTitle.getText().toString();
		sTeam = tvTeam.getText().toString();
		
		openDB();
		long id = db.insertUserRow(sFN, sLN, sTitle, sTeam);
		closeDB();
		
		tvFN.setText("");
		tvLN.setText("");
		tvTitle.setText("");
		tvTeam.setText("");
		Toast.makeText(this,sFN + " " + sLN + " is added", Toast.LENGTH_LONG).show();
		Log.i(TAG, sFN + " " + sLN + " is added");
	}
	
	private void getAllRecord() {
		Cursor c = db.getAllUsersRows();
		if (c.moveToFirst()){
			do{
				displayToast(c);
			}while(c.moveToNext());
		}
		closeDB();
	}

	public void displayToast(Cursor c) {
		Toast.makeText(this, 
				"ID: "	+ c.getString(0) + "\n" +
				"Full Name: " + c.getString(1) + " " + c.getString(2) + "\n" +
				"Title: " + c.getString(3) + "\n" +
				"Team: " + c.getString(4), Toast.LENGTH_LONG).show();
	}

	//Update User from inventory button click
	public void updateEmpInv(View V){
			
	}
}
