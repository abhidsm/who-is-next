package com.abhidsm.whoisnext;

import java.util.ArrayList;
import java.util.List;

public class ContactList {
	private List<Contact> _contacts=new ArrayList<Contact>();
	public List<Contact> getContacts(){return _contacts;}
	
	public void addContact(Contact contact){ this._contacts.add(contact);}
}
