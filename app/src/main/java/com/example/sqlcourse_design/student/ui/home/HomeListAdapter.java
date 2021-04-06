package com.example.sqlcourse_design.student.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sqlcourse_design.DatabaseHelper;
import com.example.sqlcourse_design.R;

import java.util.ArrayList;

public class HomeListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Integer> classIDList;
    private ArrayList<String> scoreList;
    boolean[] indexList;

    private int count;

    public HomeListAdapter(Context context, ArrayList<Integer> classIDList,
                           ArrayList<String> scoreList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.classIDList = null;
        this.scoreList = scoreList;
        this.classIDList = classIDList;
        count = classIDList.size();
        indexList = new boolean[count];
        for (int i = 0; i < count; i++) {
            indexList[i] = true;
        }
    }

    static class ViewHolder {
        public TextView textView_className, textView_teacher, textView_score;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.home_list_view_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textView_className = convertView.findViewById(R.id.textView_className);
            viewHolder.textView_score = convertView.findViewById(R.id.textView_score);
            viewHolder.textView_teacher = convertView.findViewById(R.id.textView_teacher);

            convertView.setTag(viewHolder);


            DatabaseHelper db = new DatabaseHelper(this.context, "test_user", null, 1);
            Cursor cursor = db.getReadableDatabase().query(
                    "classes", new String[]{"ID", "name", "teacher", "credit"}, null, null,
                    null, null, null);

            while (cursor.moveToNext()) {
                Integer classID = cursor.getInt(cursor.getColumnIndex("ID"));
                if (classIDList.contains(classID)) {
                    int index = classIDList.indexOf(classID);
                    if (!indexList[index]) {
                        continue;
                    }
                    viewHolder.textView_className.setText(cursor.getString(cursor.getColumnIndex("name")));
                    viewHolder.textView_score.setText("成绩 : " + scoreList.get(index));
                    viewHolder.textView_teacher.setText("教师 : " +
                            cursor.getString(cursor.getColumnIndex("teacher")));
                    indexList[index] = false;

                    break;
                }
            }
            db.close();
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}
