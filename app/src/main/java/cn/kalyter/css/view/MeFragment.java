package cn.kalyter.css.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leon.lib.settingview.LSettingItem;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.MeContract;
import cn.kalyter.css.model.User;
import cn.kalyter.css.presenter.MePresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseFragment;
import cn.kalyter.css.util.GlideCircleTransform;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class MeFragment extends BaseFragment implements MeContract.View {
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.membership_point)
    TextView mMembershipPoint;
    @BindView(R.id.my_message)
    LSettingItem mMyMessage;
    @BindView(R.id.payment_record)
    LSettingItem mPaymentRecord;
    @BindView(R.id.repair_record)
    LSettingItem mRepairRecord;
    @BindView(R.id.profile)
    LSettingItem mProfile;
    @BindView(R.id.about)
    LSettingItem mAbout;
    @BindView(R.id.logout)
    Button mLogout;
    @BindView(R.id.profile_container)
    LinearLayout mProfileContainer;

    private MeContract.Presenter mPresenter;

    @OnClick(R.id.logout)
    void logout() {
        new AlertDialog.Builder(getContext())
                .setTitle("确定退出登录？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.logout();
                    }
                }).setNegativeButton("取消", null).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMyMessage.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getContext(), MyMessageActivity.class));
            }
        });

        mPaymentRecord.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getContext(), PaymentRecordActivity.class));
            }
        });

        mRepairRecord.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getContext(), RepairRecordActivity.class));
            }
        });

        mProfile.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });

        mAbout.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getContext(), AboutActivity.class));
            }
        });
        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new MePresenter(this,
                App.getInjectClass().getSplashSource(),
                App.getInjectClass().getUserSource());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void showLogin() {
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
        Toast.makeText(getContext(), "退出登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUser(User user) {
        Glide.with(this)
                .load(user.getAvatar())
                .centerCrop()
                .transform(new GlideCircleTransform(getContext()))
                .into(mAvatar);
        mUsername.setText(user.getNickname());
    }
}
