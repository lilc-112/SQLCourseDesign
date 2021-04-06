package com.example.sqlcourse_design.student.ui.notifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sqlcourse_design.R;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("");
            }
        });

        String[] strings = new String[]{
                "修改个人资料", "修改密码", "退出登录"
        };

        listView = root.findViewById(R.id.listView_personal);
        listView.setAdapter(new ArrayAdapter<String>(
                getContext(), R.layout.dashboard_list_view_item, strings));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(getContext(), ChangeDataActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                    startActivity(intent);
                } else if (position == 2) {
                    AlertDialog.Builder builder = new
                            AlertDialog.Builder(getContext());
                    builder.setTitle("Quit ?");
                    builder.setMessage("确认退出 ?");

                    builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "退出成功", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    });
                    builder.setPositiveButton("No", null);
                    builder.show();
                }
            }
        });

        return root;
    }
}