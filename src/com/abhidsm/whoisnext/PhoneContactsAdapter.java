package com.abhidsm.whoisnext;

import java.util.List;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PhoneContactsAdapter  extends ArrayAdapter<Contact>{
	private final List<Contact> _contacts;
	private final Activity _context;
	
	public PhoneContactsAdapter(Activity context, List<Contact> contacts)
	{
		super(context,R.layout.contacts_item,contacts);
		this._contacts=contacts;
		this._context=context;
	}
	
	static class ViewHolder {
		protected TextView contactName;
		protected TextView contactedSince;
		private Contact  _contact;
		protected void setContact(Contact contact)
		{
			contactName.setText(contact.getDisplayName());
			contactedSince.setText(DateFormat.format("dd, MMM", contact.getContactedTimeInLong()));
			_contact=contact;
		}
		protected Contact getContact() {return _contact;}
	}
	
	@Override
	public Contact getItem(int position)
	{
		return _contacts.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view=null;
		LayoutInflater inflater=_context.getLayoutInflater();
		view=inflater.inflate(R.layout.contacts_item, null);
		final ViewHolder viewHolder=new ViewHolder();
		viewHolder.contactName=(TextView)view.findViewById(R.id.contact_name);
		viewHolder.contactedSince=(TextView)view.findViewById(R.id.contacted_since);
		viewHolder.setContact(_contacts.get(position));
		view.setTag(viewHolder);
		return view;
	}

}
