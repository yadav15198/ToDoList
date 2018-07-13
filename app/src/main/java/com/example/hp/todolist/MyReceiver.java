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
                    address = smsMessage.getDisplayOriginatingAddress();
                    timeStamp = smsMessage.getTimestampMillis();
                }
              todo todos = new todo(smsBody,address);
              todos.setTime(String.valueOf(timeStamp));
              ToDo_Open_Helper toDo_open_helper = ToDo_Open_Helper.getInstance(context);
                SQLiteDatabase database = toDo_open_helper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(TABLE_NAME,smsBody);
                contentValues.put(toDo_open_helper.COL_NAME,address);
                contentValues.put(toDo_open_helper.COL_DESC,timeStamp);
                 long id = database.insert(TABLE_NAME,null,contentValues);
                Toast.makeText(context, "content added", Toast.LENGTH_SHORT).show();
            }

        }
    }

}

