package com.yerui.com.myapplication.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import io.reactivex.Flowable;

/**
 * author:黄汝琪 on 2018/7/11.
 * email:huangruqi88@gmail.com
 * tel:
 * content:
 */

@Dao
public interface UserDao {

    /**
     * 获取用户表中的一条数据
     * @return
     */
//    @Query("SELECT * FROM Users LIMIT 1")
    @Query("SELECT * FROM Users")
    Flowable<User> getUser();

    /**
     * 在数据库中插入一条数据，如果已经存在数据则将其替换为最新的数据
     * @param user
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    /**
     * 删除所有的用户
     */
    @Query("DELETE FROM Users")
    void deleteAllUsers();

//
//    @Update();
//    void updata();
}
