package com.example.bayarkursus;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import android.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class laporandatamurid extends ListActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.laporandatamurid);
		
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String,String>>();
		//ArrayList<String> mylist = new ArrayList<String>();
		JSONObject json = JsonReportMurid.getJSONfromURL("http://yuelin.000webhostapp.com/reportwarga.php");
		
		try {
			JSONArray murid = json.getJSONArray("murid");
			for (int i = 0; i< murid.length(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				JSONObject jsonobj = murid.getJSONObject(i);
				
				map.put("id_murid", jsonobj.getString("id_murid"));
				map.put("no_telpon", jsonobj.getString("no_telpon"));
				map.put("nama_murid", jsonobj.getString("nama_murid"));
				mylist.add(map);
			}
		}catch (JSONException e) {
			
			Log.e("Log_tag","Error Parsing Data "+e.toString());
			// TODO: handle exception
		}
		
		ListAdapter adapter = new SimpleAdapter(this, mylist, R.layout.row,
				new String[] {"no_telpon"}, 
				new int[] {R.id.txtNoTelpon, R.id.nama_murid});
		setListAdapter(adapter);
		
		final ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String, String> o = (HashMap<String, String>)lv.getItemAtPosition(position);
				Toast.makeText(laporandatamurid.this, "ID '"+ o.get("id_murid")+"'Jika ada kesalahan hubungi Admin", Toast.LENGTH_LONG).show();
			}
		});
	}
}