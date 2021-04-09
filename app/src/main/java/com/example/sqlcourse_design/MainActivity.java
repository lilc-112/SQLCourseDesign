package com.example.sqlcourse_design;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlcourse_design.login.LoginActivity;
import com.example.sqlcourse_design.teacher.Main2Activity;

public class MainActivity extends AppCompatActivity {

    private Button button_signIN;
    private Button button_signOn;
    private Button button_Admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_signIN = findViewById(R.id.button_signIn);
        button_signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        button_signOn = findViewById(R.id.button_signOn);
        button_signOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignOnActivity.class);
                startActivity(intent);
            }
        });

        button_Admin = findViewById(R.id.forAdmin);
        button_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }
}
