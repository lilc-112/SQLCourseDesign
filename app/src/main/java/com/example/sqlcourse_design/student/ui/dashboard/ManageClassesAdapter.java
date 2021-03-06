package com.example.sqlcourse_design.student.ui.dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.sqlcourse_design.DatabaseHelper;
import com.example.sqlcourse_design.R;

import java.util.ArrayList;

public class ManageClassesAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    private ArrayList<Integer> classIDList;
    private ArrayList<String> scoreList;
    boolean[] indexList;

    private int count;


    public ManageClassesAdapter(Context context, ArrayList<Integer> classIDList,
                                ArrayList<String> scoreList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.scoreList = scoreList;
        this.classIDList = classIDList;
        count = classIDList.size();
        indexList = new boolean[count];
        for (int i = 0; i < count; i++) {
            indexList[i] = true;
        }
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

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DatabaseHelper db = new DatabaseHelper(context, "test_user", null, 1);
        Cursor cursor = db.getReadableDatabase().query(
                "classes", new String[]{"ID", "name", "teacher", "credit"}, null, null,
                null, null, null);

        while (cursor.moveToNext()) {
            final Integer classID = cursor.getInt(cursor.getColumnIndex("ID"));
            if (classIDList.contains(classID)) {
                int index = classIDList.indexOf(classID);
                if (!indexList[index]) {
                    continue;
                }
                if (!"".equals(scoreList.get(index))) {
                    holder.button.setEnabled(false);
                }
                holder.textView_className.setText(cursor.getString(cursor.getColumnIndex("name")));
                holder.textView_credit.setText("?????? : " +
                        cursor.getString(cursor.getColumnIndex("credit")));
                holder.textView_teacher.setText("?????? : " +
                        cursor.getString(cursor.getColumnIndex("teacher")));
                holder.button.setText("??????");
                indexList[index] = false;

                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(context);
                        builder.setTitle("Are you sure ?");
                        builder.setMessage("?????????????????????????????? ?");

                        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseHelper db = new DatabaseHelper(context,
                                        "test_user", null, 1);
                                db.getWritableDatabase().delete(
                                        "student_choose_class", "class_ID=?",
                                        new String[]{classID.toString()});
                                db.close();

                                classIDList.remove(classID);
                                count -= 1;
                                notifyDataSetChanged();
                            }
                        });

                        builder.setPositiveButton("No", null);
                        builder.show();
                    }
                });

                break;
            }
        }
        db.close();

        return convertView;
    }
}
