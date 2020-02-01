package com.example.bayarkursus;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

//import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class main extends Activity {
	
	Button lgn,clr;
	EditText et,pass;
	TextView view;
	HttpResponse response;
	HttpPost httppost;
	StringBuffer buffer;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ButtonClear();
		
		lgn = (Button)findViewById(R.id.buttonLogin);
		clr = (Button)findViewById(R.id.btnClear);
        et = (EditText)findViewById(R.id.u);
        pass = (EditText)findViewById(R.id.p);
        view = (TextView)findViewById(R.id.view);
        
		
        lgn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = ProgressDialog.show(main.this, "", 
                        "Validating user...", true);
				 new Thread(new Runnable() {
					    public void run() {
					    	login();					      
					    }
			     }).start();				
			}
		});
    }
	void login() {
		try {
			
			httpclient= new DefaultHttpClient();
			//check koneksi ke database di web
			httppost = new HttpPost("http://yuelin.000webhostapp.com/check.php");
			//get data username
			nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("username",et.getText().toString().trim()));
			nameValuePairs.add(new BasicNameValuePair("password",pass.getText().toString().trim()));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			response = httpclient.execute(httppost);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost, responseHandler);
			System.out.println("Response : " + response); 
			runOnUiThread(new Runnable() {
			    public void run() {
			    	view.setText(response + " " );
					dialog.dismiss();
			    }
			});
			if(response.equalsIgnoreCase("sukses")){
				runOnUiThread(new Runnable() {
				    public void run() {
				    	Toast.makeText(main.this,"Login Sukses", Toast.LENGTH_SHORT).show();
				    	et.setText("");
						pass.setText("");
				    }
				});
				
				startActivity(new Intent(main.this, listmenu.class));
			    }else{
					showAlert();				
				}
			
		}catch(Exception e){
			dialog.dismiss();
			System.out.println("Exception : " + e.getMessage());
		}
	}
	
	public void ButtonClear() {
		clr = (Button)findViewById(R.id.btnClear);
		clr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			clear();
			}
		});
	}
	
	public void clear() {
		et.setText("");
		pass.setText("");
	}
	
		public void showAlert(){
			main.this.runOnUiThread(new Runnable() {
			    public void run() {
			    	AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
			    	builder.setTitle("Login Error.");
			    	builder.setMessage("Periksa Kembali Username dan Password")  
			    	       .setCancelable(false)
			    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			    	           public void onClick(DialogInterface dialog, int id) {
			    	           }
			    	       });		    	       
			    	AlertDialog alert = builder.create();
			    	alert.show();		    	
			    }
			});
		}
}