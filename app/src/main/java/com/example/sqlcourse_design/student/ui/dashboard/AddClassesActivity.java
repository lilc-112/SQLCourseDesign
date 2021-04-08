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

public class AddClassesActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<AddClassesAdapter.Class> classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classes);

        DatabaseHelper db = new DatabaseHelper(this, "test_user", null, 1);
        Cursor cursor = db.getReadableDatabase().query(
                "classes", new String[]{"ID", "name", "teacher", "credit"}, null, null,
                null, null, null);
        classList = new ArrayList<>();
        while (cursor.moveToNext()) {
            boolean flag = isChosen(cursor.getInt(cursor.getColumnIndex("ID")));
            classList.add(new AddClassesAdapter.Class(
                    cursor.getInt(cursor.getColumnIndex("ID")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("teacher")),
                    cursor.getInt(cursor.getColumnIndex("credit")),
                    flag));
        }
        db.close();

        listView = findViewById(R.id.listView_addClasses);
        listView.setAdapter(new AddClassesAdapter(this, classList));
    }

    public void refresh() {
        finish();
        Intent intent = new Intent(this, AddClassesActivity.class);
        startActivity(intent);
    }

    private boolean isChosen(int ID) {
        DatabaseHelper db = new DatabaseHelper(this, "test_user", null, 1);
        Cursor cursor = db.getReadableDatabase().query(
                "student_choose_class", new String[]{"student_ID", "class_ID"}, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(
                    "student_ID")).equals(LoginActivity.getLoginID()) &&
                    cursor.getInt(cursor.getColumnIndex("class_ID")) == ID) {
                return true;
            }
        }
        return false;
    }
}
