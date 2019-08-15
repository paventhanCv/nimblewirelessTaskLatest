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

public class SignUpActivity extends AppCompatActivity {

    EditText sign_username;
    EditText sign_password;
    EditText sign_email;
    EditText sign_mobileno;
    Button sign_in_button;
    private UserDao userDao;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sign_username = findViewById(R.id.sign_username);
        sign_password = findViewById(R.id.sign_pasword);
        sign_email = findViewById(R.id.sign_email);
        sign_mobileno = findViewById(R.id.sign_mobileno);
        sign_in_button = findViewById(R.id.sign_in_button);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        userDao = Room.databaseBuilder(this, UserDatabase.class, "nimble-database.db")
                .allowMainThreadQueries()
                .build()
                .getUserDao();

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String str_username = sign_username.getText().toString();
                final String str_password = sign_password.getText().toString();
                final String str_email = sign_email.getText().toString();
                final String str_mobileno = sign_mobileno.getText().toString();

                if (str_username != null && str_password != null && str_email != null && str_mobileno != null) {

                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User(str_username, str_password, str_email, str_mobileno);
                            userDao.insert(user);
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        }
                    }, 1000);

                } else {
                    Toast.makeText(SignUpActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
