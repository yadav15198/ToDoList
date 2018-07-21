package com.example.hp.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

public class Edit_Activity extends AppCompatActivity  {
    EditText editName_tv, editDesc_tv;
    TextView editDate_tv, editTime_tv;
    Button button;
    int year, month, day, hour, min;
    String time1, date1,tname,tdesc,tdate,ttime;
    SharedPreferences sharedPreferences;
    String name,desc,date,time;
    int pos;
    int id;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_);
        editName_tv = findViewById(R.id.editName_tv);
        editDesc_tv = findViewById(R.id.editDesc_tv);
        editDate_tv = findViewById(R.id.editDate_tv);
        editTime_tv = findViewById(R.id.editTime_tv);
        button = findViewById(R.id.button);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        desc = intent.getStringExtra("desc");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        id = intent.getIntExtra("id",0);
        pos = intent.getIntExtra("position",0);
        editName_tv.setText(name);
        editDesc_tv.setText(desc);
        editDate_tv.setText(date);
        editTime_tv.setText(time);

        editTime_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Edit_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        ++month;
                        date1 = dayOfMonth + "/" + month + "/" + year;
                        editTime_tv.setText(date1);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });
        editDate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(Edit_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        time1 = hourOfDay + ":" + minute;
                        editDate_tv.setText(time1);

                    }
                }, hour, min, false);

                timePickerDialog.show();

            }
        });
//       sharedPreferences = getSharedPreferences("my_shared_pref", MODE_PRIVATE);
//       String new_name = sharedPreferences.getString("name",name);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(!validateFields()){
                  Toast.makeText(Edit_Activity.this, "fields cant be empty", Toast.LENGTH_SHORT).show();
              }
                Intent intent = new Intent();

                ToDo_Open_Helper openHelper = ToDo_Open_Helper.getInstance(getApplicationContext());
                SQLiteDatabase database = openHelper.getWritableDatabase();

                name = editName_tv.getText().toString();
                desc = editDesc_tv.getText().toString();
                date = editDate_tv.getText().toString();
                time = editTime_tv.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put(ToDo_Open_Helper.COL_NAME,name);
                contentValues.put(ToDo_Open_Helper.COL_DESC,desc);
                contentValues.put(ToDo_Open_Helper.COL_DATE,date);
                contentValues.put(ToDo_Open_Helper.COL_TIME,time);

                intent.putExtra("name",name);
                intent.putExtra("desc",desc);
                intent.putExtra("date",date);
                intent.putExtra("time",time);
                intent.putExtra("id",id);
                 intent.putExtra("position",pos);
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day,hour,min);

               // contentValues.put(ToDo_Open_Helper.COL_DATE,calendar.getTimeInMillis());
                setResult(2,intent);
                database.update(ToDo_Open_Helper.TABLE_NAME,contentValues,ToDo_Open_Helper.COL_ID + " = "+id,null);

                finish();
            }
        });

    }
    private boolean validateFields() {
        tname = editName_tv.getText().toString().trim();
        tdesc = editDesc_tv.getText().toString().trim();
        tdate = editDate_tv.getText().toString().trim();
        ttime = editTime_tv.getText().toString().trim();
        if(tname.isEmpty()){

            editName_tv.setError("Enter title");
            return false;
        }

        if(tdesc.isEmpty()){

            editDesc_tv.setError("Enter amount");
            return false;
        }

        if(tdate.isEmpty()){

            editDate_tv.setError("Select date");
            return false;
        }

        if(ttime.isEmpty()){

            editTime_tv.setError("Select time");
            return false;
        }

        return true;
    }
}