package com.example.mobileserviceactivation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddCode extends Activity implements OnClickListener {

	EditText serviceName, code;
	MyDatabaseHelper database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.addcode);
		initialize();
	}

	private void initialize() {

		database = new MyDatabaseHelper(this);

		Button save = (Button) findViewById(R.id.bSaveService);
		serviceName = (EditText) findViewById(R.id.etAddService);
		code = (EditText) findViewById(R.id.etAddCode);

		save.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.bSaveService:

			// get entered data in service name and code fields as strings
			String addedService = serviceName.getText().toString();
			String addedCode = code.getText().toString();
			// add data to the database
			database.addData("Mobitel", addedService, addedCode); // *******************************
			// go back to menu page after adding code
			Intent a = new Intent(AddCode.this, Menu.class);
			startActivity(a);
			break;
		}
	}
}
