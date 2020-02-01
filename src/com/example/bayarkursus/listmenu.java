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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class listmenu extends Activity{
	final Context context = this;
		
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmenu);
		
		final ArrayList<menu> searchResults = GetSearchResults();
		final ListView lvl = (ListView) findViewById(R.id.list_menu);
		lvl.setAdapter(new MenuAdapter(context, searchResults));
		lvl.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> a, View v, int position, long id){
				Object o = lvl.getItemAtPosition(position);
				menu fullObject = (menu)o;
				
				if(fullObject.getMenuName().toString().equalsIgnoreCase("Kelola Murid")) {
					Bundle b = new Bundle();
					Toast.makeText(context, "Entering to : ..."+" "+ fullObject.getMenuName(),Toast.LENGTH_LONG).show();
					Intent intent = new Intent(context, viewMuridActivity.class);
					intent.putExtras(b);
					startActivityForResult(intent,0);
				}else if(fullObject.getMenuName().toString().equalsIgnoreCase("Kelola Pembayaran Murid")) {
					Bundle b = new Bundle();
					Toast.makeText(context, "Entering to : ..."+" "+ fullObject.getMenuName(),Toast.LENGTH_LONG).show();
					Intent intent = new Intent(context, cariDataTransaksiMurid.class);
					intent.putExtras(b);
					startActivityForResult(intent,0);
				}else if(fullObject.getMenuName().toString().equalsIgnoreCase("Laporan Murid")) {
					Bundle b = new Bundle();
					Toast.makeText(context, "Entering to : ..."+" "+ fullObject.getMenuName(),Toast.LENGTH_LONG).show();
					Intent intent = new Intent(context, laporandatamurid.class);
					intent.putExtras(b);
					startActivityForResult(intent,0);
				}else if(fullObject.getMenuName().toString().equalsIgnoreCase("Laporan Pembayaran Murid")) {
					Bundle b = new Bundle();
					Toast.makeText(context, "Entering to : ..."+" "+ fullObject.getMenuName(),Toast.LENGTH_LONG).show();
					Intent intent = new Intent(context, laporanpembayaran.class);
					intent.putExtras(b);
					startActivityForResult(intent,0);
				}else if(fullObject.getMenuName().toString().equalsIgnoreCase("LogOut")) {
					Bundle b = new Bundle();
					Toast.makeText(context, "Logging Out ..."+" "+ fullObject.getMenuName(),Toast.LENGTH_LONG).show();
					finish();
				}
			}
			
		});
	}

	private ArrayList<menu> GetSearchResults() {
		// TODO Auto-generated method stub
		ArrayList<menu> res = new ArrayList<menu>();
		
		menu data = new menu();
		data.setMenuName("Kelola Murid");
		res.add(data);
		
		data = new menu();
		data.setMenuName("Kelola Pembayaran Murid");
		res.add(data);
		
		data = new menu();
		data.setMenuName("Laporan Murid");
		res.add(data);
		
		data = new menu();
		data.setMenuName("Laporan Pembayaran Murid");
		res.add(data);
		
		data = new menu();
		data.setMenuName("LogOut");
		res.add(data);
		
		return res;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			alert.setMessage("Yakin Akan Keluar ?");
			
			alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					finish();
				}
			});
				alert.show();
				return true;
		}
	return super.onKeyDown(keyCode, event);
	}
}