package cn.kalyter.css.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.lib.settingview.LSettingItem;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.AboutContract;
import cn.kalyter.css.util.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class AboutActivity extends BaseActivity implements AboutContract.View {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.introduction)
    LSettingItem mIntroduction;
    @BindView(R.id.cooperation)
    LSettingItem mCooperation;
    @BindView(R.id.check_update)
    LSettingItem mCheckUpdate;
    @BindView(R.id.feedback)
    LSettingItem mFeedback;
    @BindView(R.id.help)
    LSettingItem mHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle.setText(R.string.about);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIntroduction.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getApplicationContext(), IntroductionActivity.class));
            }
        });

        mCooperation.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getApplicationContext(), CooperationActivity.class));
            }
        });

        mCheckUpdate.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                Toast.makeText(getApplicationContext(), R.string.checking_update, Toast.LENGTH_SHORT).show();
                Observable.just(0)
                        .delay(1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                Toast.makeText(AboutActivity.this, R.string.already_newest, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        mFeedback.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getApplicationContext(), FeedbackActivity.class));
            }
        });

        mHelp.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getApplicationContext(), HelpActivity.class));
            }
        });
    }

    @Override
    protected void setupPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }
}
