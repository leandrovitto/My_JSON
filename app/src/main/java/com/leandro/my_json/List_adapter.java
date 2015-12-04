package com.leandro.my_json;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class List_adapter extends ArrayAdapter<room> {

	private Context context;
	private List<room> listdata;

	public List_adapter(Context con, int resource, List<room> objects) {
		super(con, resource, objects);

		context = con;
		listdata = objects; // Initializing list data

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.list_items, parent, false);
		room temp = listdata.get(position);
		TextView id = (TextView) view.findViewById(R.id.id);
		TextView name = (TextView) view.findViewById(R.id.name);
		TextView note = (TextView) view.findViewById(R.id.note);
		//TextView phone = (TextView) view.findViewById(R.id.Phone);
		id.setText("ID :" + temp.getId());
		name.setText("Name : "+temp.getName());
		note.setText("Note : "+temp.getNote());
		//phone.setText("Phone : "+temp.getPhone());
		return view;
	}
}
