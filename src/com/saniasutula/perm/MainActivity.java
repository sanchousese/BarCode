package com.saniasutula.perm;


import android.os.Bundle;
import android.provider.BaseColumns;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;


public class MainActivity extends Activity{
	protected WebView myWebView;
	private String temp = null;
	private String id = null;
	
	DataBase database;
	SQLiteDatabase sdb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		myWebView = (WebView) findViewById(R.id.webView1);

		database = new DataBase(this, "cnotct.db", null, 1);
		sdb = database.getWritableDatabase();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				String name = intent.getStringExtra(AddNew.THIEF);
				(Toast.makeText(getApplicationContext(), 
						   name, Toast.LENGTH_SHORT)).show(); 
				addToBD(name);
				}		
		}
		
		if(requestCode == 0){
			if(resultCode == RESULT_OK)
				checkCode(intent.getStringExtra("SCAN_RESULT"));
			
			else 
				if(resultCode == RESULT_CANCELED)
					(Toast.makeText(getApplicationContext(), 
						   "Cancelled", Toast.LENGTH_SHORT)).show(); 
			}
		}
	
	//Buttons
	public void scanNow(View view) {
		Intent intent = new Intent("com.google.zxing.client.android.SCAN"); 
		intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
		startActivityForResult(intent, 0);
	}
	
	public void addToBD(String str){
		ContentValues newValues = new ContentValues();
		// Задайте значения для каждой строки.
		newValues.put(DataBase.PEOPLE_NAME_COLUMN, str);
		newValues.put(DataBase.CODE_COLUMN, temp);
		// Вставляем данные в базу
		sdb.insert("people", null, newValues);
	}
	
	void checkCode(String temp){
		this.temp = temp;
		String html;
		
		Cursor cursor = sdb.query("people", new String[] {BaseColumns._ID, DataBase.PEOPLE_NAME_COLUMN, DataBase.CODE_COLUMN }, 
			    null, null, 
			    null, null, null) ;
		while (cursor.moveToNext()) {
			String code = cursor.getString(cursor.getColumnIndex(DataBase.CODE_COLUMN));
			if(code.equals(temp)){
				id = cursor.getString(cursor.getColumnIndex(BaseColumns._ID));
				String name = cursor.getString(cursor.getColumnIndex(DataBase.PEOPLE_NAME_COLUMN));
				html = 	"<html><head></head><body style=\"background-color:#00FF00;\"><center><p>"+ name + 
						"</p><p>" + temp +"</p></center></body></html>";
				myWebView.loadDataWithBaseURL(null, html, "text/html", "en_US", null);
				return;
			}
		}
		
		html = 	"<html><head></head><body style=\"background-color:#FF0000;\"><center><p>"+ 
				temp +"</p></center></body></html>";
		myWebView.loadDataWithBaseURL(null, html, "text/html", "en_US", null);
	}	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// Операции для выбранного пункта меню
		switch (item.getItemId()) {
		case R.id.action_add_To_BD:
			Intent intent = new Intent(MainActivity.this, AddNew.class);
		    startActivityForResult(intent, 1);
			return true;
		case R.id.action_show_BD:
			intent = new Intent(MainActivity.this, ShowBD.class);
		    startActivity(intent);
			return true;
		case R.id.action_clear_BD:
			sdb.delete("people", null, null);
			return true;
		case R.id.action_delete:
			sdb.execSQL("DELETE FROM people WHERE _id=" + id + ";");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
}

