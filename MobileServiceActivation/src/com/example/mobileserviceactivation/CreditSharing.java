package com.example.mobileserviceactivation;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreditSharing extends Activity implements View.OnClickListener {

	Button enterAmount, enterNo;
	EditText inputNo, inputAmount;
	TextView codeText;
	final static int pickNo = 0;
	Intent creditSharingIntent, setAmount;
	String amount, code, number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.creditsharing);

		initialize();

		// when enterNo button is clicked do what is inside onClick () method
		enterNo.setOnClickListener(this);
		// when enterAmount button is clicked do what is inside onClick ()
		// method
		enterAmount.setOnClickListener(this);
	}

	private void initialize() {
		// build the connections with creditsharing.xml class
		enterAmount = (Button) findViewById(R.id.bEnterAmount);
		enterNo = (Button) findViewById(R.id.bEnterPhone);
		inputNo = (EditText) findViewById(R.id.etPhone);
		inputAmount = (EditText) findViewById(R.id.etAmount);
		codeText = (TextView) findViewById(R.id.tvCode);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.bEnterPhone: // when the browse button is clicked

			creditSharingIntent = new Intent(Intent.ACTION_GET_CONTENT);
			creditSharingIntent
					.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
			startActivityForResult(creditSharingIntent, pickNo);
			break;

		case R.id.bEnterAmount:// when the enter is clicked

			// extract the entered amount in the edit text field
			amount = inputAmount.getText().toString();
			codeText.setText(amount + "  " + number);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (data != null) {
			Uri uri = data.getData();

			if (uri != null) {
				Cursor c = null;
				try {
					c = getContentResolver()
							.query(uri,
									new String[] {
											ContactsContract.CommonDataKinds.Phone.NUMBER,
											ContactsContract.Contacts.DISPLAY_NAME },
									null, null, null);

					if (c != null && c.moveToFirst()) {
						number = c.getString(0);
						String name = c.getString(1);
						showSelectedNumber(name, number);
					}
				} finally {
					if (c != null) {
						c.close();
					}
				}
			}
		}
	}

	// method to view the browsed name and number in a widget
	public void showSelectedNumber(String name, String number) {

		inputNo.setText(number);
		Toast.makeText(this, name + ": " + number, Toast.LENGTH_LONG).show();
	}
}
