package com.example.mobileserviceactivation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

//start page
public class StartPage extends Activity {

	MediaPlayer song;
	MyDatabaseHelper mdh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.startpage); // load startpage.xml

		mdh = new MyDatabaseHelper(this);
		song = MediaPlayer.create(StartPage.this, R.raw.honeyy);

		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		boolean music = getPrefs.getBoolean("checkbox", true);
		// play music if check box is selected
		if (music == true)
			song.start();// start the song

		Thread timer = new Thread() {
			public void run() {

				try {
					sleep(1000); // keep the starting page for 1 seconds
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					SharedPreferences settings = getSharedPreferences("prefs", 0);
				    boolean firstRun = settings.getBoolean("firstRun", true);
				    if ( firstRun ){
				    	  mdh.addData("Mobitel", "Credit Balance","*100#");
						  mdh.addData("Mobitel","My Best Friend", "*6767#");
						  mdh.addData("Mobitel","CreditSharing", "");
						  mdh.addData("Mobitel","AddCode", "+"); mdh.addData("Etisalat","Credit Balance--","*100#");
						  mdh.addData("AirTell","My Best Friend--", "*6767#");
						  mdh.addData("Dialog","CreditSharing--", "");
						  mdh.addData("Dialog","AddCode--", "+");
					// after 1 seconds open the menu page
					//Intent openMainPage = new Intent("com.example.mobileserviceactivation.SERVICEPROVIDER");
				    	Intent i=new Intent(StartPage.this,ServiceProvider.class);
				    	startActivity(i); // load the main page
				    	SharedPreferences.Editor editor = settings.edit();
				        editor.putBoolean("firstRun", false);
				        editor.commit();
				    }else{
				    	Intent i= new Intent(StartPage.this,Menu.class);
				    	startActivity(i);
				    }
				    
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {

		// startAcrivity method will call this method to destroy this startPage
		// class because it is no longer needed.
		super.onPause();
		song.release();// stop the music
		finish();
	}
}
