package com.yerui.com.myapplication;

import android.content.Context;

import com.yerui.com.myapplication.persistence.LocaleUserDataSource;
import com.yerui.com.myapplication.persistence.UserDatabase;
import com.yerui.com.myapplication.viewmodel.ViewModelFactory;

/**
 * author:黄汝琪 on 2018/7/11.
 * email:huangruqi88@gmail.com
 * tel:
 * content: s注入数据源
 */
public class Injection {

    public static UserDataSource provideUserDataSource(Context context) {
        UserDatabase database = UserDatabase.getInstance(context);
        return new LocaleUserDataSource(database.userDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context){
        UserDataSource dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);

    }
}
