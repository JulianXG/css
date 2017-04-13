package cn.kalyter.css.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.IdentityTypeContract;
import cn.kalyter.css.presenter.IdentityTypePresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseActivity;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public class IdentityTypeActivity extends BaseActivity implements IdentityTypeContract.View {
    @BindView(R.id.list)
    ListView mList;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private IdentityTypeContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LocateCommunityActivity.class));
                finish();
            }
        });
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mPresenter.setIdentity(i);
            }
        });
        mToolbar.inflateMenu(R.menu.identity_type);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showMain();
                return false;
            }
        });
        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new IdentityTypePresenter(
                App.getInjectClass().getIdentitySource(), this, this,
                App.getInjectClass().getSplashSource());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_identity_type;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        mList.setAdapter(adapter);
    }

    @Override
    public void showMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showIdentitySuccess() {
        Toast.makeText(this, "身份认证成功！", Toast.LENGTH_SHORT).show();
    }
}
