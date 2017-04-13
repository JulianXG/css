package cn.kalyter.css.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.BindView;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.LocateCommunityContract;
import cn.kalyter.css.presenter.LocateCommunityPresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseActivity;
import cn.kalyter.css.util.LocateRecyclerAdapter;

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
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mProgressDialog = new ProgressDialog(this);
        // TODO: 2017-4-9 0009 加入recyclerview分割线
        mCities.setLayoutManager(manager);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseCity();
            }
        });
        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new LocateCommunityPresenter(this,
                App.getInjectClass().getLocateSource(),
                this,
                App.getInjectClass().getUserSource(),
                App.getInjectClass().getSplashSource());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_locate_community;
    }

    @Override
    public void showConfirmIdentify() {
        Toast.makeText(this, "小区选择成功！", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, IdentityTypeActivity.class));
        finish();
    }

    @Override
    public void showChooseCity() {

    }

    @Override
    public void showMain() {

    }

    @Override
    public void showLocating() {
        mProgressDialog.setMessage(getString(R.string.locating));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void showLocateSuccess() {
        Toast.makeText(this, "定位成功", Toast.LENGTH_SHORT).show();
        mProgressDialog.dismiss();
    }

    @Override
    public void showLocateFail() {
        Toast.makeText(this, "定位失败", Toast.LENGTH_SHORT).show();
        mProgressDialog.dismiss();
    }

    @Override
    public void setAdapter(LocateRecyclerAdapter adapter) {
        mCities.setAdapter(adapter);
    }
}
