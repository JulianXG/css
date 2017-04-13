package cn.kalyter.css.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.ProfileContract;
import cn.kalyter.css.model.User;
import cn.kalyter.css.presenter.ProfilePresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseActivity;
import cn.kalyter.css.util.GlideCircleTransform;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class ProfileActivity extends BaseActivity implements ProfileContract.View {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.avatar_image)
    ImageView mAvatarImage;
    @BindView(R.id.avatar_container)
    LinearLayout mAvatarContainer;
    @BindView(R.id.background_image)
    ImageView mBackgroundImage;
    @BindView(R.id.background_container)
    LinearLayout mBackgroundContainer;
    @BindView(R.id.nickname)
    TextView mNickname;
    @BindView(R.id.nickname_container)
    LinearLayout mNicknameContainer;
    @BindView(R.id.birthday)
    TextView mBirthday;
    @BindView(R.id.birthday_container)
    LinearLayout mBirthdayContainer;
    @BindView(R.id.gender)
    TextView mGender;
    @BindView(R.id.gender_container)
    LinearLayout mGenderContainer;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.address_container)
    LinearLayout mAddressContainer;
    @BindView(R.id.bind_tel)
    TextView mBindTel;
    @BindView(R.id.bind_tel_container)
    LinearLayout mBindTelContainer;

    private ProfileContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle.setText(R.string.profile);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new ProfilePresenter(this,
                App.getInjectClass().getUserSource(),
                App.getInjectClass().getUserApi());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public void showUser(User user) {
        Glide.with(this)
                .load(user.getAvatar())
                .centerCrop()
                .transform(new GlideCircleTransform(this))
                .into(mAvatarImage);
        Glide.with(this)
                .load(R.drawable.bg_me)
                .centerCrop()
                .into(mBackgroundImage);
        mNickname.setText(user.getNickname());
        mBirthday.setText(user.getBirthday());
        mGender.setText(user.getGender());
        mAddress.setText(user.getAddress());
        mBindTel.setText(user.getBindTel());
    }
}
