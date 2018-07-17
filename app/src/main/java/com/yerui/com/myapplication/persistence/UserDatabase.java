package com.yerui.com.myapplication.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * author:黄汝琪 on 2018/7/11.
 * email:huangruqi88@gmail.com
 * tel:
 * content:
 */
@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    private static volatile UserDatabase INSTANCE;

    public abstract UserDao userDao();

    public static UserDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "Sample.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
