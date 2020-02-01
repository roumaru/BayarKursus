package com.example.bayarkursus;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.text.DateFormatSymbols;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DetailTransaksiMurid extends Activity {
	Button btnok;
	TextView txtNmMurid,txtNoTelpon, txtTunggakan, txtBulan, txtTahun, txtBiaya;
	HttpPost httppost;
	HttpResponse httpresponse;
	HttpClient httpclient;
	List<NameValuePair> nameValuePair;
	ProgressDialog dialog = null;
	String[]Bulan;
	String[]Tahun;
	String[]murid;
	String totalHasil, totalBulan, totalTahunl;
	int biaya;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailtransaksi);
		
		btnok = (Button)findViewById(R.id.okdetail);
		txtNoTelpon = (TextView)findViewById(R.id.txtNoTelpon);
		txtNmMurid = (TextView)findViewById(R.id.txtNmMurid);
		txtTunggakan = (TextView)findViewById(R.id.txtTunggakan);
		txtBulan = (TextView)findViewById(R.id.txtBulan);
		txtTahun = (TextView)findViewById(R.id.Tahun);
		txtBiaya = (TextView)findViewById(R.id.TotalBiaya);
		
		txtNoTelpon.setText(getIntent().getStringExtra("no_telpon"));
		txtNmMurid.setText(getIntent().getStringExtra("nama_murid"));
		txtBulan.setText(getIntent().getStringExtra("bulan"));
		txtTahun.setText(getIntent().getStringExtra("tahun"));
		txtTunggakan.setText(getIntent().getStringExtra("total")+"bulan");
		txtBiaya.setText("Rp "+getIntent().getIntExtra("biaya",0));
		
		btnok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnok();
			}
		});
		
	}
	
	public void btnok() {
		Intent i = new Intent(getApplicationContext(),transaksimurid.class);
		i.putExtra("id_murid", getIntent().getStringExtra("id_murid"));
		i.putExtra("no_telpon", getIntent().getStringExtra("no_telpon"));
		i.putExtra("nama_murid", getIntent().getStringExtra("nama_murid"));
		i.putExtra("dbulan", getIntent().getStringExtra("bulan"));
		i.putExtra("dtahun", getIntent().getStringExtra("tahun"));
		i.putExtra("total", getIntent().getStringExtra("total")+" bulan");
		i.putExtra("biaya", getIntent().getIntExtra("biaya",0));
		startActivity(i);
	}
}
