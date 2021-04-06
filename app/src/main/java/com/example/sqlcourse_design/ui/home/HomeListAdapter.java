package com.example.sqlcourse_design.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sqlcourse_design.R;

import java.util.ArrayList;

public class HomeListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Classes> classList;

    private int count;

    public HomeListAdapter(Context context, ArrayList<Classes> classList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.classList = classList;
        count = classList.size();
    }

    static class ViewHolder {
        public TextView textView_className, textView_teacher, textView_score;
    }

    static class Classes {
        public int ID;
        public String name, teacher, credit;
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

            Classes data = classList.get(position);
            viewHolder.textView_className.setText(data.name);
            viewHolder.textView_teacher.setText("教师 :" + data.teacher);
            viewHolder.textView_score.setText("学分 :" + data.credit);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}
