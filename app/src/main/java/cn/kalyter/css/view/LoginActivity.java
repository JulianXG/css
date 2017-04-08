package cn.kalyter.css.view;

import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.LoginContract;
import cn.kalyter.css.presenter.LoginPresenter;
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
    private LoginContract.Presenter mPresenter;

    @OnClick(R.id.login)
    void login() {
        showSelectCommunity();
        finish();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void showSelectCommunity() {
        startActivity(new Intent(this, LocateCommunityActivity.class));
    }
}
