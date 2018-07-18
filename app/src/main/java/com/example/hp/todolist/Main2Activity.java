package com.example.hp.todolist;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.StaticLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.app.TimePickerDialog.*;

public class Main2Activity extends AppCompatActivity {
  EditText editText1,editText2;
  Button button;
    String edit1,time1,date1;
    String edit2;
    TextView timeEditText;
    TextView dateEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        dateEditText =findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        button = findViewById(R.id.button);

               dateEditText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Calendar calendar = Calendar.getInstance();
               int year = calendar.get(Calendar.YEAR);
               int month = calendar.get(Calendar.MONTH);
               int day = calendar.get(Calendar.DAY_OF_MONTH);

               DatePickerDialog datePickerDialog = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
                      @Override
                       public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                       ++month;
                       date1 = dayOfMonth + "/" + month + "/" + year;
                       dateEditText.setText(date1);
                        }
                       },year,month,day);

                       datePickerDialog.show();
                       }
                       });
               timeEditText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Calendar calendar = Calendar.getInstance();
               int hour = calendar.get(Calendar.HOUR_OF_DAY);
               int min = calendar.get(Calendar.MINUTE);

               TimePickerDialog timePickerDialog = new TimePickerDialog(Main2Activity.this, new TimePickerDialog.OnTimeSetListener() {
                   @Override
                           public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                           time1 = hourOfDay + ":" + minute;
                         timeEditText.setText(time1);

                        }
                        },hour,min,false);

                        timePickerDialog.show();

                       }
                      });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            edit1 = editText1.getText().toString();
            edit2 = editText2.getText().toString();
            time1 = timeEditText.getText().toString();
            date1 = dateEditText.getText().toString();
                startMainActivity(view);
            }
            public void startMainActivity(View view){
              Intent intent = new Intent();
              intent.putExtra(MainActivity.title_name,edit1);
              intent.putExtra(MainActivity.desc_name,edit2);
              intent.putExtra(MainActivity.date_string,date1);
              intent.putExtra(MainActivity.time_String,time1);
                setResult(2,intent);
                String date[] = date1.split("/");
                String time[] = time1.split(":");
                int day = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int year = Integer.parseInt(date[2]);
                int hour = Integer.parseInt(time[0]);
                int min = Integer.parseInt(time[1]);
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(year,month-1,day,hour,min);
               long alarmtime= calendar.getTimeInMillis();
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
               Intent intent2 = new Intent(getApplicationContext(),Alarmmanager.class);
               final PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),1,intent2,0);
               long currentTime = alarmtime;
               alarmManager.set(AlarmManager.RTC_WAKEUP,currentTime ,pendingIntent);

              finish();
            }
        });

    }

}
