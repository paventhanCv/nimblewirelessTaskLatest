package com.example.pb0386.nimblewirelesstask.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User where username= :username and password= :password or email= :password or mobileno= :password")
    User getUser(String username, String password);

    @Query("SELECT * FROM MovieList WHERE id >= :id LIMIT :size")
    public List<MovieList> getallMovie(int id, int size);

    @Insert
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(MovieList movieList);

    @Delete
    public void delete(MovieList movieList);
}
