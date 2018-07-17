package com.yerui.com.myapplication.persistence;

import com.yerui.com.myapplication.UserDataSource;

import io.reactivex.Flowable;

/**
 * author:黄汝琪 on 2018/7/11.
 * email:huangruqi88@gmail.com
 * tel:
 * content:
 */
public class LocaleUserDataSource implements UserDataSource {

    private final UserDao mUserDao;

    public LocaleUserDataSource(UserDao mUserDao) {
        this.mUserDao = mUserDao;
    }

    @Override
    public Flowable<User> getUser() {
        return mUserDao.getUser();
    }

    @Override
    public void insertOrUpdateUser(User user) {
        mUserDao.insertUser(user);
    }

    @Override
    public void deleteAllUsers() {
        mUserDao.deleteAllUsers();
    }
}
