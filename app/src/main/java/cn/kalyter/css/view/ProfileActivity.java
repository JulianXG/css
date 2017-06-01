package cn.kalyter.css.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.ProfileContract;
import cn.kalyter.css.model.User;
import cn.kalyter.css.presenter.ProfilePresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseActivity;
import cn.kalyter.css.util.Config;
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
    @BindView(R.id.community_address)
    TextView mCommunityAddress;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.address_container)
    LinearLayout mAddressContainer;
    @BindView(R.id.bind_tel)
    TextView mBindTel;
    @BindView(R.id.bind_tel_container)
    LinearLayout mBindTelContainer;

    @OnClick(R.id.nickname_container)
    void changeNickname() {
        new MaterialDialog.Builder(this)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .title(R.string.change_profile)
                .input("请输入新的昵称", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        String nickname = input.toString();
                        if (!nickname.equals("")) {
                            User user = new User();
                            user.setNickname(nickname);
                            mPresenter.changeProfile(user);
                        }
                    }
                }).build().show();
    }

    @OnClick(R.id.birthday_container)
    void changeBirthday() {
        TimePickerView timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                User user = new User();
                user.setBirthday(Config.yyyyMMdd.format(date));
                mPresenter.changeProfile(user);
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY).build();
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Config.yyyyMMdd.parse(mUser.getBirthday()));
            timePickerView.setDate(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timePickerView.show();
    }

    @OnClick(R.id.gender_container)
    void changeGender() {
        int defaultIndex = 0;
        if (mUser.getGender().equals("女")) {
            defaultIndex = 1;
        }
        new MaterialDialog.Builder(this)
                .title(R.string.change_profile)
                .items(R.array.genders)
                .itemsCallbackSingleChoice(defaultIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        User user = new User();
                        user.setGender(text.toString());
                        mPresenter.changeProfile(user);
                        return false;
                    }
                }).build().show();
    }

    @OnClick(R.id.bind_tel_container)
    void changeTel() {
        new MaterialDialog.Builder(this)
                .title(R.string.change_profile)
                .inputType(InputType.TYPE_CLASS_PHONE)
                .input("请输入正确的手机号", mUser.getTel(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        Matcher matcher = Config.TEL_PATTERN.matcher(input);
                        if (matcher.matches()) {
                            User user = new User();
                            user.setTel(input.toString());
                            mPresenter.changeProfile(user);
                        } else {
                            Toast.makeText(ProfileActivity.this, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).build().show();
    }

    private ProfileContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    private User mUser;


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

        mProgressDialog = new ProgressDialog(this);

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
        mUser = user;
        Glide.with(this)
                .load(user.getAvatar())
                .centerCrop()
                .transform(new GlideCircleTransform(this))
                .into(mAvatarImage);
        Glide.with(this)
                .load(user.getBackground())
                .placeholder(R.drawable.bg_me)
                .centerCrop()
                .into(mBackgroundImage);
        mNickname.setText(user.getNickname());
        mBirthday.setText(user.getBirthday());
        mGender.setText(user.getGender());
        mCommunityAddress.setText(user.getCommunity().getAddress());
        if (user.getHouse() != null) {
            mAddressContainer.setVisibility(View.VISIBLE);
            mAddress.setText(String.format("%s %s %s",
                    user.getHouse().getBuildingId(),
                    user.getHouse().getUnitId(),
                    user.getHouse().getRoomId()));
        } else {
            mAddressContainer.setVisibility(View.GONE);
        }
        mBindTel.setText(user.getTel());
    }

    @Override
    public void showLoading() {
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void showChangeSuccess() {
        Toast.makeText(this, "修改个人信息成功！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showActionFail() {
        Toast.makeText(this, "更改用户信息失败请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeLoading() {
        mProgressDialog.dismiss();
    }
}
