package com.example.sqlcourse_design.ui.slideshow;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.sqlcourse_design.DatabaseHelper;
import com.example.sqlcourse_design.R;

import java.util.ArrayList;

public class SlideshowAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Classes> classArrayList;
    private int count;

    public SlideshowAdapter(Context context, ArrayList<Classes> classArrayList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.classArrayList = classArrayList;
        this.count = classArrayList.size();
    }

    static class Classes {
        public Classes(int ID, String name, String teacher, int credit, boolean flag) {
            this.ID = ID;
            this.name = name;
            this.teacher = teacher;
            this.credit = credit;
            this.flag = flag;
        }

        public int ID, credit;
        public String name, teacher;
        boolean flag;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        TextView textView_className, textView_teacher, textView_credit;
        Button button;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.addclass_list_view_item, null);
            holder = new ViewHolder();
            holder.textView_className = convertView.findViewById(R.id.textView_className);
            holder.textView_teacher = convertView.findViewById(R.id.textView_teacher);
            holder.textView_credit = convertView.findViewById(R.id.textView_credit);
            holder.button = convertView.findViewById(R.id.button_addClass);

            final Classes data = classArrayList.get(position);
            holder.textView_className.setText(data.name);
            holder.textView_teacher.setText("授课教师 :" + data.teacher);
            holder.textView_credit.setText("学分 :" + data.credit);
            holder.button.setText("删除课程");
            holder.button.setEnabled(!data.flag);
            if (data.flag) holder.textView_credit.setText("学分 :" + data.credit);

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(context);
                    builder.setTitle("Are you sure ?");
                    builder.setMessage("确认删除该课程 ?\n所有已选课的同学都将强制退课。");

                    builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseHelper db = new DatabaseHelper(context,
                                    "test_user", null, 1);
                            String sql = "PRAGMA foreign_keys = ON";
                            db.getWritableDatabase().execSQL(sql);
                            sql = "DELETE FROM classes WHERE ID=" + data.ID;
                            db.getWritableDatabase().execSQL(sql);
                            db.close();
                            Toast.makeText(context, "课程已删除", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                    });

                    builder.setPositiveButton("No", null);
                    builder.show();
                }
            });

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}
