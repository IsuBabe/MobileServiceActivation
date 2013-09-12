package com.example.mobileserviceactivation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class ServiceProvider extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	TextView question, test;
	Button enterData;
	RadioGroup selectionList;
	private String setData;

	public void setData(String data) {
		setData = data;
	}

	public String getData() {
		return setData;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.change_service_provider);
		initialize();
	}

	private void initialize() {

		question = (TextView) findViewById(R.id.tvQuestion);
		test = (TextView) findViewById(R.id.tvText);
		enterData = (Button) findViewById(R.id.bReturn);
		selectionList = (RadioGroup) findViewById(R.id.rgAnswers);

		enterData.setOnClickListener(this);
		selectionList.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {

		/*// passing string setData to menu activity and load menu page
		Bundle basket = new Bundle();
		basket.putString("key", setData);
		Intent a = new Intent(ServiceProvider.this, Menu.class);
		a.putExtras(basket);
		startActivity(a);*/
		
		SharedPreferences app_preferences =
                PreferenceManager.getDefaultSharedPreferences(ServiceProvider.this);
        SharedPreferences.Editor editor = app_preferences.edit();
        //String text = textBox.getText().toString();
        editor.putString("key", setData);
        editor.commit();
        Intent myIntent = new Intent(ServiceProvider.this,Menu.class);
        startActivity(myIntent);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.rbMobitel:

			setData("Mobitel");
			break;
		case R.id.rbDialog:

			setData("Dialog");
			break;
		case R.id.rbEtisalat:

			setData("Etisalat");
			break;
		case R.id.rbAirtell:

			setData("AirTell");
			break;
		}
		test.setText("You have selected your service provider as " + setData);
	}
}
