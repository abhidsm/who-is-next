package com.abhidsm.whoisnext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactsListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] contacts;
	
	public ContactsListAdapter(Context context,String[] contacts) {
		super(context, R.layout.contacts_item, contacts);
		this.context = context;
		this.contacts = contacts;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.contacts_item, parent, false);
		TextView contactNameTextView = (TextView) rowView.findViewById(R.id.contact_name);
		TextView contactedSinceTextView = (TextView) rowView.findViewById(R.id.contacted_since);
		contactNameTextView.setText(contacts[position]);
		contactedSinceTextView.setText(contacts[position]);

		return rowView;
	}
}
