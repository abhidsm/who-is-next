package com.abhidsm.whoisnext;

public class Contact {
	private String _id;
	private String _displayName;
	private String _lastTimeContacted;
	
	public String getId(){return _id;}
	public String getDisplayName(){return _displayName;}
	public String getLastTimeContacted(){return _lastTimeContacted;}
	public Long getContactedTimeInLong(){return Long.valueOf(_lastTimeContacted);}
	public void setId(String id){_id=id;}
	public void setDisplayName(String displayName){_displayName=displayName;}
	public void setLastTimeContacted(String lastTimeContacted){_lastTimeContacted=lastTimeContacted;}
}
