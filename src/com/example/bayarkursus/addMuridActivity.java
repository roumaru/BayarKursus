package com.example.bayarkursus;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class addMuridActivity extends Activity{
	Button btnAdd;
	EditText txtNoTelpon, txtNmMurid;
	HttpPost httppost;
	HttpClient httpclient;
	HttpResponse httpresponse;
	List<NameValuePair> nameValuePair;
	ProgressDialog dialog = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addmurid);
		
		btnAdd = (Button)findViewById(R.id.btnAddSimpan);
		txtNoTelpon = (EditText)findViewById(R.id.txtNoTelpon);
		txtNmMurid = (EditText)findViewById(R.id.txtNmMurid);
		
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(validasi()==0) {
					dialog = ProgressDialog.show(addMuridActivity.this, "", "Loading ...",true);
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							addUser();
						}
					}).start();
					
				}
			}
		});
	}
	void addUser() {
		try {
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost("http://yuelin.000webhostapp.com/addwarga.php");
			nameValuePair = new ArrayList<NameValuePair>(2);
			
			nameValuePair.add(new BasicNameValuePair("no_telpon", txtNoTelpon.getText().toString().trim()));
			nameValuePair.add(new BasicNameValuePair("nama_murid", txtNmMurid.getText().toString().trim()));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			httpresponse = httpclient.execute(httppost);
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
					viewMuridActivity.ma.RefreshList();
					finish();
				}
			});
		}catch (Exception e) {
			dialog.dismiss();
			System.out.println("Exception : "+e.getMessage());
			// TODO: handle exception
		}
	}
	
	public int validasi() {
		txtNoTelpon = (EditText)findViewById(R.id.txtNoTelpon);
		txtNmMurid = (EditText)findViewById(R.id.txtNmMurid);
		int error = 0;
		
		try {
			if(txtNoTelpon.getText().length()==0) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
				alertbox.setMessage("Masukan No Telpon");
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
			Log.e("YOUR ERROR TAG HERE","COPYING failed",e);
			// TODO: handle exception
		}
		return error;
	}
}
