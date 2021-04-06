package com.example.sqlcourse_design.student.ui.dashboard;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.sqlcourse_design.DatabaseHelper;
import com.example.sqlcourse_design.R;
import com.example.sqlcourse_design.login.LoginActivity;

import java.util.ArrayList;

public class AddClassesAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private int count;
    private ArrayList<Class> classList;

    public AddClassesAdapter(Context context, ArrayList<Class> classList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.classList = classList;
        count = classList.size();
    }

    static class Class {
        public Class(int ID, String name, String teacher, int credit, boolean flag) {
            this.ID = ID;
            this.name = name;
            this.teacher = teacher;
            this.credit = credit;
            this.flag = flag;
        }

        int ID, credit;
        String name, teacher;
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

            final Class data = classList.get(position);
            holder.textView_className.setText(data.name);
            holder.textView_teacher.setText("授课教师 :" + data.teacher);
            holder.textView_credit.setText("学分 :" + data.credit);


            if (data.flag) {
                holder.button.setText("已选");
                holder.button.setEnabled(false);
            }

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(context);
                    builder.setTitle("Are you sure ?");
                    builder.setMessage("请确认是否添加选课 ?");

                    builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseHelper db = new DatabaseHelper(context,
                                    "test_user", null, 1);
                            ContentValues values = new ContentValues();
                            values.put("student_ID", LoginActivity.getLoginID());
                            values.put("class_ID", data.ID);
                            db.getWritableDatabase().insert("student_choose_class", null, values);
                            db.close();
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
