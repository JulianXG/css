package cn.kalyter.css.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.PaymentPropertyContract;
import cn.kalyter.css.model.Community;
import cn.kalyter.css.presenter.PaymentPropertyPresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseFragment;
import cn.kalyter.css.util.Config;

/**
 * Created by Kalyter on 2017-5-4 0004.
 */

public class PaymentPropertyFragment extends BaseFragment implements
        PaymentPropertyContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.community_name)
    TextView mCommunityName;
    @BindView(R.id.property_fee_post_status)
    TextView mPropertyFeePostStatus;
    @BindView(R.id.publish_property_fee)
    Button mPublishPropertyFee;

    private PaymentPropertyContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;

    @OnClick(R.id.publish_property_fee)
    void publishFee() {
        Toast.makeText(getContext(), "请选择物业费缴费截止时间", Toast.LENGTH_SHORT).show();
        new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mPresenter.publishToyearFee(date);
            }
        }).build().show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTitle.setText(R.string.community_payment_center);
        mProgressDialog = new ProgressDialog(getContext());
        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new PaymentPropertyPresenter(App.getInjectClass().getPaymentApi(),
                this, App.getInjectClass().getUserSource());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_property_payment;
    }

    @Override
    public void showPublishStatus(int status) {
        if (status == Config.PROPERTY_FEE_REQUIRED) {
            mPublishPropertyFee.setEnabled(true);
            mPropertyFeePostStatus.setText("未发布");
        } else if (status == Config.PROPERTY_FEE_FINISHED) {
            mPublishPropertyFee.setEnabled(false);
            mPropertyFeePostStatus.setText("已发布");
        }
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
    public void showPublishSuccess() {
        Toast.makeText(getContext(), "发布缴费信息成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPublishFail() {
        Toast.makeText(getContext(), "发布缴费信息失败，请尝试重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommunity(Community community) {
        mCommunityName.setText(community.getName());
    }
}
