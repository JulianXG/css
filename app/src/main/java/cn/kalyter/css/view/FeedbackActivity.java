package cn.kalyter.css.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.css.R;
import cn.kalyter.css.contract.FeedbackContract;
import cn.kalyter.css.presenter.FeedbackPresenter;
import cn.kalyter.css.util.App;
import cn.kalyter.css.util.BaseActivity;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class FeedbackActivity extends BaseActivity implements FeedbackContract.View {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.feedback_content)
    EditText mFeedbackContent;
    @BindView(R.id.send_feedback)
    Button mSendFeedback;

    @OnClick(R.id.send_feedback)
    void sendFeedback() {
        if (TextUtils.isEmpty(mFeedbackContent.getText())) {
            Toast.makeText(this, R.string.feedback_require, Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.submitFeedback(mFeedbackContent.getText().toString());
        }
    }

    private FeedbackContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle.setText(R.string.feedback);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mProgressDialog = new ProgressDialog(this);

        mPresenter.start();
    }

    @Override
    protected void setupPresenter() {
        mPresenter = new FeedbackPresenter(this,
                App.getInjectClass().getUserSource(),
                App.getInjectClass().getFeedbackApi());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void showSubmitting() {
        mProgressDialog.setMessage(getString(R.string.posting));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void showSubmitSuccess() {
        Toast.makeText(this, R.string.send_feedback_success, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void closeSubmitting() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showSubmitFail() {
        Toast.makeText(this, "反馈意见上传失败，请尝试重新上传", Toast.LENGTH_SHORT).show();
    }
}
