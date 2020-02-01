package com.example.bayarkursus;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormatSymbols;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import android.R;
import android.app.Activity;
import android.app.AlertDialog;
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

public class cariDataTransaksiMurid extends Activity {
	private JSONObject jObject;
	private String jsonResult ="";
	private JSONObject jObject1;
	private String jsonResult1 ="";
	private String url = "http://yuelin.000webhostapp.com/cariwarga.php";
	private String url1 = "http://yuelin.000webhostapp.com/tunggakan.php";
	String[] idWarga;
	String[] noRumah;
	String[] namaWarga = null;
	String[] total, dtahun,dbulan;
	String totalHasil;
	int biaya;
	Menu menu;
	Button btnPencarian;
	ProgressDialog dialog = null;
	Context aa;
	EditText txtWarga;
	public String terme_search;
	
	public static cariDataTransaksiMurid ma;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listtransaksi);
        
        //deklarasi widget dengan id pada layout
        btnPencarian = (Button)findViewById(R.id.btnCari);
        txtWarga = (EditText)findViewById(R.id.namaMurid);
        
        // buat perintah pada tombol pencarian
        btnPencarian.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pencarian();
			}
		});
    }
    
    // JSON tunggakan.php
    public void tunggakan(String tmpTunggakan) {
       	try {
          	jsonResult1 = getRequest(url1 + "?id_murid="+tmpTunggakan);
            
          	jObject1 = new JSONObject(jsonResult1);
          	totalHasil=jObject1.getString("total");
          	biaya=jObject1.getInt("hasil");
          	System.out.println("test : "+jsonResult1);
		if(biaya ==0){
				Toast.makeText(getApplicationContext(), "Tunggakan Tidak Ada = Lunas", Toast.LENGTH_LONG).show();}
          	
   		} catch (JSONException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
    }
    
    // JSON cariwarga.php
    public void pencarian() {
    	try {
        	txtWarga = (EditText)findViewById(R.id.namaMurid);
			terme_search = txtWarga.getText().toString();
    		
        	jsonResult = getRequest(url+"?nama_murid="+terme_search); 
        	System.out.println("nama Murid : "+jsonResult);
        	jObject = new JSONObject(jsonResult);
			JSONArray menuitemArray = jObject.getJSONArray("warga");

			idWarga = new String[menuitemArray.length()];
			noRumah = new String[menuitemArray.length()];
			namaWarga = new String[menuitemArray.length()];
			dbulan = new String[menuitemArray.length()];
			dtahun = new String[menuitemArray.length()];
			
			//System.out.println("nama Murid : "+namaWarga);
			
			for (int i = 0; i < menuitemArray.length(); i++)
			{
				idWarga[i] = menuitemArray.getJSONObject(i).getString("id_murid").toString();
				noRumah[i] = menuitemArray.getJSONObject(i).getString("no_telpon").toString();
				namaWarga[i] = menuitemArray.getJSONObject(i).getString("nama_murid").toString();
				if(menuitemArray.getJSONObject(i).get("bulan").equals(null))
				{
					
				dbulan[i]="0";
				}
				else
				{
				dbulan[i] = menuitemArray.getJSONObject(i).getString("bulan").toString();
				}
				
				if(menuitemArray.getJSONObject(i).get("tahun").equals(null))
				{
					
				dtahun[i]="Belum Pernah Transaksi";
				}
				else
				{
				dtahun[i] = menuitemArray.getJSONObject(i).getString("tahun").toString();
				}
			}
			
			if(menuitemArray.length() ==0 ){
				Toast.makeText(getApplicationContext(), "Data Tidak Ada", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ListView ListView01 = (ListView)findViewById(R.id.ListMurid);
        ListView01.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, namaWarga));
        
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final String selectionId = idWarga[arg2];
				final String selectionNoRumah = noRumah[arg2]; 
				final String selectionNmWarga = namaWarga[arg2];
				final String selectionBulan = dbulan[arg2]; 
				final String selectionTahun = dtahun[arg2];
				
				final CharSequence[] dialogitem = {"Detail Transaksi"};
		    	AlertDialog.Builder builder = new AlertDialog.Builder(cariDataTransaksiMurid.this);
		        builder.setTitle("Pilih ?");
		        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {

		        	public void onClick(DialogInterface dialog, int item) {
						switch(item){
						case 0 :
							tunggakan(selectionId);
							Log.e("id_murid", selectionId);
							
							// pindah ke tampilan detail dengan mengirimkan variable
							Intent i = new Intent(getApplicationContext(), DetailTransaksiMurid.class);  
							i.putExtra("id_murid", selectionId);
							i.putExtra("no_telpon", selectionNoRumah);
							i.putExtra("nama_murid", selectionNmWarga);
							i.putExtra("bulan", ConMonth(selectionBulan));
							i.putExtra("tahun", selectionTahun);
					    	i.putExtra("total", totalHasil);
					    	i.putExtra("biaya", biaya);
					    	startActivity(i);
							break;
						}
					}
				});
		        builder.create().show();
			}});

        ((ArrayAdapter)ListView01.getAdapter()).notifyDataSetInvalidated();
        
    }
    
    
    /**
	 * Method untuk Mengirimkan data ke server
	 */
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
	/**
	 * Method untuk Menerima data dari server
	 */
	public static String request(HttpResponse response){
		String result = "";
		try{
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder str = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null){
				str.append(line + "\n");
			}
			in.close();
			result = str.toString();
		}catch(Exception ex){
			result = "Error";
		}
		return result;
	}    

	// kondisi mengambil bulan dari database tbl_transaksi dan menampilkannya di layout
	public String getMonth(int month) {     
		return new DateFormatSymbols().getMonths()[month-1]; 
		}
	public String ConMonth(String bulan)
	{
		if(bulan.equals("01"))
		{
			bulan="Januari";
		}
		else if(bulan.equals("02"))
		{
			bulan="Februari";
		}
		else if(bulan.equals("03"))
		{
			bulan="Maret";
		}
		else if(bulan.equals("04"))
		{
			bulan="April";
		}
		else if(bulan.equals("05"))
		{
			bulan="Mei";
		}
		else if(bulan.equals("06"))
		{
			bulan="Juni";
		}
		else if(bulan.equals("07"))
		{
			bulan="Juli";
		}
		else if(bulan.equals("08"))
		{
			bulan="Agustus";
		}
		else if(bulan.equals("09"))
		{
			bulan="September";
		}
		else if(bulan.equals("10"))
		{
			bulan="Oktober";
		}
		else if(bulan.equals("11"))
		{
			bulan="November";
		}
		else if(bulan.equals("12"))
		{
			bulan="Desember";
		}
		else
		{
			bulan ="Belum Pernah Transaksi";
		}
		return bulan;	
	}
}