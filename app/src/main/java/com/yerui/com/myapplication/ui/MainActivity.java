package com.yerui.com.myapplication.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yerui.com.myapplication.Injection;
import com.yerui.com.myapplication.R;
import com.yerui.com.myapplication.viewmodel.UserViewModel;
import com.yerui.com.myapplication.viewmodel.ViewModelFactory;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTvUserName;

    private EditText mEtUserName;

    private Button mBtUpdate;

    private ViewModelFactory mViewModelFactory;

    private UserViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvUserName = findViewById(R.id.user_name);
        mEtUserName = findViewById(R.id.user_name_input);
        mBtUpdate = findViewById(R.id.update_user);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this,mViewModelFactory).get(UserViewModel.class);

        mBtUpdate.setOnClickListener(v -> updataName());
    }

    @Override
    protected void onStart() {
        super.onStart();


        /**
         * Consumer是简易版的Observer，他有多重重载，可以自定义你需要处理的信息，我这里调用的是只接受onNext消息的方法，
         * 他只提供一个回调接口accept，由于没有onError和onCompete，无法再 接受到onError或者onCompete之后，实现函数回调。
         * 无法回调，并不代表不接收，他还是会接收到onCompete和onError之后做出默认操作，也就是监听者（Consumer）不在接收
         * Observable发送的消息，下方的代码测试了该效果。
         *   mTvUserName.setText(userName);
         throw new Exception("Unable to update username", new Throwable());
         */

        //订阅UserViewModel 中发射过来的UserName。在下一次发射时更新用户名TextView。如果发生错误，请记录Log。
        mDisposable.add(mViewModel.getUserName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //Jdk8 之后lambda语法
//                .subscribe(userName -> mTvUserName.setText(userName),
//                        throwable -> Log.d(TAG,"Unable to update username",throwable)));
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String userName) throws Exception {
                        mTvUserName.setText(userName);
//                        throw new Exception("Unable to update username", new Throwable());
                    }
                }));

    }


    @Override
    protected void onStop() {
        super.onStop();
        //取消订阅
        mDisposable.clear();
    }


    /**
     *   mBtUpdate.setEnabled(true);
        throw new Exception("Unable to update username", new Throwable());
     */
    private void updataName() {
        String userName = mEtUserName.getText().toString();
        //在没用完成更新数据之前禁用button
        mBtUpdate.setEnabled(false);
        //订阅更新数据（即用户名），用户名更新之后
        mDisposable.add(mViewModel.updateUserName(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //Jdk8 之后lambda语法
//                .subscribe(() -> mBtUpdate.setEnabled(true),
//                        throwable -> Log.d(TAG, "Unable to update username", throwable)));
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        mBtUpdate.setEnabled(true);
                    }
                }));
    }
}
