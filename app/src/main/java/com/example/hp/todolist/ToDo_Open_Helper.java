package com.example.hp.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class ToDo_Open_Helper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="Todo_db";
    public static final String TABLE_NAME = "table_todo";
    public static final String  COL_ID = "ID";
    public static final String  COL_NAME = "NAME";
    public static final String  COL_DESC ="DESCRIPTION";
    public  static  final String COL_DATE ="DATE";
    public static final String  COL_TIME = "TIME";

    public static final int version = 1 ;
   // String TABLE_TODO,ID,NAME,DESCRIPTION,DATE,TIME;
    private static ToDo_Open_Helper INSTANCE;

    public static ToDo_Open_Helper getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new ToDo_Open_Helper(context.getApplicationContext());
        }
        return INSTANCE;
    }

    public ToDo_Open_Helper(Context context) {
        super(context,DATABASE_NAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String query = "CREATE TABLE  " + TABLE_NAME  + " (" +COL_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT  ,  " +  COL_NAME  + "  TEXT, " +COL_DESC + " TEXT, " + COL_DATE +  " TEXT, " + COL_TIME+ " TEXT " + " )";
    db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
