package com.comscience15.trackingdevices;

import java.util.Calendar;

import android.widget.DatePicker;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Devices extends Activity{
	private String TAG = "TRACKINGDEVICES";
	private TextView tvDB, tvOS, tvPF, tvZT, tvDCO, tvDCI, tvPCO, tvPCI, tvDFI, tvPD;
	private Button btUpdate, btAdd, pickDateChkOut, pickDateChkIn, pickDateDeviceIn ;
	private EditText editTextChkOutDate, editTextChkInDate, editTextFirstDeviceInDate;
	private int mYear, mMonth, mDay;
	static final int DATE_DIALOG_ID = 1;
	private String etDB, etOS, etPF, etZT, etDCO, etDCI, etPCO, etPCI, etDFI, etPD;
	DBAdapter db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devices);
		
		tvDB = (TextView) findViewById(R.id.etDeviceBrand);
		tvOS = (TextView) findViewById(R.id.etOS);
		tvPF = (TextView) findViewById(R.id.etPlatform);
		tvZT = (TextView) findViewById(R.id.etzTag);
		tvDCO = (TextView) findViewById(R.id.etDateChkOut);
		tvDCI = (TextView) findViewById(R.id.etDateChkIn);
		tvPCO = (TextView) findViewById(R.id.etPersonChkOut);
		tvPCI = (TextView) findViewById(R.id.etPersonChkIn);
		tvDFI = (TextView) findViewById(R.id.etDateInInventory);
		tvPD = (TextView) findViewById(R.id.etProDevice);
		
		btUpdate = (Button) findViewById(R.id.updateDeviceInfo);
		btAdd = (Button) findViewById(R.id.addDevice);
		pickDateChkOut = (Button) findViewById(R.id.pickDateChkOut);
		pickDateChkIn = (Button) findViewById(R.id.pickDateChkIn);
		pickDateDeviceIn = (Button) findViewById(R.id.pickDateDeviceIn);
		
		/* 
		 * EditText and Button to select Date
		 */
		editTextChkOutDate = (EditText) findViewById(R.id.etDateChkOut);
		editTextChkOutDate.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				showDialog(1);
				updateChkOut();
				return false;
			}
		});
		pickDateChkOut.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				showDialog(1);
				updateChkOut();
			}
		});
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@//
		
		/*
		 * EditText and Button to select Date
		 */
		editTextChkInDate = (EditText) findViewById(R.id.etDateChkIn);
		editTextChkInDate.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				showDialog(2);
				updateChkIn();
				return false;
			}
		});
		pickDateChkIn.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				showDialog(2);
				updateChkIn();
			}
		});
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@//
		
		/*
		 * EditText and Button to select Date
		 */
		editTextFirstDeviceInDate = (EditText) findViewById(R.id.etDateInInventory);
		editTextFirstDeviceInDate.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				showDialog(3);
				updateDeviceIn();
				return false;
			}
		});
		pickDateDeviceIn.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				showDialog(3);
				updateDeviceIn();
			}
		});
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@//
		
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {

		case 1:
			return new DatePickerDialog(this,
					updateChkOut,
				mYear, mMonth, mDay);
		case 2:
			return new DatePickerDialog(this,
					updateChkIn,
				mYear, mMonth, mDay);
		case 3:
			return new DatePickerDialog(this,
					updateFirstIn,
				mYear, mMonth, mDay);
		}
		return null;
	}
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {

		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		}
	}    
	private void updateChkOut() {
		tvDCO.setText(
			new StringBuilder()
			// Month is 0 based so add 1
			.append(mMonth + 1).append("/")
			.append(mDay).append("/")
			.append(mYear).append(" "));
	}
	
	private void updateChkIn() {
		tvDCI.setText(
				new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("/")
				.append(mDay).append("/")
				.append(mYear).append(" "));
	}
	
	private void updateDeviceIn() {
		tvDFI.setText(
				new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("/")
				.append(mDay).append("/")
				.append(mYear).append(" "));
	}
	
	private DatePickerDialog.OnDateSetListener updateChkOut =
		new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateChkOut();
		}
	};
	
	private DatePickerDialog.OnDateSetListener updateChkIn =
		new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateChkIn();
		}
	};
	
	private DatePickerDialog.OnDateSetListener updateFirstIn =
		new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDeviceIn();
		}
	};
	
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
	
	//add device button click
	public void addDevice(View V){
		etDB = tvDB.getText().toString();
		etOS = tvOS.getText().toString();
		etPF = tvPF.getText().toString();
		etZT = tvZT.getText().toString();
		etDCO = tvDCO.getText().toString();
		etDCI = tvDCI.getText().toString();
		etPCO = tvPCO.getText().toString();
		etPCI = tvPCI.getText().toString();
		etDFI = tvDFI.getText().toString();
		etPD = tvPD.getText().toString();
		
		openDB();
		long id = db.insertDeviceRow(etDB, etOS, etPF, etZT, etDCO, etDCI, etPCO, etPCI, etDFI, etPD);
//		long id = db.insertDeviceRow(etDB, etOS, etPF, etZT, etDCO, etDCI, etPCO, etPCI, etDFI);
		closeDB();
		
		tvDB.setText("");
		tvOS.setText("");
		tvPF.setText("");
		tvZT.setText("");
		tvDCO.setText("");
		tvDCI.setText("");
		tvPCO.setText("");
		tvPCI.setText("");
		tvDFI.setText("");
		tvPD.setText("");
		Toast.makeText(this,etDB + " " + etOS + " " + etZT +" is added", Toast.LENGTH_LONG).show();
		Log.i(TAG, etDB + " " + etOS + " " + etZT +" is added");
	}

	private void getAllRecord() {
		Cursor c = db.getAllDevicesRows();
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
				"Device Brand: " + c.getString(1) + " " + c.getString(2) + "\n" +
				"Device OS: " + c.getString(3) + "\n" +
				"Device zTag: " + c.getString(4), Toast.LENGTH_LONG).show();
	}
}
