package com.yerui.com.myapplication;

import com.yerui.com.myapplication.persistence.User;

import io.reactivex.Flowable;

/**
 * author:黄汝琪 on 2018/7/11.
 * email:huangruqi88@gmail.com
 * tel:
 * content:
 */
public interface UserDataSource {

    /**
     * 获取用户数据 Flowable 背压策略
     * 背压参考https://www.jianshu.com/p/ff8167c1d191
     * @return 源数据（即数据库）得到的用户
     */
    Flowable<User> getUser();

    /**
     * 插入或者更新数据源（即数据库），如果没有数据就插入如果有数据就更新
     * @param user 插入或者更新的用户
     */
    void insertOrUpdateUser(User user);

    /**
     * 从数据源（即数据库）中删除数据
     */
    void deleteAllUsers();
}
