package com.example.hp.todolist;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Alarmmanager extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("mychannelid", "expencechannel", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        long id = intent.getLongExtra("id",-1);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"mychannelid");
        builder.setContentTitle("AlarmManager");
        builder.setContentText("its done!");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        Intent intent2 = new Intent(context,MainActivity.class);
        intent2.putExtra("id",id);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context,1,intent2,0);
        builder.setContentIntent(pendingIntent1);
        Notification notification = builder.build();
        manager.notify(1,notification);


    }
};

