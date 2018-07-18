package com.example.hp.todolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class todoadapter extends ArrayAdapter {
    private ArrayList<todo> item;
  private  LayoutInflater inflater;
     todoadapter(@NonNull Context context, ArrayList<todo> items ) {
        super(context, 0, items);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.item = items;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View output;
        output = convertView;
        if (output == null) {
            output = inflater.inflate(R.layout.row_layout,parent,false);
            TextView tname,tdescription,tid;
//            tid = output.findViewById(R.id.id);
            tname = output.findViewById(R.id.text1);
            tdescription = output.findViewById(R.id.text2);
           TextView  ttime = output.findViewById(R.id.text4);
            TextView  tdate = output.findViewById(R.id.text3);
            TodoView_holder todoView_holder = new TodoView_holder();
           // todoView_holder.tid=tid;
            todoView_holder.tname = tname;
            todoView_holder.tdescription = tdescription;
            todoView_holder.tdate = tdate;
            todoView_holder.ttime = ttime;
            output.setTag(todoView_holder);
        }
        TodoView_holder todoView_holder = (TodoView_holder)output.getTag();
        todo tod = item.get(position);
      //  todoView_holder.tid.setText(tod.getID()+"");
        todoView_holder.tname.setText(tod.getName());
        todoView_holder.tdescription.setText(tod.getDescription());
        todoView_holder.tdate.setText(tod.getDate());
        todoView_holder.ttime.setText(tod.getTime());
        return output;
    }


    class TodoView_holder{
       // TextView tid;
        TextView tname;
        TextView tdescription;
        TextView tdate;
        TextView ttime;
    }
}
