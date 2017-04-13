package cn.kalyter.css.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Arrays;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGAGuideLinkageLayout;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.WelcomeContract;
import cn.kalyter.css.presenter.WelcomePresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseActivity;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class WelcomeActivity extends BaseActivity implements WelcomeContract.View{

    @BindView(R.id.tutorial_background)
    BGABanner mTutorialBackground;
    @BindView(R.id.tutorial_foreground)
    BGABanner mTutorialForeground;
    @BindView(R.id.container)
    BGAGuideLinkageLayout mContainer;
    @BindView(R.id.skip)
    TextView mSkip;
    @BindView(R.id.enter)
    Button mEnter;
    private WelcomeContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setupBanner();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new WelcomePresenter(this, App.getInjectClass().getSplashSource(), this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void setupBanner() {
        mTutorialBackground.setAdapter(new BGABanner.Adapter<ImageView, Integer>() {

            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, Integer model, int position) {
                Glide.with(WelcomeActivity.this)
                        .load(model)
                        .centerCrop()
                        .into(itemView);
            }
        });
        mTutorialBackground.setData(Arrays.asList(
                R.drawable.bg_tutorial_1,
                R.drawable.bg_tutorial_2,
                R.drawable.bg_tutorial_3),
                Arrays.asList("", "", ""));

        mTutorialForeground.setData(
                R.drawable.fore_bg_tutorial_1,
                R.drawable.fore_bg_tutorial_2,
                R.drawable.fore_bg_tutorial_3);
        mTutorialBackground.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == mTutorialBackground.getItemCount() - 1) {
                    mEnter.setVisibility(View.VISIBLE);
                    mSkip.setVisibility(View.GONE);
                } else {
                    mEnter.setVisibility(View.GONE);
                    mSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTutorialForeground.setEnterSkipViewIdAndDelegate(R.id.enter, R.id.skip,
                new BGABanner.GuideDelegate() {
                    @Override
                    public void onClickEnterOrSkip() {
                        mPresenter.loadNext();
                    }
                });
    }

    @Override
    public void showLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
