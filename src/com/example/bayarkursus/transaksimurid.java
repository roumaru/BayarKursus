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

public class transaksimurid extends Activity {
	Button btnOk;
	TextView txtNoTelpon, txtNmMurid, txtTunggakan, txtBulan, txtTahun, txtBiaya;
	EditText tahun, tanggal_transaksi, keterangan, bayaran;
	Spinner s1;
	HttpPost httpPost;
	HttpResponse httpResponse;
	HttpClient httpClient;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;
	String idMurid, nama_murid, no_telpon, dtahun,dbulan;
	private String url1 = "http://yuelin.000webhostapp.com/cariwargaditrans.php";
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private JSONObject jObject1;
	private String jsonResult1 ="";
	Date dt = new Date();
	String[] total;
	String totalHasil;
	
	String[] club = {"Januari", "February", "Maret", "April", "Mei", "Juni",
			"Juli", "Agustus", "September", "Oktober", "November", "Desember"};
	
	int mYear, mMonth, mDay;
	static final int DATE_DIALOG_ID = 1;
	
	String tempBulan;
	protected void onCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaksimurid);
		
		idMurid = getIntent().getStringExtra("id_murid");
		no_telpon = getIntent().getStringExtra("no_telpon");
		nama_murid = getIntent().getStringExtra("nama_murid");
		dbulan = getIntent().getStringExtra("bulan");
		dtahun = getIntent().getStringExtra("tahun");
		
		btnOk = (Button)findViewById(R.id.simpan);
		txtNoTelpon = (TextView)findViewById(R.id.NoTelpon);
		txtNmMurid = (TextView)findViewById(R.id.NmMurid);
		txtBulan = (TextView)findViewById(R.id.Bulan);
		txtTahun = (TextView)findViewById(R.id.tahun);
		txtTunggakan = (TextView)findViewById(R.id.txtTunggakan);
		txtBiaya = (TextView)findViewById(R.id.Biaya);
		bayaran = (EditText)findViewById(R.id.txtiuran);
		s1 = (Spinner)findViewById(R.id.spinnerBln);
		
		txtNoTelpon.setText(getIntent().getStringExtra("no_telpon"));
		txtNmMurid.setText(getIntent().getStringExtra("nama_murid"));
		txtBulan.setText(getIntent().getStringExtra("dbulan"));
		txtTahun.setText(getIntent().getStringExtra("dtahun"));
		txtBiaya.setText("Rp"+getIntent().getIntExtra("total",0));
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, club);
		s1.setAdapter(adapter);
		s1.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	tanggal_transaksi = (EditText)findViewById(R.id.dateTransaksi);
	final Calendar c = Calendar.getInstance();
	mYear = c.get(Calendar.YEAR);
	mMonth = c.get(Calendar.MONTH);
	mDay = c.get(Calendar.DAY_OF_MONTH);
	
	tanggal_transaksi.setOnTouchListener(new OnTouchListener() {
		
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			showDialog(DATE_DIALOG_ID);
			return true;
		}
	});
	
	//tombol proses
	btnOk.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(validasi()==0) {
				dialog = ProgressDialog.show(transaksimurid.this, "", "Loading...",true);
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						addTransaksi();
					}
				}).start();
			}
		}
	});
	}
	
	//cari id murid
	public void cariTransaksiMurid(String tmpTotal) {
		try {
			jsonResult1 =   getRequest(url1 + "?id_murid" + tmpTotal);
			jObject1 = new JSONObject(jsonResult1);
			JSONArray menuItemArray1 = jObject1.getJSONArray("total");
			
			total = new String[menuItemArray1.length()];
			System.out.println("id_murid : "+total);
			for (int i = 0 ;i < menuItemArray1.length();i++) {
				total[i] = menuItemArray1.getJSONObject(i).getString("total");
				System.out.println("id_murid 2 : "+total[i]);
				totalHasil = total[i];
			}
			System.out.println("id_murid 3 : "+totalHasil);
			if (menuItemArray1.length() ==0) {
				Toast.makeText(getApplicationContext(), "id_murid Tidak ada = 0", Toast.LENGTH_LONG).show();
			}
			
		}   catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	void addTransaksi() {
		try {
			//hasil pencarian murid
			cariTransaksiMurid(idMurid);
			
			//jika ada id_murid maka akan update
			if(totalHasil.equalsIgnoreCase("1")) {
				System.out.println("Update");
				
				//insert ke history
				addHistory();
				
				httpClient = new DefaultHttpClient();
				httpPost = new HttpPost("https://yuelin.000webhostapp.com/transaksiupdate.php");
				//add data
				nameValuePairs = new ArrayList<NameValuePair>(5);
				
				if (s1.getSelectedItem().toString().equalsIgnoreCase("Januari")) {
					tempBulan = "01";
				}else if (s1.getSelectedItem().toString().equalsIgnoreCase("Februari")) {
					tempBulan = "02";
				}else if (s1.getSelectedItem().toString().equalsIgnoreCase("Maret")) {
					tempBulan = "03";
				}else if (s1.getSelectedItem().toString().equalsIgnoreCase("April")) {
					tempBulan = "04";
				}else if (s1.getSelectedItem().toString().equalsIgnoreCase("Mei")) {
					tempBulan = "05";
				}else if (s1.getSelectedItem().toString().equalsIgnoreCase("Juni")) {
					tempBulan = "06";
				}else if (s1.getSelectedItem().toString().equalsIgnoreCase("juli")) {
					tempBulan = "07";
				}else if (s1.getSelectedItem().toString().equalsIgnoreCase("Agustus")) {
					tempBulan = "08";
				}else if (s1.getSelectedItem().toString().equalsIgnoreCase("September")) {
					tempBulan = "09";
				}else if (s1.getSelectedItem().toString().equalsIgnoreCase("Oktober")) {
					tempBulan = "10";
				}else if (s1.getSelectedItem().toString().equalsIgnoreCase("November")) {
					tempBulan = "11";
				}else if (s1.getSelectedItem().toString().equalsIgnoreCase("Desember")) {
					tempBulan = "12";
				}
				nameValuePairs.add(new BasicNameValuePair("id_murid", idMurid));
				nameValuePairs.add(new BasicNameValuePair("no_telpon", no_telpon));
				nameValuePairs.add(new BasicNameValuePair("nama_murid", nama_murid));
				nameValuePairs.add(new BasicNameValuePair("trans_date", tanggal_transaksi.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("bulan", tempBulan));
				nameValuePairs.add(new BasicNameValuePair("tahhun", tahun.getText().toString().trim()));
				nameValuePairs.add(new BasicNameValuePair("keterangan", keterangan.getText().toString().trim()));
				nameValuePairs.add(new BasicNameValuePair("total", bayaran.getText().toString().trim()));
				
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				httpResponse = httpClient.execute(httpPost);
		}else {
			//jika tidak ada id murid, maka instert
			System.out.println("Insert");
			addHistory();
			
			httpClient = new DefaultHttpClient();
			httpPost = new HttpPost("https://yuelin.000webhostapp.com/transaksi.php");
			//add data
			nameValuePairs = new ArrayList<NameValuePair>(5);
			if (s1.getSelectedItem().toString().equalsIgnoreCase("Januari")) {
				tempBulan = "01";
			}else if (s1.getSelectedItem().toString().equalsIgnoreCase("Februari")) {
				tempBulan = "02";
			}else if (s1.getSelectedItem().toString().equalsIgnoreCase("Maret")) {
				tempBulan = "03";
			}else if (s1.getSelectedItem().toString().equalsIgnoreCase("April")) {
				tempBulan = "04";
			}else if (s1.getSelectedItem().toString().equalsIgnoreCase("Mei")) {
				tempBulan = "05";
			}else if (s1.getSelectedItem().toString().equalsIgnoreCase("Juni")) {
				tempBulan = "06";
			}else if (s1.getSelectedItem().toString().equalsIgnoreCase("juli")) {
				tempBulan = "07";
			}else if (s1.getSelectedItem().toString().equalsIgnoreCase("Agustus")) {
				tempBulan = "08";
			}else if (s1.getSelectedItem().toString().equalsIgnoreCase("September")) {
				tempBulan = "09";
			}else if (s1.getSelectedItem().toString().equalsIgnoreCase("Oktober")) {
				tempBulan = "10";
			}else if (s1.getSelectedItem().toString().equalsIgnoreCase("November")) {
				tempBulan = "11";
			}else if (s1.getSelectedItem().toString().equalsIgnoreCase("Desember")) {
				tempBulan = "12";
			}
			
			nameValuePairs.add(new BasicNameValuePair("id_murid", idMurid));
			nameValuePairs.add(new BasicNameValuePair("no_telpon", no_telpon));
			nameValuePairs.add(new BasicNameValuePair("nama_murid", nama_murid));
			nameValuePairs.add(new BasicNameValuePair("trans_date", tanggal_transaksi.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("bulan", tempBulan));
			nameValuePairs.add(new BasicNameValuePair("tahhun", tahun.getText().toString().trim()));
			nameValuePairs.add(new BasicNameValuePair("keterangan", keterangan.getText().toString().trim()));
			nameValuePairs.add(new BasicNameValuePair("total", bayaran.getText().toString().trim()));
			
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			httpResponse = httpClient.execute(httpPost);
		}
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
				tanggal_transaksi.setText("");
				tahun.setText("");
				keterangan.setText("");
				dialog.dismiss();
			}
		});
		
		} catch (Exception e) {
			// TODO: handle exception
			dialog.dismiss();
			System.out.println("Exception : "+e.getMessage());
			System.out.println("null");
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener , mYear, mMonth, mDay);
		}
		return null;
	}
	
	private static String LPad(String schar, String spad, int len) {
		String sret = schar;
		for (int i = sret.length(); i < len ; i++) {
			sret = spad + sret;
		}
		return new String(sret);
	}
	
	private DatePickerDialog.OnDateSetListener mDateSetListener = 
			new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					CharSequence strDate = null;
					Time choosenDate = new Time();
					choosenDate.set(dayOfMonth, monthOfYear, year);
					long dtDob = choosenDate.toMillis(true);
					strDate = DateFormat.format("yyyy-MM-dd", dtDob);
					tanggal_transaksi.setText(strDate);
				}
			};
			
	//send data ke server
	public String getRequest(String Url) {
		String sret = "";
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(Url);
		try {
			HttpResponse response = client.execute(request);
			sret = request(response);
		}catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, "Gagal "+sret, Toast.LENGTH_SHORT).show();
		}
		return sret;
	}
	
	//get data dari server
	public static String request(HttpResponse response) {
		String result = "";
		try {
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine())!=null) {
				str.append(line + "\n");
			}
			in.close();
			result = str.toString();
		}catch (Exception e) {
			// TODO: handle exception
			result = "Error";
		}
		return result;
	}
	
	//add ke tabel history
	public void addHistory()throws ClientProtocolException, IOException{
		httpClient=new DefaultHttpClient();
		httpPost= new HttpPost("https://yuelin.000webhostapp.com/history.php");
		
nameValuePairs = new ArrayList<NameValuePair>(5);
		
		if (s1.getSelectedItem().toString().equalsIgnoreCase("Januari")){
			tempBulan = "01";
		}else if(s1.getSelectedItem().toString().equalsIgnoreCase("February")){
			tempBulan = "02";
		}else if(s1.getSelectedItem().toString().equalsIgnoreCase("Maret")){
			tempBulan = "03";
		}else if(s1.getSelectedItem().toString().equalsIgnoreCase("April")){
			tempBulan = "04";
		}else if(s1.getSelectedItem().toString().equalsIgnoreCase("Mei")){
			tempBulan = "05";
		}else if(s1.getSelectedItem().toString().equalsIgnoreCase("Juni")){
			tempBulan = "06";
		}else if(s1.getSelectedItem().toString().equalsIgnoreCase("Juli")){
			tempBulan = "07";
		}else if(s1.getSelectedItem().toString().equalsIgnoreCase("Agustus")){
			tempBulan = "08";
		}else if(s1.getSelectedItem().toString().equalsIgnoreCase("September")){
			tempBulan = "09";
		}else if(s1.getSelectedItem().toString().equalsIgnoreCase("Oktober")){
			tempBulan = "10";
		}else if(s1.getSelectedItem().toString().equalsIgnoreCase("November")){
			tempBulan = "11";
		}else if(s1.getSelectedItem().toString().equalsIgnoreCase("Desember")){
			tempBulan = "12";
		}
		
		nameValuePairs.add(new BasicNameValuePair("id_murid", idMurid));
		nameValuePairs.add(new BasicNameValuePair("no_telpon", no_telpon));
		nameValuePairs.add(new BasicNameValuePair("nama_murid", nama_murid));
		nameValuePairs.add(new BasicNameValuePair("trans_date", tanggal_transaksi.getText().toString()));
		nameValuePairs.add(new BasicNameValuePair("bulan", tempBulan));
		nameValuePairs.add(new BasicNameValuePair("tahhun", tahun.getText().toString().trim()));
		nameValuePairs.add(new BasicNameValuePair("keterangan", keterangan.getText().toString().trim()));
		nameValuePairs.add(new BasicNameValuePair("total", bayaran.getText().toString().trim()));
		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		//Execute HTTP Post Request
		httpResponse=httpClient.execute(httpPost);
	}
	
	public int validasi() {
		
		tahun = (EditText)findViewById(R.id.txtTahun);
		s1 = (Spinner)findViewById(R.id.spinnerBln);
		tanggal_transaksi = (EditText)findViewById(R.id.dateTransaksi);
		keterangan = (EditText)findViewById(R.id.txtKeterangan);
		int error = 0;
		
		try {
			if (tahun.getText().length()==0) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
				alertbox.setMessage("Masukan Tahun");
				alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				alertbox.show();
				error = 1;
			}else if (tanggal_transaksi.getText().length()==0) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
				alertbox.setMessage("Atur Waktu Transaksi");
				alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				alertbox.show();
				error = 1;
			}else if (keterangan.getText().length()==0) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
				alertbox.setMessage("Tambahkan keterangan");
				alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				alertbox.show();
				error = 1;
				
			}else if (bayaran.getText().length()==0) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
				alertbox.setMessage("Tambahkan Bayaran 6000");
				alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				alertbox.show();
				error = 1;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("YOUR ERROR TAG HERE", "Copying failed",e);
		}
		return error;
	}
}

