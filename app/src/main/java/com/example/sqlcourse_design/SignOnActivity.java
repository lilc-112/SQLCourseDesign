package com.example.sqlcourse_design;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlcourse_design.login.LoginActivity;

public class SignOnActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText password_confirm;
    private Button button;
    private EditText studentName;
    private EditText phoneNumber;
    private RadioGroup radioGroup;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_on);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        password_confirm = findViewById(R.id.password_confirm);
        button = findViewById(R.id.login);
        studentName = findViewById(R.id.editText_studentName);
        phoneNumber = findViewById(R.id.editText_phoneNumber);
        radioGroup = findViewById(R.id.radioGroup);

        Drawable drawable_user = getResources().getDrawable(R.drawable.user);
        Drawable drawable_pass = getResources().getDrawable(R.drawable.password);
        drawable_user.setBounds(0, 0, 60, 60);
        drawable_pass.setBounds(0, 0, 60, 60);
        username.setCompoundDrawables(drawable_user, null, null, null);
        password.setCompoundDrawables(drawable_pass, null, null, null);
        password_confirm.setCompoundDrawables(drawable_pass, null, null, null);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = radioGroup.findViewById(checkedId);
                sex = radioButton.getText().toString();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.getText().toString().equals(
                        password_confirm.getText().toString())) {
                    Toast.makeText(SignOnActivity.this, "两次密码输入不一致，请重新输入",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseHelper db = new DatabaseHelper(SignOnActivity.this,
                        "test_user", null, 1);
                Cursor cursor = db.getReadableDatabase().query("user", new String[]{"username"},
                        null, null, null, null, null);
                while (cursor.moveToNext()) {
                    if (username.getText().toString().equals(cursor.getString(
                            cursor.getColumnIndex("username")))) {
                        Toast.makeText(SignOnActivity.this,
                                "该用户名已被注册", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                ContentValues values = new ContentValues();
                values.put("username", username.getText().toString());
                values.put("password", password.getText().toString());
                db.getWritableDatabase().insert("user", null, values);

                ContentValues values1 = new ContentValues();
                values1.put("ID", username.getText().toString());
                values1.put("name", studentName.getText().toString());
                values1.put("sex", sex);
                values1.put("phoneNumber", phoneNumber.getText().toString());
                db.getWritableDatabase().insert("student", null, values1);

                db.close();

                Toast.makeText(SignOnActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignOnActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

