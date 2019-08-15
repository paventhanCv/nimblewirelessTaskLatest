package com.example.pb0386.nimblewirelesstask.activity;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pb0386.nimblewirelesstask.R;
import com.example.pb0386.nimblewirelesstask.Database.User;
import com.example.pb0386.nimblewirelesstask.Database.UserDao;
import com.example.pb0386.nimblewirelesstask.Database.UserDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText log_username;
    EditText log_password;
    Button login_button;
    Button signup_button;
    private UserDatabase database;
    private UserDao userDao;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        log_username = findViewById(R.id.log_username);
        log_password = findViewById(R.id.log_pasword);
        login_button = findViewById(R.id.login_button);
        signup_button = findViewById(R.id.sign_up_button);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Checking User...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        database = Room.databaseBuilder(this, UserDatabase.class, "nimble-database.db")
                .allowMainThreadQueries()
                .build();

        userDao = database.getUserDao();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    final String str_username = log_username.getText().toString();
                    final String str_password = log_password.getText().toString();

                    if (str_username != null && str_password != null) {
                        progressDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                User user = userDao.getUser(str_username, str_password);
                                if (user != null) {
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Unregistered user, or incorrect", Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }
                        }, 1000);
                    } else {
                        Toast.makeText(LoginActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.toString();
                }
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sign_in_intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(sign_in_intent);
                finish();

            }
        });

    }
}
