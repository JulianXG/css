package cn.kalyter.css.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.OptionsPickerView;

import butterknife.BindView;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.IdentityTypeContract;
import cn.kalyter.css.model.CommunityAllHouse;
import cn.kalyter.css.model.User;
import cn.kalyter.css.presenter.IdentityTypePresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseActivity;
import cn.kalyter.css.util.Config;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public class IdentityTypeActivity extends BaseActivity implements
        IdentityTypeContract.View, OptionsPickerView.OnOptionsSelectListener {
    private static final String TAG = "IdentityTypeActivity";
    @BindView(R.id.list)
    ListView mList;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private IdentityTypeContract.Presenter mPresenter;
    private int mCommunityId;
    private OptionsPickerView mOptionsPickerView;
    private CommunityAllHouse mCommunityAllHouse;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommunityId = getIntent().getIntExtra(Config.BUNDLE_COMMUNITY_ID, 0);
        mToolbar.setNavigationIcon(null);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mPresenter.setIdentity(mCommunityId, i + 1);
            }
        });

        mOptionsPickerView = new OptionsPickerView.Builder(this, this)
                .build();

        mProgressDialog = new ProgressDialog(this);

        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new IdentityTypePresenter(
                App.getInjectClass().getIdentitySource(), this, this,
                App.getInjectClass().getSplashSource(),
                App.getInjectClass().getCommunityApi(),
                App.getInjectClass().getHouseApi());
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

    @Override
    public void showConfirmCommunityCode() {
        new MaterialDialog.Builder(this)
                .title("社区码")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("请输入对应的社区码", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        mPresenter.validateCommunityCode(mCommunityId, input.toString());
                    }
                }).show();
    }

    @Override
    public void showChooseHouse() {
        mOptionsPickerView.show();
    }

    @Override
    public void showRegister(User user) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(Config.BUNDLE_USER, JSON.toJSONString(user));
        startActivity(intent);
        finish();
    }

    @Override
    public void showValidateCodeSuccess() {
        Toast.makeText(this, "验证社区码成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showValidateCodeFail() {
        Toast.makeText(this, "验证社区码失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setHouses(CommunityAllHouse communityAllHouse) {
        mCommunityAllHouse = communityAllHouse;
        mOptionsPickerView.setPicker(communityAllHouse.getBuildings(),
                communityAllHouse.getUnits(), communityAllHouse.getRooms());
    }

    @Override
    public void showLoading() {
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void closeLoading() {
        mProgressDialog.dismiss();
    }

    @Override
    public void requestError() {
        Toast.makeText(this, R.string.network_request_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        mPresenter.selectHouse(mCommunityId, mCommunityAllHouse, options1, options2, options3);
    }
}
