package com.example.bayarkursus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;

public class editMuridActivity extends Activity{
	Button btnEdit;
	EditText txtNoTelpon, txtNmMurid;
	HttpPost httppost;
	HttpResponse httpresponse;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		
		btnEdit = (Button)findViewById(R.id.btnEditSimpan);
		txtNoTelpon = (EditText)findViewById(R.id.txtNoTelpon);
		txtNmMurid = (EditText)findViewById(R.id.txtNmMurid);
		
		txtNoTelpon.setText(getIntent().getStringExtra("no_telpon"));
		txtNmMurid.setText(getIntent().getStringExtra("nama_murid"));
		
		btnEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(validasi()==0) {
					dialog = ProgressDialog.show(editMuridActivity.this, "", "Loading...",true);
					new Thread (new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							editUser();
						}
					}).start();
				}
			}
		});
		
	}
	void editUser () {
		try {
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost("http://yuelin.000webhostapp.com/updatewarga.php");
			//add data
			nameValuePairs = new ArrayList<NameValuePair>(3);
			
			nameValuePairs.add(new BasicNameValuePair("id_murid", getIntent().getStringExtra("id_murid")));
			nameValuePairs.add(new BasicNameValuePair("no_telpon", txtNoTelpon.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("nama_murid", txtNmMurid.getText().toString()));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			httpresponse = httpclient.execute(httppost);
			
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "Edit data sukses", Toast.LENGTH_LONG).show();
						viewMuridActivity.ma.RefreshList();
						finish();
					}
				});
			
		}catch (Exception e) {
			// TODO: handle exception
			dialog.dismiss();
			System.out.println("Exception : "+e.getMessage());
		}
	}
	//method validasi
	public int validasi() {
		txtNoTelpon = (EditText)findViewById(R.id.txtNoTelpon);
		txtNmMurid = (EditText)findViewById(R.id.txtNmMurid);
		int error = 0;
		
		try {
			if(txtNoTelpon.getText().length()==0) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
				alertbox.setMessage("Masukan No Telpon Murid");
				alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				alertbox.show();
				error = 1;
			}else if(txtNmMurid.getText().length()==0) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
				alertbox.setMessage("Masukan Nama Murid");
				alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				alertbox.show();
				error = 1;
			}
		}catch (Exception e) {
			Log.e("Your Error Tag Here","Copying failed",e);
			// TODO: handle exception
		} return error;
	}
}
