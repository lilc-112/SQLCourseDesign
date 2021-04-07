package com.example.sqlcourse_design.ui.tools;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlcourse_design.DatabaseHelper;
import com.example.sqlcourse_design.R;

import java.util.ArrayList;

public class AddScoreActivity extends AppCompatActivity {

    private ListView listView;
    private Button button;

    private ArrayList<AddScoreAdapter.Student> studentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);

        int classID = ToolsFragment.getClassID();

        studentArrayList = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(this, "test_user", null, 1);
        Cursor cursor = db.getReadableDatabase().query(
                "student_choose_class", new String[]{"student_ID", "class_ID", "score"}, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex("class_ID")) == classID) {
                studentArrayList.add(new AddScoreAdapter.Student(
                        cursor.getInt(cursor.getColumnIndex("student_ID")),
                        cursor.getInt(cursor.getColumnIndex("score"))
                ));
            }
        }

        cursor = db.getReadableDatabase().query(
                "student", new String[]{"ID", "name"}, null,
                null, null, null, null);
        ArrayList<Integer> idList = new ArrayList<>();
        for (AddScoreAdapter.Student s : studentArrayList) {
            idList.add(s.ID);
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            if (idList.contains(id)) {
                int index = idList.indexOf(id);
                studentArrayList.get(index).setName(
                        cursor.getString(cursor.getColumnIndex("name")));
            }
        }

        listView = findViewById(R.id.listView_addScore);
        listView.setAdapter(new AddScoreAdapter(this, studentArrayList));

        button = findViewById(R.id.button_addScore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
