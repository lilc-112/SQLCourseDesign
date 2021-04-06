package com.example.sqlcourse_design.login.data;

import android.database.Cursor;

import com.example.sqlcourse_design.DatabaseHelper;
import com.example.sqlcourse_design.login.data.model.LoggedInUser;
import com.example.sqlcourse_design.login.LoginActivity;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            DatabaseHelper databaseHelper = new DatabaseHelper(
                    LoginActivity.getContext(), "test_user", null, 1
            );
            Cursor cursor = databaseHelper.getReadableDatabase().query(
                    "user", new String[]{"username", "password"},
                    null, null, null, null, null
            );
            while (cursor.moveToNext()) {
                boolean flag = cursor.getString(cursor.getColumnIndex(
                        "username")).equals(username);
                if (!flag) {
                    continue;
                } else {
                    if (!cursor.getString(cursor.getColumnIndex(
                            "password")).equals(password)) {
                        return new Result.Failed<>("密码输入错误");
                    } else {

                        LoggedInUser fakeUser = new LoggedInUser(
                                java.util.UUID.randomUUID().toString(), username);

                        /*LoggedInUser fakeUser =
                                new LoggedInUser(
                                        java.util.UUID.randomUUID().toString(),
                                        "Jane Doe");*/
                        return new Result.Success<>(fakeUser);
                    }
                }
            }
            return new Result.Failed<>("用户名不存在");
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
