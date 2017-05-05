package cn.kalyter.css.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.NewRepairContract;
import cn.kalyter.css.model.Repair;
import cn.kalyter.css.presenter.NewRepairPresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseFragment;
import cn.kalyter.css.util.Config;
import cn.kalyter.css.util.RepairTypeEnum;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public class NewRepairFragment extends BaseFragment implements NewRepairContract.View{
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.radio_home)
    RadioButton mRadioHome;
    @BindView(R.id.radio_gas)
    RadioButton mRadioGas;
    @BindView(R.id.radio_public)
    RadioButton mRadioPublic;
    @BindView(R.id.reporter)
    EditText mReporter;
    @BindView(R.id.reporter_tel)
    EditText mReporterTel;
    @BindView(R.id.expect_time)
    TextView mExpectTime;
    @BindView(R.id.description)
    EditText mDescription;
    @BindView(R.id.expect_time_container)
    LinearLayout mExpectTimeContainer;
    @BindView(R.id.repair_group)
    RadioGroup mRepairGroup;

    private NewRepairContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    private Repair mRepair;

    @OnClick(R.id.expect_time_container)
    void chooseExpectTime() {
        new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mRepair.setExpectHandleTime(date);
                showExpectTime(date);
            }
        }).build().show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitle.setText(R.string.repair);
        mToolbar.inflateMenu(R.menu.menu_send);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (TextUtils.isEmpty(mReporter.getText())) {
                    Toast.makeText(getContext(), "请填写报修人姓名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mReporterTel.getText())) {
                    Toast.makeText(getContext(), "请填写报修人手机号码", Toast.LENGTH_SHORT).show();
                } else if (mRepair.getExpectHandleTime() == null) {
                    Toast.makeText(getContext(), "请选择期望时间", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mDescription.getText())) {
                    Toast.makeText(getContext(), "请填写报修相关情况描述", Toast.LENGTH_SHORT).show();
                } else {
                    mRepair.setReporter(mReporter.getText().toString());
                    mRepair.setReporterTel(mReporterTel.getText().toString());
                    mRepair.setDescription(mDescription.getText().toString());
                    mPresenter.post(mRepair);
                }
                return true;
            }
        });


        mProgressDialog = new ProgressDialog(getContext());
        mPresenter.start();


        mRepairGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == mRadioHome.getId()) {
                    mRepair.setType(RepairTypeEnum.HOUSE.getType());
                } else if (checkedId == mRadioGas.getId()) {
                    mRepair.setType(RepairTypeEnum.WATER_OR_GAS.getType());
                } else if (checkedId == mRadioPublic.getId()) {
                    mRepair.setType(RepairTypeEnum.PUBLIC_FACILITIES.getType());
                }
            }
        });
        // 默认选中房屋维修
        mRadioHome.setChecked(true);
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new NewRepairPresenter(App.getInjectClass().getRepairApi(),
                this, App.getInjectClass().getUserSource());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_repair;
    }

    public void showExpectTime(Date date) {
        if (date == null) {
            mExpectTime.setText("");
        } else {
            mExpectTime.setText(Config.yyyyMMddHHmmss.format(date));
        }
    }

    @Override
    public void clearUIStatusAndData() {
        mRepair = new Repair();
        showExpectTime(mRepair.getExpectHandleTime());
        mReporter.setText("");
        mReporterTel.setText("");
        mDescription.setText("");
    }

    @Override
    public void showPosting() {
        mProgressDialog.setMessage(getString(R.string.posting));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void closePosting() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showPostSuccess() {
        Toast.makeText(getContext(), R.string.post_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPostFail() {
        Toast.makeText(getContext(), R.string.post_fail, Toast.LENGTH_SHORT).show();
    }
}
