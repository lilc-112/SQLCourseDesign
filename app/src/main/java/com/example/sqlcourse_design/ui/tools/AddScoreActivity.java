package com.example.sqlcourse_design.ui.tools;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlcourse_design.DatabaseHelper;
import com.example.sqlcourse_design.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AddScoreActivity extends AppCompatActivity {

    private ListView listView;
    private Button button;
    private int classID;

    private ArrayList<AddScoreAdapter.Student> studentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);

        this.classID = ToolsFragment.getClassID();

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

        final AddScoreAdapter adapter = new AddScoreAdapter(this, studentArrayList);

        listView = findViewById(R.id.listView_addScore);
        listView.setAdapter(adapter);

        button = findViewById(R.id.button_addScore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(AddScoreActivity.this, "test_user", null, 1);
                ContentValues values = new ContentValues();
                HashMap<Integer, String> map = adapter.getMap();
                Iterator i = map.keySet().iterator();
                while (i.hasNext()) {
                    int ID = (int) i.next();
                    String sql = "UPDATE student_choose_class SET score = " + map.get(ID) +
                            " WHERE student_ID = " + ID + " AND class_ID = " +
                            AddScoreActivity.this.classID;
                    db.getWritableDatabase().execSQL(sql);
                }

                Toast.makeText(AddScoreActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
