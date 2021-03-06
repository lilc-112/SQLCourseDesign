package com.example.sqlcourse_design.teacher.ui.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sqlcourse_design.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AddScoreAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Student> studentArrayList;
    private HashMap<Integer, EditText> map;
    private int count;

    public AddScoreAdapter(Context context, ArrayList<Student> studentArrayList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.studentArrayList = studentArrayList;
        this.count = studentArrayList.size();
        this.map = new HashMap();
    }

    public HashMap getMap() {
        HashMap<Integer, String> map = new HashMap<>();
        Iterator i = this.map.keySet().iterator();
        while (i.hasNext()) {
            int ID = (int) i.next();
            map.put(ID, this.map.get(ID).getText().toString());
        }
        return map;
    }

    static class Student {
        public int ID, score;
        public String name;

        public Student(int ID, int score) {
            this.ID = ID;
            this.score = score;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return this;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        public TextView textView_name, textView_ID;
        public EditText editText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.addscore_list_view_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textView_name = convertView.findViewById(R.id.textView_studentName);
            viewHolder.textView_ID = convertView.findViewById(R.id.textView_studentID);
            viewHolder.editText = convertView.findViewById(R.id.editText_score);

            Student data = studentArrayList.get(position);
            viewHolder.textView_name.setText(data.name);
            viewHolder.textView_ID.setText("?????? :" + data.ID);
            viewHolder.editText.setText("" + data.score);
            map.put(data.ID, viewHolder.editText);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}
