package cn.kalyter.css.view;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.LocateCommunityContract;
import cn.kalyter.css.util.BaseActivity;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class LocateCommunityActivity extends BaseActivity implements LocateCommunityContract.View {
    @BindView(R.id.city)
    TextView mCity;
    @BindView(R.id.search)
    TextView mSearch;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.cities)
    RecyclerView mCities;

    private LocateCommunityContract.Presenter mPresenter;

    @Override
    protected void setupPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_locate_community;
    }

}
