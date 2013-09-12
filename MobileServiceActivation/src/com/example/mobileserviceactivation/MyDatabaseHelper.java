package com.example.mobileserviceactivation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "ServicesDatabase";

	public MyDatabaseHelper(Context context) {

		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE Service (_id INTEGER PRIMARY KEY AUTOINCREMENT, service_provider TEXT NOT NULL, name TEXT NOT NULL, code TEXT NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS Service");
		onCreate(db);
	}

	public void addData(String service_provider, String service, String code) {

		ContentValues values = new ContentValues();

		values.put("service_provider", service_provider);
		values.put("name", service);
		values.put("code", code);

		getWritableDatabase().insert("Service", "name", values);
	}

	public Cursor retriveData(String data) {

		Cursor cursor = getReadableDatabase().rawQuery(
				"select * from Service where service_provider='" + data + "'",
				null);
		return cursor;
	}

	public void deleteAll() {

		getWritableDatabase().delete("Service", null, null);
	}
}
