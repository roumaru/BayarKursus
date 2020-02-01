package com.example.bayarkursus;

import java.util.ArrayList;

//import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private static ArrayList<menu> searchArrayList;
	
	public MenuAdapter(Context context, ArrayList<menu> res) {
		searchArrayList = res;
		mInflater = LayoutInflater.from(context);

	}

	public int getCount() {
		return searchArrayList.size();
	}

	public Object getItem(int position) {
		return searchArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.rowmenu, null);
			holder = new ViewHolder();
			holder.txtMenuName = (TextView) convertView.findViewById(R.id.rowView);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtMenuName.setText(searchArrayList.get(position).getMenuName());
		

		return convertView;
	}
	
	static class ViewHolder {
		TextView txtMenuName;
	}
	

}
