package com.example.pb0386.nimblewirelesstask.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class User  {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String username;
    private String password;
    private String email;
    private String mobileno;

    public User(String username, String password, String email, String mobileno) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobileno = mobileno;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

}
