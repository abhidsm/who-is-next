package com.abhidsm.whoisnext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.widget.Toast;

public class ContactList {
	private List<Contact> _contacts=new ArrayList<Contact>();
	private Context context;
	
	public List<Contact> getContacts()
	{
		this.sortContacts();
		return _contacts;
	}

	public ContactList(Context context) {
		super();
		this.context = context;
	}

	public void addContact(Contact contact){ 
		this._contacts.add(contact);
		this.createFile();
	}
	
	public void removeContact(int position){
		this._contacts.remove(position);
		this.createFile();
	}

	private void createFile(){
		try {
			FileOutputStream fos = this.context.openFileOutput(WhoIsNextApplication.fileName, Context.MODE_PRIVATE);
			for(Iterator<Contact> i = this._contacts.iterator(); i.hasNext();){
				Contact contact = (Contact) i.next();
				String data = contact.getId()+","+contact.getDisplayName()+","+contact.getLastTimeContacted()+";"; 
				fos.write(data.getBytes());
			}
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String[] getContactsFromFile(){
		String data = "";
		try {
			FileInputStream fis = this.context.openFileInput(WhoIsNextApplication.fileName);
			
			byte[] input = new byte[fis.available()];
			while(fis.read(input) != -1){
				data += new String(input);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] contacts;
		if(data != ""){
			contacts = data.split(";");
		}else{
			contacts = new String[0];
		}
		
		return contacts;
	}
	
	public void addContactsFromFile(){
		String[] contacts = this.getContactsFromFile();
		//Log.v("addContentsFromFile", "Contacts Length: "+ contacts.length);
		for(int i=0; i<contacts.length; i++){
			Contact contact = getContactFromString(contacts[i]);
			if(contact.getDisplayName() != null)
				this._contacts.add(contact);	
		}
	}
	
	private Contact getContactFromString(String contactString){
		String[] contactData = contactString.split(",");
		Contact contact = new Contact();
        contact.setId(contactData[0]);
        contact.setDisplayName(contactData[1]);
        contact.setLastTimeContacted(contactData[2]);
		return contact;
	}
	
	private void sortContacts(){
		int length = this._contacts.size();
		for(int i=1; i<=length; i++){
			for(int j=0; j<length-i; j++){
				if(this._contacts.get(j).getContactedTimeInLong() > this._contacts.get(j+1).getContactedTimeInLong()){
					Contact tempContact = this._contacts.get(j);
					this._contacts.set(j, this._contacts.get(j+1));
					this._contacts.set(j+1, tempContact);
				}
			}
		}
	}
	
	public void updateLastTimeContactedValueOfContact(String contactID, String lastTimeContacted){
		for(Iterator<Contact> i = this._contacts.iterator(); i.hasNext();){
			Contact contact = (Contact) i.next();
			if(contact.getId().equals(contactID)){
				contact.setLastTimeContacted(lastTimeContacted);
				this.createFile();
				break;
			}
		}
	}
	
}
