package com.example.sqlcourse_design.teacher.ui.gallery;

// import android.app.AlertDialog;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sqlcourse_design.DatabaseHelper;
import com.example.sqlcourse_design.R;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    private class MyAlertDialog extends AlertDialog {

        public MyAlertDialog(@NonNull Context context) {
            super(context);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        final EditText editText_className = root.findViewById(R.id.editText_className);
        final EditText editText_credit = root.findViewById(R.id.editText_credit);
        final Button button = root.findViewById(R.id.button_addClass);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getActivity());
                builder.setTitle("Are you sure ?");
                builder.setMessage("请确认是否添加选课 ?");

                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper databaseHelper = new DatabaseHelper(
                                getContext(), "test_user", null, 1);
                        ContentValues values = new ContentValues();
                        values.put("name", editText_className.getText().toString());
                        values.put("credit", editText_credit.getText().toString());
                        values.put("teacher", "Admin");
                        databaseHelper.getWritableDatabase().insert("classes", null, values);
                        databaseHelper.close();
                    }
                });

                builder.setPositiveButton("No", null);
                builder.show();
            }
        });

        return root;
    }
}