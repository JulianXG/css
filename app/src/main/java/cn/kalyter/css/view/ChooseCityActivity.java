package cn.kalyter.css.view;

import cn.kalyter.css.R;
import cn.kalyter.css.contract.ChooseCityContract;
import cn.kalyter.css.util.BaseActivity;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class ChooseCityActivity extends BaseActivity implements ChooseCityContract.View {
    private ChooseCityContract.Presenter mPresenter;

    @Override
    protected void setupPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_city;
    }
}
