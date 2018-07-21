package com.example.hp.todolist;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.FontsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.AlarmManagerCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


import static com.example.hp.todolist.ToDo_Open_Helper.COL_ID;
import static com.example.hp.todolist.ToDo_Open_Helper.COL_NAME;
import static com.example.hp.todolist.ToDo_Open_Helper.COL_DESC;
import static com.example.hp.todolist.ToDo_Open_Helper.COL_DATE;
import static com.example.hp.todolist.ToDo_Open_Helper.COL_TIME;
import static com.example.hp.todolist.ToDo_Open_Helper.TABLE_NAME;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{

    ListView list1;
    ArrayList<todo> todoArrayList = new ArrayList<>();
    todoadapter todoadap;
    public static final String title_name ="NAME";
    public static final String desc_name = "desccripion";
    public static final String date_string = "date";
    public static final String time_String = "time";
    public static final String Id_string = "id";
    ToDo_Open_Helper toDo_open_helper;
    SQLiteDatabase database;
    MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDo_open_helper = ToDo_Open_Helper.getInstance(this);
        database = toDo_open_helper.getReadableDatabase();
        //  String [] columns = {toDo_open_helper.COL_NAME, toDo_open_helper.COL_DESC};
        Cursor cursor = database.query(ToDo_Open_Helper.TABLE_NAME,null ,null,null,null,null,null);
        if(cursor.moveToNext()) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                String description = cursor.getString(cursor.getColumnIndex(COL_DESC));
                String date = cursor.getString(cursor.getColumnIndex(COL_DATE));
                String time = cursor.getString(cursor.getColumnIndex(COL_TIME));
                long id = cursor.getLong(cursor.getColumnIndex(COL_ID));
                todo todos = new todo(name, description);
                todos.setTime(time);
                todos.setDate(date);
                todos.setID(id);
                todoArrayList.add(todos);
            }
        }
        cursor.close();


//     IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
//       registerReceiver(myReceiver,intentFilter);


        list1 = findViewById(R.id.list1);

        todoadap = new todoadapter(this, todoArrayList);
        list1.setAdapter(todoadap);
        list1.setOnItemClickListener(this);
        list1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final todo tod = todoArrayList.get(i);
                final int pos = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you want to continue");
                builder.setTitle("Delete");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        todoArrayList.remove(pos);
                       long id = tod.getID();
                       // String whereArgs[] = {ToDo_Open_Helper.COL_ID = id};
                        database.delete(TABLE_NAME,ToDo_Open_Helper.COL_ID + " = "+id,null);
                        todoadap.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Canceal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(MainActivity.this,Main3Activity.class);
        todo todo = todoArrayList.get(i);
        intent.putExtra(title_name,todo.name);
        intent.putExtra(desc_name,todo.description);
        intent.putExtra(date_string,todo.date);
        intent.putExtra(time_String,todo.time);
        intent.putExtra("position",i);
        intent.putExtra(Id_string, todo.getID());
        startActivityForResult(intent,5);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.list_item1) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivityForResult(intent, 1);
        }
        else if(id == R.id.list_item2){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            Uri uri = Uri.parse("mailto:sumansaurabh421@gmail.com");
            intent.setData(uri);
            startActivity(intent);
        }
        else if(id == R.id.list_item3){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("https://codingninjas.in");
            intent.setData(uri);

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == 2) {
                String name = data.getStringExtra(MainActivity.title_name);
                String desc = data.getStringExtra(MainActivity.desc_name);
                String time = data.getStringExtra(MainActivity.date_string);
                String date = data.getStringExtra(MainActivity.time_String);
                //long  id = data.getStringExtra(MainActivity.Id_string);
                todo tod = new todo(name, desc);
                tod.setDate(date);
                tod.setTime(time);
                //  todoArrayList.add(tod);
//                todoadap.notifyDataSetChanged();
                ToDo_Open_Helper toDo_open_helper = ToDo_Open_Helper.getInstance(this);
                SQLiteDatabase database = toDo_open_helper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(ToDo_Open_Helper.COL_NAME, tod.getName());
                cv.put(ToDo_Open_Helper.COL_DESC, tod.getDescription());
                cv.put(ToDo_Open_Helper.COL_DATE, tod.getDate());
                cv.put(ToDo_Open_Helper.COL_TIME, tod.getTime());

                long id = database.insert(ToDo_Open_Helper.TABLE_NAME, null, cv);
                if (id > -1) {

                    tod.setID(id);
                    cv.put(ToDo_Open_Helper.COL_ID, tod.getID());
                    todoArrayList.add(tod);
                    todoadap.notifyDataSetChanged();
                }
            }
        }
        if(requestCode == 5 && resultCode == 9){
            String name = data.getStringExtra("name");
            String desc = data.getStringExtra("desc");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            int id = data.getIntExtra("id",0);
            int position = data.getIntExtra("position",0);
            todo newtodo = new todo(name,desc);
            newtodo.setDate(date);
            newtodo.setID(id);
            newtodo.setTime(time);
            todoArrayList.set(position,newtodo);
            todoadap.notifyDataSetChanged();
        }
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.e("MyActivity","onstart");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.e("MyActivity","onpause");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.e("MyActivity","onresume");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d("MyActivity","onstop");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.e("MyActivity","onsDestroy");
//    }
}