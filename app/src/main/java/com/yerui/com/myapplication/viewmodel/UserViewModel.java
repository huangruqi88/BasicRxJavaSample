package com.yerui.com.myapplication.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.yerui.com.myapplication.UserDataSource;
import com.yerui.com.myapplication.persistence.User;
import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * author:黄汝琪 on 2018/7/11.
 * email:huangruqi88@gmail.com
 * tel:
 * content: UserActivity的ViewModel
 */
public class UserViewModel extends ViewModel {

    private final UserDataSource mDatabaseSource;

    private User mUser;

    public UserViewModel(UserDataSource mDatabaseSource) {
        this.mDatabaseSource = mDatabaseSource;
    }

    /**
     * 获取用户名
     * @return {@link Flowable} 用户名更新时Flowable将会将事件发出
     */
    public Flowable<String> getUserName(){
        return mDatabaseSource.getUser()
                .map(user -> {
                    //获取用户每次发射的用户名
                    mUser = user;
                    return user.getUserName();

                });
    }


    /**
     * 更新用户名
     *
     * Completable 参考https://www.jianshu.com/p/45309538ad94
     * @param userName 新的用户名 Completable 它从来不发射数据，只处理 onComplete 和 onError 事件。可以看成是Rx的Runnable。
     * @return {@link Completable}  当用户名更新时
     */
    public Completable updateUserName(String userName){
        return Completable.fromAction(() -> {
            //如果没有就创建一个新的用户。
            // 如果有了一个用户，那么，由于用户对象是不可变的，那么就创建一个新用户，使用之前用户的id和更新后的用户名
            mUser = mUser == null ? new User(userName) : new User(mUser.getId(),userName);
            mDatabaseSource.insertOrUpdateUser(mUser);
        });
    }
}
