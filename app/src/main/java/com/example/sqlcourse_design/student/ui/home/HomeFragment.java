package com.example.sqlcourse_design.student.ui.home;

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
import com.example.sqlcourse_design.login.LoginActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView textView_name;
    private TextView textView_ID;
    private TextView textView_sex;
    private TextView textView_phoneNumber;
    private TextView textView_credit;

    private ArrayList<Integer> classIDList;
    private ArrayList<String> scoreList;

    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("");
            }
        });

        String loginID = LoginActivity.getLoginID();

        textView_name = root.findViewById(R.id.textView_studentName);
        textView_ID = root.findViewById(R.id.textView_studentID);
        textView_sex = root.findViewById(R.id.textView_studentSex);
        textView_phoneNumber = root.findViewById(R.id.textView_studentPhoneNumber);
        textView_credit = root.findViewById(R.id.textView_studentCredit);

        DatabaseHelper db = new DatabaseHelper(getContext(), "test_user", null, 1);
        Cursor cursor = db.getReadableDatabase().query(
                "student", new String[]{"ID", "name", "sex", "phoneNumber", "totalCredit"}, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex("ID");
            if (loginID.equals(cursor.getString(columnIndex))) {
                textView_name.setText("姓名 :" +
                        cursor.getString(cursor.getColumnIndex("name")));
                textView_ID.setText("学号 :" + loginID);
                textView_sex.setText("性别 :" +
                        cursor.getString(cursor.getColumnIndex("sex")));
                textView_phoneNumber.setText("电话 :" +
                        cursor.getString(cursor.getColumnIndex("phoneNumber")));
                textView_credit.setText("学分总计 :" +
                        cursor.getInt(cursor.getColumnIndex("totalCredit")));

                break;
            }
        }

        cursor = db.getReadableDatabase().query(
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

        listView = root.findViewById(R.id.listView_classes);
        listView.setAdapter(new HomeListAdapter(getContext(), classIDList, scoreList));

        return root;
    }
}