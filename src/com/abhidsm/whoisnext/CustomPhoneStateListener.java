package com.abhidsm.whoisnext;

import java.util.Date;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class CustomPhoneStateListener extends PhoneStateListener{
	static int INITIAL_STATE = 999;
    Context context; //Context to make Toast if required
    Intent intent;
    int state;
    public CustomPhoneStateListener(Context context, Intent intent) {
        super();
        this.context = context;
        this.intent = intent;
        this.state = INITIAL_STATE;
    }
    
    @Override
    public void onCallStateChanged(int state, String number) {
        super.onCallStateChanged(state, number);

        switch (state) {
        case TelephonyManager.CALL_STATE_IDLE:
            //when Idle i.e no call
            //Toast.makeText(context, "Phone state Idle", Toast.LENGTH_LONG).show();
        	if(this.state == INITIAL_STATE){
        		if(number.length() == 0){
        			number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        		}
        		if(number != null && number.length() > 0){
        			updateContactDetails(number);
        			//Toast.makeText(context, "num =" + number,Toast.LENGTH_LONG).show();
        		}
        	}
            break;
        case TelephonyManager.CALL_STATE_OFFHOOK:
            //when Off hook i.e in call
            //Make intent and start your service here
            //Toast.makeText(context, "Phone state Off hook", Toast.LENGTH_LONG).show();
            break;
        case TelephonyManager.CALL_STATE_RINGING:
            //when Ringing
            //Toast.makeText(context, "Phone state Ringing", Toast.LENGTH_LONG).show();
            break;
        default:
            break;
        }
        Log.e("PhoneStateListener", "Phone State: "+this.state);
        this.state = state;
    }
    
    private void updateContactDetails(String number){
    	Date currentDate = new Date();
    	String lastTimeContacted = String.valueOf(currentDate.getTime());
    	ContactList contacts = new ContactList(context);
		String contactID = getContactIDByNumber(number);
		if(contactID.length() > 0){
			Log.e("ContactList", "Number: "+number+" callerID: "+contactID);
	    	contacts.addContactsFromFile();
	    	contacts.updateLastTimeContactedValueOfContact(contactID, lastTimeContacted);
		}
    }
    
	private String getContactIDByNumber(String number) {
	    Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
	    String contactID = "";

	    ContentResolver contentResolver = this.context.getContentResolver();
	    Cursor contactLookup = contentResolver.query(uri, new String[] {BaseColumns._ID,
	            ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);

	    try {
	        if (contactLookup != null && contactLookup.getCount() > 0) {
	            contactLookup.moveToNext();
	            contactID = contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
	        }
	    } finally {
	        if (contactLookup != null) {
	            contactLookup.close();
	        }
	    }

	    return contactID;
	}

}
