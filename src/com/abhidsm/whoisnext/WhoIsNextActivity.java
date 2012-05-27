package com.abhidsm.whoisnext;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class WhoIsNextActivity extends ListActivity {
    /** Called when the activity is first created. */
	static final String[] MOBILE_OS = 
            new String[] { "Android", "iOS", "WindowsMobile", "Blackberry"};	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setListAdapter(new ContactsListAdapter(this, MOBILE_OS));
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
		Intent intent = new Intent(this.getApplicationContext(), PhoneContactsActivity.class);
		startActivityForResult(intent, 0);
	 }
	 
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
	        super.onActivityResult(requestCode, resultCode, intent);
	        Bundle extras = intent.getExtras();
	        String id = extras != null ? extras.getString("id"):"nothing returned";
	        String name = extras != null ? extras.getString("name"):"nothing returned";
	        Toast.makeText(this.getApplicationContext(), "ID: "+ id+" Name: "+ name, Toast.LENGTH_SHORT).show();
	    }

}