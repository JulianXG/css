package cn.kalyter.css.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.NewNoticeContract;
import cn.kalyter.css.model.Message;
import cn.kalyter.css.presenter.NewNoticePresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseActivity;
import cn.kalyter.css.util.Config;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public class NewNoticeActivity extends BaseActivity implements NewNoticeContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.notice_content)
    EditText mNoticeContent;
    @BindView(R.id.is_top)
    Switch mIsTop;
    @BindView(R.id.property_container)
    LinearLayout mPropertyContainer;
    @BindView(R.id.top_start_time)
    Button mTopStartTime;
    @BindView(R.id.top_end_time)
    Button mTopEndTime;
    @BindView(R.id.timeRange)
    TextView mTimeRange;

    @OnCheckedChanged(R.id.is_top)
    void toggleIsTop(boolean isChecked) {
        if (isChecked) {
            mTopStartTime.setVisibility(View.VISIBLE);
            mTopEndTime.setVisibility(View.VISIBLE);
            mMessage.setIsTop(true);
        } else {
            mTopStartTime.setVisibility(View.INVISIBLE);
            mTopEndTime.setVisibility(View.INVISIBLE);
            mMessage.setIsTop(false);
        }
    }

    @OnClick(R.id.top_start_time)
    void selectTopStartTime() {
        TimePickerView timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mMessage.setTopStartTime(date);
                showTopTimeRange();
            }
        }).build();
        if (mMessage.getTopStartTime() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mMessage.getTopStartTime());
            timePickerView.setDate(calendar);
        }
        timePickerView.show();
    }

    @OnClick(R.id.top_end_time)
    void selectTopEndTime() {
        TimePickerView timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mMessage.setTopEndTime(date);
                showTopTimeRange();
            }
        }).build();
        if (mMessage.getTopEndTime() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mMessage.getTopEndTime());
            timePickerView.setDate(calendar);
        }
        timePickerView.show();
    }

    private NewNoticeContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    private Message mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessage = new Message();

        mTitle.setText(R.string.talk_a_talk);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mToolbar.inflateMenu(R.menu.menu_send);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (TextUtils.isEmpty(mNoticeContent.getText())) {
                    Toast.makeText(NewNoticeActivity.this, R.string.notice_content_empty, Toast.LENGTH_SHORT).show();
                } else if (mMessage.getIsTop() &&
                        (mMessage.getTopStartTime() == null ||
                                mMessage.getTopEndTime() == null)) {
                    Toast.makeText(NewNoticeActivity.this, "请选择指定时间段", Toast.LENGTH_SHORT).show();
                } else {
                    mMessage.setContent(mNoticeContent.getText().toString());
                    mPresenter.postMessage(mMessage);
                }
                return true;
            }
        });

        mProgressDialog = new ProgressDialog(this);

        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new NewNoticePresenter(this,
                App.getInjectClass().getMessageApi(),
                App.getInjectClass().getUserSource());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_notice;
    }

    @Override
    public void showOwner() {
        mPropertyContainer.setVisibility(View.GONE);
    }

    @Override
    public void showProperty() {
        mPropertyContainer.setVisibility(View.VISIBLE);
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
        Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showPostFail() {
        Toast.makeText(this, "发送失败，请检查网络，并尝试重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTopTimeRange() {
        String topStartTime = "";
        String topEndTime = "";
        if (mMessage.getTopStartTime() != null) {
            topStartTime = Config.yyyyMMddHHmmss.format(mMessage.getTopStartTime());
        }
        if (mMessage.getTopEndTime() != null) {
            topEndTime = Config.yyyyMMddHHmmss.format(mMessage.getTopEndTime());
        }
        mTimeRange.setText(String.format("%s\t-\t%s", topStartTime, topEndTime));
    }
}
