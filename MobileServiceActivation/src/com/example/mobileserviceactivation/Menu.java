package com.example.mobileserviceactivation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Menu extends Activity implements OnItemClickListener {

	private ListAdapter adapter;
	ListView myList;
	String gotServiceProvider;
	MyDatabaseHelper mdh;
	String text;
	RadioGroup rg;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

	//	rg=(RadioGroup) findViewById(id);
		/*// received service provider type from another activity
		Bundle dataFromAnotherActivity = getIntent().getExtras();
		gotServiceProvider = dataFromAnotherActivity.getString("key");*/
		SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        gotServiceProvider = app_preferences.getString("key", "null");
		

		/*SharedPreferences getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String values =getData.getString("list", "You havent selected a service provider");
		if(values.contentEquals("Mobitel")){
			text="Mobitel";
		}else if(values.contentEquals("Etisalat")){
			text="Etisalat";
		}else if(values.contentEquals("Airtell")){
			text="Airtell";
		}else if(values.contentEquals("Dialog")){
			text="Dialog";
		}else text="Dialog";*/
		
		mdh = new MyDatabaseHelper(this);

		//mdh.deleteAll();

		// retrieve data according to the type of service provider selected by
		// the user
		Cursor allData = mdh.retriveData(gotServiceProvider);
		String[] from = { "name", "code" };
		int[] to = { android.R.id.text1, android.R.id.text2 };

		// add services into list
		adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2, allData, from, to);
		myList = (ListView) findViewById(R.id.listView1);
		myList.setAdapter(adapter);

		// when an list item is clicked
		myList.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

		// get the item selected as a string
		Cursor c = (Cursor) myList.getItemAtPosition(position);
		String itemClicked = c.getString(c.getColumnIndex("name"));
		String codeClicked =c.getString(c.getColumnIndex("code"));
		String code= findCode(codeClicked);
		
		Toast.makeText(this, codeClicked, Toast.LENGTH_SHORT).show();

		String encodedHash = Uri.encode("#");
		startActivityForResult(new Intent("android.intent.action.CALL",  Uri.parse("tel:*100" + encodedHash)), 1);
		
		try {
			// if there is another activity with the item clicked start it
			Class ourClass = Class
					.forName("com.example.mobileserviceactivation."
							+ itemClicked);
			if(ourClass.equals(null)){
				
			}
			Intent intent = new Intent(Menu.this, ourClass);
			startActivity(intent);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// AboutUs, Settings, Exit
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {

		super.onCreateOptionsMenu(menu);
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.my_menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.mSettings:

			Intent i = new Intent(
					"com.example.mobileserviceactivation.SETTINGS");
			startActivityForResult(i, 1);
			break;
		case R.id.mAboutUs:

			Intent p = new Intent("com.example.mobileserviceactivation.ABOUTUS");
			startActivity(p);
			break;
		case R.id.mExit:

			// exit from the application
			finish();
			break;
		}

		return false;
	}
	
	private String findCode(String code){
	
		int length=code.length();
		for(int i=0;i<length;i++){
			
			if(code.charAt(i)=='#'){
				
			}
		}
		return null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (resultCode) {
        case 1:
        	Toast.makeText(this, "mobitel", Toast.LENGTH_SHORT).show();
        	//String selected= 
            //showUserSettings();
            break;
        case 2: 
        	Toast.makeText(this, "Dialog", Toast.LENGTH_SHORT).show();
        	break;
        }
	}
	
	/*
	private void showUserSettings() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
 
        //SharedPreferences.Editor editor = sharedPrefs.edit();
        text = sharedPrefs.getString("service_provider", null);
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        //editor.putString("key", text);
        //editor.commit();
       // Intent myIntent = new Intent(ServiceProvider.this,Menu.class);
        //startActivity(myIntent);
       /* StringBuilder builder = new StringBuilder();
 
        builder.append("\n Username: "
                + sharedPrefs.getString("prefUsername", "NULL"));
 
        builder.append("\n Send report:"
                + sharedPrefs.getBoolean("prefSendReport", false));
 
        builder.append("\n Sync Frequency: "
                + sharedPrefs.getString("prefSyncFrequency", "NULL"));
 
        TextView settingsTextView = (TextView) findViewById(R.id.textUserSettings);
 
        settingsTextView.setText(builder.toString());
    }
	*/
}
