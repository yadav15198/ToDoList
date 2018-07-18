package com.example.hp.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {
    TextView nameTextview, descTextview, dateTextview, timetextview,idtextview;
    String name, desc,date,time;
    int pos;
    int id;
   // Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        nameTextview = findViewById(R.id.nameTextview);
        descTextview = findViewById(R.id.descTextview);
        dateTextview = findViewById(R.id.dateTextview);
        timetextview = findViewById(R.id.timeTextview);
       // idtextview =   findViewById(R.id.idtextview);
       // button = findViewById(R.id.button);
        Intent intent = getIntent();
        name = intent.getStringExtra(MainActivity.title_name);
        desc = intent.getStringExtra(MainActivity.desc_name);
        pos = intent.getIntExtra("position",0);
        date = intent.getStringExtra(MainActivity.date_string);
        time = intent.getStringExtra(MainActivity.time_String);
        id = (int)intent.getLongExtra(MainActivity.Id_string,0);

       nameTextview.setText(name);
       descTextview.setText(desc);
       dateTextview.setText(date);
       //idtextview.setText(id);
      timetextview.setText(time);
    }

    @Override
    public void onClick(View view) {
         Intent intent = new Intent(this,Main2Activity.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        if(menuId == R.id.edit_menu){
            String name = (String) nameTextview.getText();
            String desc = (String) descTextview.getText();
            String date = (String) dateTextview.getText();
            String time = (String) timetextview.getText();
            Intent intent1 = new Intent(Main3Activity.this,Edit_Activity.class);
            intent1.putExtra("name",name);
            intent1.putExtra("desc",desc);
            intent1.putExtra("date",date);
            intent1.putExtra("time",time);
            intent1.putExtra("id",id);
            intent1.putExtra("position",pos);
             startActivityForResult(intent1,7);

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 7 && resultCode == 2){
            String name = data.getStringExtra("name");
            String desc = data.getStringExtra("desc");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            int position = data.getIntExtra("position",0);
            int id = data.getIntExtra("id",0);
            nameTextview.setText(name);
            descTextview.setText(desc);
            dateTextview.setText(date);
            timetextview.setText(time);
            String tdate[] = time.split("/");
            String ttime[] = date.split(":");
            int day = Integer.parseInt(tdate[0]);
            int month = Integer.parseInt(tdate[1]);
            int year = Integer.parseInt(tdate[2]);
            int hour = Integer.parseInt(ttime[0]);
            int min = Integer.parseInt(ttime[1]);
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(year,month-1,day,hour,min);
            long alarmtime= calendar.getTimeInMillis();
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent2 = new Intent(getApplicationContext(),Alarmmanager.class);
            final PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),1,intent2,0);
            long currentTime = alarmtime;
            alarmManager.set(AlarmManager.RTC_WAKEUP,currentTime ,pendingIntent);
        }
        setResult(9,data);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}