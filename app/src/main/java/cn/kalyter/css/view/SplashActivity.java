package cn.kalyter.css.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;


import cn.kalyter.css.R;
import cn.kalyter.css.contract.SplashContract;
import cn.kalyter.css.presenter.SplashPresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseActivity;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {
    private SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mPresenter.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void setupPresenter() {
        mPresenter = new SplashPresenter(App.getInjectClass().getSplashSource(), this);
    }

    @Override
    public void showTutorial() {
        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }

    @Override
    public void showLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void showMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showLocate() {
        startActivity(new Intent(this, LocateCommunityActivity.class));
        finish();
    }
}
