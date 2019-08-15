package com.example.pb0386.nimblewirelesstask.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {User.class,MovieList.class}, version = 1)

public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();

}
