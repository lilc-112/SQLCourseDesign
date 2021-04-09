package com.example.sqlcourse_design.teacher.ui.home;

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

public class TeacherHomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ListView listView;
    private ArrayList<HomeListAdapter.Classes> classList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home_teacher, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        classList = new ArrayList<>();

        DatabaseHelper db = new DatabaseHelper(getContext(), "test_user", null, 1);
        Cursor cursor = db.getReadableDatabase().query(
                "classes", new String[]{"ID", "name", "teacher", "credit"}, null, null,
                null, null, null);
        while (cursor.moveToNext()) {
            HomeListAdapter.Classes data = new HomeListAdapter.Classes();
            data.ID = cursor.getInt(cursor.getColumnIndex("ID"));
            data.name = cursor.getString(cursor.getColumnIndex("name"));
            data.teacher = cursor.getString(cursor.getColumnIndex("teacher"));
            data.credit = cursor.getString(cursor.getColumnIndex("credit"));
            classList.add(data);
        }

        listView = root.findViewById(R.id.listView_classes);
        listView.setAdapter(new HomeListAdapter(getContext(), classList));

        return root;
    }
}