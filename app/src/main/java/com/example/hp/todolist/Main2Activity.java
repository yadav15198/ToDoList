package com.example.hp.todolist;

import android.app.DatePickerDialog;
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
    EditText timeEditText;
    EditText dateEditText;
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
              finish();
            }
        });
    }

}
