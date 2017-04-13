package cn.kalyter.css.view;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.widget.FrameLayout;

import butterknife.BindView;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.MainContract;
import cn.kalyter.css.presenter.MainPresenter;
import cn.kalyter.css.util.BaseActivity;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public class MainActivity extends BaseActivity implements MainContract.View {
    @BindView(R.id.frame_content)
    FrameLayout mFrameContent;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigation;

    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new MainPresenter(this, getSupportFragmentManager(), this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setBottomItemSelectListener(BottomNavigationView.OnNavigationItemSelectedListener listener) {
        mBottomNavigation.setOnNavigationItemSelectedListener(listener);
    }

    @Override
    public void showDefaultSection() {
        mPresenter.toggleFragment(R.id.board);
    }

    @Override
    public void showBadge(int index, int count) {

    }
}
