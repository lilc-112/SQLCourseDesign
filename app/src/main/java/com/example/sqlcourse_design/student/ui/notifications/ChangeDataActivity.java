package com.example.sqlcourse_design.student.ui.notifications;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlcourse_design.DatabaseHelper;
import com.example.sqlcourse_design.R;
import com.example.sqlcourse_design.login.LoginActivity;

public class ChangeDataActivity extends AppCompatActivity {

    private EditText studentName;
    private EditText phoneNumber;
    private RadioGroup radioGroup;
    private Button button;

    private String sex;
    private String name, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data);

        studentName = findViewById(R.id.editText_studentName);
        phoneNumber = findViewById(R.id.editText_phoneNumber);
        radioGroup = findViewById(R.id.radioGroup);

        DatabaseHelper db = new DatabaseHelper(this, "test_user", null, 1);
        Cursor cursor = db.getReadableDatabase().query(
                "student", new String[]{"ID", "phoneNumber", "sex", "name"}, null,
                null, null, null, null);

        while (cursor.moveToNext()) {
            if (LoginActivity.getLoginID().equals(
                    cursor.getString(cursor.getColumnIndex("ID")))) {
                studentName.setText(cursor.getString(cursor.getColumnIndex("name")));
                phoneNumber.setText(cursor.getString(cursor.getColumnIndex("phoneNumber")));
                radioGroup.check(cursor.getString(
                        cursor.getColumnIndex("sex")).equals("男") ? 0 : 1);
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = radioGroup.findViewById(checkedId);
                sex = radioButton.getText().toString();
            }
        });

        button = findViewById(R.id.changeData);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new
                        AlertDialog.Builder(ChangeDataActivity.this);
                builder.setTitle("Sure ?");
                builder.setMessage("确认修改 ?");

                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name = studentName.getText().toString();
                        number = phoneNumber.getText().toString();
                        ContentValues values = new ContentValues();
                        values.put("name", name);
                        values.put("sex", sex);
                        values.put("phoneNumber", number);
                        DatabaseHelper db = new DatabaseHelper(ChangeDataActivity.this,
                                "test_user", null, 1);
                        db.getWritableDatabase().update(
                                "student", values, "ID=?",
                                new String[]{LoginActivity.getLoginID()}
                        );

                        Toast.makeText(getApplicationContext(), "修改成功",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setPositiveButton("No", null);
                builder.show();
            }
        });
    }
}
