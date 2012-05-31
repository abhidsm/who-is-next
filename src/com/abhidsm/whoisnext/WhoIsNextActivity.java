package com.abhidsm.whoisnext;

import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WhoIsNextActivity extends ListActivity {
    /** Called when the activity is first created. */
	private ContactList contacts;
	private List<Contact> listItems;
	private ArrayAdapter<Contact> adapter;
	private Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context= this.getApplicationContext();
        this.contacts = new ContactList(context);
        this.contacts.addContactsFromFile();
        this.listItems = contacts.getContacts();
        this.adapter=new PhoneContactsAdapter(this,this.listItems);
		setListAdapter(this.adapter);
		this.getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(WhoIsNextActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete "+contacts.getContacts().get(position).getDisplayName()+" ?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						contacts.removeContact(positionToRemove);
		            	listItems = contacts.getContacts();
		            	adapter.notifyDataSetChanged();
					}});
                adb.show();
				return false;
			}
		});
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Object o=this.getListAdapter().getItem(position);
        Contact c=(Contact)o;
        String url = "content://contacts/people/"+c.getId();
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivityForResult(intent, 0);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_contact:
            	showAddContactView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
	 private void showAddContactView(){
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		startActivityForResult(intent, 0);
	 }
	 
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data){
	        super.onActivityResult(requestCode, resultCode, data);
	        
	        if (resultCode == Activity.RESULT_OK) {
	            Uri contactData = data.getData();
	            Cursor c =  managedQuery(contactData, null, null, null, null);
	            if (c.moveToFirst()) {
	            	String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));	
	            	String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	            	String lastTimeContacted = c.getString(c.getColumnIndex(ContactsContract.Contacts.LAST_TIME_CONTACTED));
	            	Contact contact = new Contact();
	            	contact.setId(id);
	            	contact.setDisplayName(name);
	            	contact.setLastTimeContacted(lastTimeContacted);
	            	this.contacts.addContact(contact);
	            	this.listItems = this.contacts.getContacts();
	            	this.adapter.notifyDataSetChanged();
	            }
	        }
	    }
	 
	 

}