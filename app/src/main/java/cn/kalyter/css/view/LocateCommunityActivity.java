package cn.kalyter.css.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.LocateCommunityContract;
import cn.kalyter.css.presenter.LocateCommunityPresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseActivity;
import cn.kalyter.css.util.Config;
import cn.kalyter.css.util.LocateRecyclerAdapter;
import cn.kalyter.css.util.Util;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class LocateCommunityActivity extends BaseActivity implements LocateCommunityContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title)
    TextView mTitle;
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
            public void onClick(View v) {
                mPresenter.locateCurrentPlace();
            }
        });

        mToolbar.inflateMenu(R.menu.search);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showSearch();
                return true;
            }
        });
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);

        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new LocateCommunityPresenter(getApplicationContext(),
                App.getInjectClass().getLocateSource(),
                this,
                App.getInjectClass().getUserSource(),
                App.getInjectClass().getSplashSource(),
                App.getInjectClass().getCommunityApi());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_locate_community;
    }

    @Override
    public void showConfirmIdentify(int communityId) {
        Toast.makeText(this, "小区选择成功！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, IdentityTypeActivity.class);
        intent.putExtra(Config.BUNDLE_COMMUNITY_ID, communityId);
        startActivity(intent);
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
        mProgressDialog.dismiss();
        mProgressDialog.setMessage(getString(R.string.locating));
        mProgressDialog.show();
    }

    @Override
    public void showLocatingCommunity() {
        mProgressDialog.dismiss();
        mProgressDialog.setMessage(getString(R.string.search_nearby_community));
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
    public void showTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setAdapter(LocateRecyclerAdapter adapter) {
        mCities.setAdapter(adapter);
    }

    @Override
    public void requestPermissions(String[] permissions) {
        Util.requestPermission(this, permissions);
    }

    @Override
    public void showLocateDenied() {
        mProgressDialog.dismiss();
        Toast.makeText(this, R.string.locate_denied, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNotCooperate() {
        Toast.makeText(this, R.string.not_cooperate, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSearch() {
        new MaterialDialog.Builder(this)
                .title("社区关键词搜索")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(getString(R.string.hint_keyword_search), null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        mPresenter.searchCommunity("南京", input.toString());
                    }
                }).show();
    }
}
