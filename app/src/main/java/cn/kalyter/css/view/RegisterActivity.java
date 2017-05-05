package cn.kalyter.css.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.RegisterContract;
import cn.kalyter.css.model.User;
import cn.kalyter.css.presenter.RegisterPresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseActivity;
import cn.kalyter.css.util.Config;

/**
 * Created by Kalyter on 2017-5-3 0003.
 */

public class RegisterActivity extends BaseActivity implements RegisterContract.View {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.username_container)
    TextInputLayout mUsernameContainer;
    @BindView(R.id.nickname)
    EditText mNickname;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.male)
    RadioButton mMale;
    @BindView(R.id.female)
    RadioButton mFemale;
    @BindView(R.id.register)
    AppCompatButton mRegister;
    @BindView(R.id.gender)
    RadioGroup mGender;

    @OnClick(R.id.register)
    void register() {
        if (TextUtils.isEmpty(mUsername.getText()) ||
                TextUtils.isEmpty(mNickname.getText()) ||
                TextUtils.isEmpty(mPassword.getText())) {
            showValidateError();
        } else {
            mUser.setUsername(mUsername.getText().toString());
            mUser.setNickname(mNickname.getText().toString());
            mUser.setPassword(mPassword.getText().toString());
            if (mMale.isChecked()) {
                mUser.setGender("男");
            } else if (mFemale.isChecked()) {
                mUser.setGender("女");
            }
            mPresenter.register(mUser);
        }
    }

    private RegisterContract.Presenter mPresenter;
    private User mUser;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userJSON = getIntent().getStringExtra(Config.BUNDLE_USER);
        mUser = JSON.parseObject(userJSON, User.class);
        mTitle.setText(R.string.register);
        mProgressDialog = new ProgressDialog(this);
        mMale.setChecked(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogin();
            }
        });
        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new RegisterPresenter(this, App.getInjectClass().getUserApi());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void showValidateError() {
        Toast.makeText(this, "表单填写不完整，请检查", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRegistering() {
        mProgressDialog.setMessage("正在注册……");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void closeRegistering() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showRegisterSuccess() {
        Toast.makeText(this, "注册成功，自动返回登录界面", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void showRegisterFail() {
        Toast.makeText(this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUsernameConflict() {
        Toast.makeText(this, "注册用户名重复，请更换一个新的用户名进行注册", Toast.LENGTH_SHORT).show();
    }
}
