package cn.kalyter.css.view;

import cn.kalyter.css.R;
import cn.kalyter.css.contract.SearchCommunityContract;
import cn.kalyter.css.util.BaseActivity;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class SearchCommunityActivity extends BaseActivity implements SearchCommunityContract.View {
    private SearchCommunityContract.Presenter mPresenter;

    @Override
    protected void setupPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_community;
    }
}
