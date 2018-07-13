package com.example.hp.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {
    TextView nameTextview, descTextview, dateTextview, timetextview,idtextview;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        nameTextview = findViewById(R.id.nameTextview);
        descTextview = findViewById(R.id.descTextview);
        dateTextview = findViewById(R.id.dateTextview);
        timetextview = findViewById(R.id.timeTextview);
        idtextview =   findViewById(R.id.idtextview);
        button = findViewById(R.id.button);
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.title_name);
        String desc = intent.getStringExtra(MainActivity.desc_name);
        String date = intent.getStringExtra(MainActivity.date_string);
        String time = intent.getStringExtra(MainActivity.time_String);
       nameTextview.setText(name);
       descTextview.setText(desc);
       dateTextview.setText(date);
      timetextview.setText(time);
    }

    @Override
    public void onClick(View view) {
         Intent intent = new Intent(this,Main2Activity.class);

    }
}