package com.example.sqlcourse_design.student.ui.notifications;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlcourse_design.DatabaseHelper;
import com.example.sqlcourse_design.R;
import com.example.sqlcourse_design.login.LoginActivity;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText oldPassword, newPassword, newPassword_2;
    private Button button;

    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        newPassword_2 = findViewById(R.id.newPasswordConfirm);
        button = findViewById(R.id.changePassword);

        DatabaseHelper db = new DatabaseHelper(this, "test_user", null, 1);
        Cursor cursor = db.getReadableDatabase().query(
                "user", new String[]{"username", "password"}, null,
                null, null, null, null
        );
        while (cursor.moveToNext()) {
            if (LoginActivity.getLoginID().equals(
                    cursor.getString(cursor.getColumnIndex("username")))) {
                password = cursor.getString(cursor.getColumnIndex("password"));
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.equals(oldPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "旧密码输入错误",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPassword.getText().toString().equals(
                        newPassword_2.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "两次密码输入不一致",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new
                        AlertDialog.Builder(ChangePasswordActivity.this);
                builder.setTitle("Sure ?");
                builder.setMessage("确认修改 ?");

                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String password = newPassword.getText().toString();
                        ContentValues values = new ContentValues();
                        values.put("password", password);

                        DatabaseHelper db = new DatabaseHelper(ChangePasswordActivity.this,
                                "test_user", null, 1);
                        db.getWritableDatabase().update(
                                "user", values, "username=?",
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
