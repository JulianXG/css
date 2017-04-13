package cn.kalyter.css.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.LoginContract;
import cn.kalyter.css.model.LoginUser;
import cn.kalyter.css.presenter.LoginPresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseActivity;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {
    @BindView(R.id.email)
    EditText mEmail;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.login)
    AppCompatButton mLogin;
    @BindView(R.id.sign_up)
    TextView mSignUp;

    private ProgressDialog mProgressDialog;

    private LoginContract.Presenter mPresenter;

    @OnClick(R.id.login)
    void login() {
        if (!TextUtils.isEmpty(mEmail.getText()) && !TextUtils.isEmpty(mPassword.getText())) {
            LoginUser loginUser = new LoginUser();
            loginUser.setUsername(mEmail.getText().toString());
            loginUser.setPassword(mPassword.getText().toString());
            mPresenter.login(loginUser);
        } else {
            showValidError();
        }
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new LoginPresenter(this,
                App.getInjectClass().getUserSource(),
                App.getInjectClass().getUserApi(),
                App.getInjectClass().getSplashSource());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void showSelectCommunity() {
        mProgressDialog.dismiss();
        Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LocateCommunityActivity.class));
        finish();
    }

    @Override
    public void showValidError() {
        Toast.makeText(this, "请填入用户名和密码", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLogining() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(getString(R.string.logining));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showAuthFailed() {
        mProgressDialog.dismiss();
        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
    }
}
