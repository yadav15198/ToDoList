package com.example.hp.todolist;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;

import static com.example.hp.todolist.ToDo_Open_Helper.COL_NAME;
import static com.example.hp.todolist.ToDo_Open_Helper.COL_DESC;
import static com.example.hp.todolist.ToDo_Open_Helper.COL_DATE;
import static com.example.hp.todolist.ToDo_Open_Helper.COL_TIME;
import static com.example.hp.todolist.ToDo_Open_Helper.TABLE_NAME;


public class MyReceiver extends BroadcastReceiver {
    long timeStamp;
    String smsBody;
    String address;


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get("pdus");


            if (sms != null)
            {

                for (int i = 0; i < sms.length; ++i)
                {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                     smsBody = smsMessage.getDisplayMessageBody();
                    // Log.d("body",smsBody+"");
                    address = smsMessage.getDisplayOriginatingAddress();
                    timeStamp = smsMessage.getTimestampMillis();
                   // Log.d("time",timeStamp+"");

                }
              todo todos = new todo(smsBody,address);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timeStamp);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day =  calendar.get(Calendar.DATE);

                int hours = calendar.get(Calendar.HOUR);
                int minutes = calendar.get(Calendar.MINUTE);
                 String date = day +"/" + month +"/"+ year+"";
                 String time = hours +":"+ minutes+"";

              ToDo_Open_Helper toDo_open_helper = ToDo_Open_Helper.getInstance(context);
                SQLiteDatabase database = toDo_open_helper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(ToDo_Open_Helper.COL_DESC,smsBody);
                contentValues.put(ToDo_Open_Helper.COL_NAME,address);
                contentValues.put(ToDo_Open_Helper.COL_TIME,time);
                contentValues.put(ToDo_Open_Helper.COL_DATE,date);
                 long id = database.insert(TABLE_NAME,null,contentValues);
                Toast.makeText(context, "content added", Toast.LENGTH_SHORT).show();
            }

        }
    }

}

