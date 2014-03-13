package com.saniasutula.perm;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ShowBD extends Activity {

	DataBase database;
	SQLiteDatabase sdb;
	
	private ListView lv1;
	private String ID;
	private String code;
	private String temp;
	
	ArrayList<String> names = new ArrayList<String>();

	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 2) {
			if (resultCode == RESULT_OK) {
				String name = intent.getStringExtra(Edit.THIEF);
				
				sdb.execSQL("DELETE FROM people WHERE _id=" + temp + ";");
				ContentValues newValues = new ContentValues();
				// Задайте значения для каждой строки.
				newValues.put(DataBase.PEOPLE_NAME_COLUMN, name);
				newValues.put(DataBase.CODE_COLUMN, code);
				// Вставляем данные в базу
				sdb.insert("people", null, newValues);
				refresh();
				}		
		}
	}

	private void refresh(){
		names.clear();
		Cursor cursor = sdb.query("people", new String[] {BaseColumns._ID, DataBase.PEOPLE_NAME_COLUMN, DataBase.CODE_COLUMN }, 
			    null, null, 
			    null, null, null) ;
		while (cursor.moveToNext()) 
			names.add((String)cursor.getString(cursor.getColumnIndex(DataBase.PEOPLE_NAME_COLUMN)));
		lv1.setAdapter(
        		new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , names));
		lv1.setTextFilterEnabled(true);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_bd);
		
		database = new DataBase(this, "cnotct.db", null, 1);
		sdb = database.getWritableDatabase();
		
		Cursor cursor = sdb.query("people", new String[] {BaseColumns._ID, DataBase.PEOPLE_NAME_COLUMN, DataBase.CODE_COLUMN }, 
			    null, null, 
			    null, null, null) ;
		while (cursor.moveToNext()) 
			names.add((String)cursor.getString(cursor.getColumnIndex(DataBase.PEOPLE_NAME_COLUMN)) + "\t" +
					(String)cursor.getString(cursor.getColumnIndex(DataBase.CODE_COLUMN)));
		lv1 = (ListView)findViewById(R.id.listView);
		lv1.setAdapter(
        		new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , names));
		lv1.setTextFilterEnabled(true);
		
        lv1.setOnItemClickListener(new OnItemClickListener() 
        {
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) 
        	{
                ID = names.get(position);
                
        		Cursor cursor = sdb.query("people", new String[] {BaseColumns._ID, DataBase.PEOPLE_NAME_COLUMN, DataBase.CODE_COLUMN }, 
        			    null, null, 
        			    null, null, null) ;
        		while (cursor.moveToNext()) {
        			String k = cursor.getString(cursor.getColumnIndex(DataBase.PEOPLE_NAME_COLUMN));
        			if(k.equals(ID)){
        				temp = cursor.getString(cursor.getColumnIndex(DataBase._ID));
        				code = cursor.getString(cursor.getColumnIndex(DataBase.CODE_COLUMN));
        				break;
        			}
        		}
                Intent intent = new Intent(ShowBD.this, Edit.class);
    		    startActivityForResult(intent, 2);
             }
		});
	}
	
	
}
