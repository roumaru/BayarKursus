package com.example.bayarkursus;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

@SuppressWarnings("deprecation")
public class viewMuridActivity extends Activity {
	private JSONObject jObject;
	private String jsonResult ="";
	private String url = "http://yuelin.000webhostapp.com/daftarwarga.php";
	private String url2 = "http://yuelin.000webhostapp.com/deletewarga.php";
	String [] idMurid;
	String [] noTelpon;
	String [] namaMurid;
	Context aa;
	Menu menu;
	
	public static viewMuridActivity ma;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmurid);
		ma = this;
		RefreshList();
	}
	
	public void RefreshList() {
		try {
			jsonResult = getRequest(url);
			jObject = new JSONObject(jsonResult);
			JSONArray menuitem = jObject.getJSONArray("murid");
			
			idMurid =  new String [menuitem.length()];
			noTelpon = new String [menuitem.length()];
			namaMurid = new String [menuitem.length()];
			
				for (int i = 0; i < menuitem.length(); i++)
				{
					idMurid[i] = menuitem.getJSONObject(i).getString("id_murid").toString();
					noTelpon[i] = menuitem.getJSONObject(i).getString("no_telpon").toString();
					namaMurid[i] = menuitem.getJSONObject(i).getString("nama_murid").toString();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		
		ListView listsiswa = (ListView)findViewById(R.id.listsiswa);
		listsiswa.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, namaMurid));
		
		listsiswa.setSelected(true);
		listsiswa.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				final String selectionId = idMurid[arg2];
				final String selectionNomor = noTelpon[arg2];
				final String selectionNama = namaMurid[arg2];
				
				//memunculkan opsi edit dan delete
				final CharSequence[] dialogitem = {"EDIT","DELETE"};
				AlertDialog.Builder builder = new AlertDialog.Builder(viewMuridActivity.this);
				builder.setTitle("Please Choose Something");
				builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int item) {
						// TODO Auto-generated method stub
						switch (item) {
						case 0 :
							//jika pilih edit akan masuk ke sesi edit
							Intent i = new Intent(getApplicationContext(),editMuridActivity.class);
							i.putExtra("id_murid", selectionId);
							i.putExtra("no_telpon", selectionNomor);
							i.putExtra("nama_murid", selectionNama);
							startActivity(i);
						break;
						
						case 1 :
							//jika pilih delete masuk ke delete activity
						AlertDialog.Builder alertbox = new AlertDialog.Builder(viewMuridActivity.this);
						alertbox.setMessage("Yakin ingin dihapus data Murid?");
						
							//set pilihan yes
							alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									getRequest(url2 + "?id_murid="+ selectionId);
									RefreshList();
								}
							});
							//set pilihan NO
							alertbox.setNegativeButton("NO", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									
								}
							});
							alertbox.show();
						break;
						}
						
					}
				});
				builder.create().show();
			}
		});
		((ArrayAdapter)listsiswa.getAdapter()).notifyDataSetInvalidated();
	}
	
	//getRequest ke server database
	public String getRequest(String Url){

		String sret="";
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(Url);
		try{
			HttpResponse response = client.execute(request);
			sret =request(response);

		}catch(Exception ex){
			Toast.makeText(this,"Gagal "+sret, Toast.LENGTH_SHORT).show();
		}
		return sret;
	}
	//getResponse dari server
	public static String request(HttpResponse response) {
		String hasil = "";
		try {
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine())!= null){
				str.append(line + "\n");
			}
			in.close();
			hasil = str.toString();
		}catch (Exception ex) {
			hasil = "Error";
		}
		return hasil;
	}
	
	//membuat menu option
	
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		menu.add(0,1,0, "Tambah").setIcon(R.drawable.user);
		menu.add(0,2,0, "Home").setIcon(R.drawable.home);
		menu.add(0,3,0, "Back").setIcon(R.drawable.backicon);
		return true ;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//case tambah murid
		case 1 :
			Intent i = new Intent(viewMuridActivity.this,addMuridActivity.class);
			startActivity(i);
			return true;
		case 2 :
			RefreshList();
			return true;
		case 3 :
			finish();
			return true;
		}
		return false;
	}
}
