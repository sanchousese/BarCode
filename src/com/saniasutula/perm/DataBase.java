package com.saniasutula.perm;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DataBase extends SQLiteOpenHelper implements BaseColumns {
	private static final String DATABASE_TABLE = "people";

	public static final String PEOPLE_NAME_COLUMN = "people_name";
	public static final String CODE_COLUMN = "code";

	private static final String DATABASE_CREATE_SCRIPT = "create table "
			+ DATABASE_TABLE + " (" + BaseColumns._ID
			+ " integer primary key autoincrement, " + PEOPLE_NAME_COLUMN
			+ " text not null, " + CODE_COLUMN + " text not null);";

	public DataBase(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	public DataBase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE_SCRIPT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Запишем в журнал
		Log.w("SQLite", "Обновляємся з версії " + oldVersion + " на версію " + newVersion);
		
		// Удаляем старую таблицу и создаём новую
		db.execSQL("DROP TABLE IF IT EXIST " + DATABASE_TABLE);
		// Создаём новую таблицу
		onCreate(db);
	}
	
	public void onUpgrade(SQLiteDatabase db) {
		// TODO Auto-generated method stub		
		// Удаляем старую таблицу и создаём новую
		db.execSQL("DROP TABLE IF IT EXIST " + DATABASE_TABLE);
		// Создаём новую таблицу
		onCreate(db);
	}
}