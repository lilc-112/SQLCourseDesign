package com.example.sqlcourse_design.student.ui.dashboard;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlcourse_design.DatabaseHelper;
import com.example.sqlcourse_design.R;
import com.example.sqlcourse_design.login.LoginActivity;

import java.util.ArrayList;

public class ManageClassesActivity extends AppCompatActivity {

    private ListView listView;

    private ArrayList<Integer> classIDList;
    private ArrayList<String> scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_classes);

        DatabaseHelper db = new DatabaseHelper(this, "test_user", null, 1);
        Cursor cursor = db.getReadableDatabase().query(
                "student_choose_class", new String[]{"student_ID", "class_ID", "score"}, null,
                null, null, null, null);
        classIDList = new ArrayList<>();
        scoreList = new ArrayList<>();

        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(
                    "student_ID")).equals(LoginActivity.getLoginID())) {
                classIDList.add(cursor.getInt(cursor.getColumnIndex("class_ID")));
                scoreList.add(cursor.getString(cursor.getColumnIndex("score")));
            }
        }

        listView = findViewById(R.id.listView_manageClasses);
        listView.setAdapter(new ManageClassesAdapter(this, classIDList, scoreList));
    }

    public void refresh() {
        finish();
        Intent intent = new Intent(this, ManageClassesActivity.class);
        startActivity(intent);
    }

}
