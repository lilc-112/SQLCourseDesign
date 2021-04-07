package com.example.sqlcourse_design.ui.tools;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    private ListView listView;
    private ArrayList<ToolsAdapter.Classes> classList;

    private static int classID;

    public static int getClassID() {
        return classID;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        classID = 0;
        classList = new ArrayList<>();

        DatabaseHelper db = new DatabaseHelper(getContext(), "test_user", null, 1);
        Cursor cursor = db.getReadableDatabase().query(
                "classes", new String[]{"ID", "name", "teacher", "credit"}, null, null,
                null, null, null);
        while (cursor.moveToNext()) {
            ToolsAdapter.Classes data = new ToolsAdapter.Classes();
            data.ID = cursor.getInt(cursor.getColumnIndex("ID"));
            data.name = cursor.getString(cursor.getColumnIndex("name"));
            data.teacher = cursor.getString(cursor.getColumnIndex("teacher"));
            data.credit = cursor.getString(cursor.getColumnIndex("credit"));
            classList.add(data);
        }

        listView = root.findViewById(R.id.listView_addScore);
        listView.setAdapter(new ToolsAdapter(getContext(), classList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                classID = classList.get(position).ID;
                Intent intent = new Intent(getContext(), AddScoreActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}