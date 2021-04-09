package com.example.sqlcourse_design.teacher.ui.slideshow;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sqlcourse_design.DatabaseHelper;
import com.example.sqlcourse_design.R;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    private ListView listView;
    private ArrayList<SlideshowAdapter.Classes> classArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("");
            }
        });

        classArrayList = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(getContext(),
                "test_user", null, 1);
        Cursor cursor = db.getReadableDatabase().query("classes",
                new String[]{"ID", "name", "teacher", "credit"}, null, null,
                null, null, null);
        while (cursor.moveToNext()) {
            int ID = cursor.getInt(cursor.getColumnIndex("ID"));
            boolean flag = hasScore(ID);
            classArrayList.add(
                    new SlideshowAdapter.Classes(
                            ID,
                            cursor.getString(cursor.getColumnIndex("name")),
                            cursor.getString(cursor.getColumnIndex("teacher")),
                            cursor.getInt(cursor.getColumnIndex("credit")),
                            flag)
            );
        }

        listView = root.findViewById(R.id.listView_manageClasses);
        listView.setAdapter(new SlideshowAdapter(getContext(), classArrayList));

        return root;
    }

    boolean hasScore(int ID) {
        DatabaseHelper db = new DatabaseHelper(getContext(), "test_user", null, 1);
        Cursor cursor = db.getReadableDatabase().query(
                "student_choose_class", new String[]{"score", "class_ID"}, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex("class_ID")) == ID) {
                if (!cursor.getString(cursor.getColumnIndex("score")).equals(""))
                    return true;
            }
        }
        return false;
    }
}