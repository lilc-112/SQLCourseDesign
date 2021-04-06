package com.example.sqlcourse_design.ui.slideshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.sqlcourse_design.R;

public class SlideshowAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public SlideshowAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
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

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}
