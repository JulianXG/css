package cn.kalyter.css.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import cn.kalyter.css.R;
import cn.kalyter.css.util.BaseActivity;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public class NewNoticeActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.notice_content)
    EditText mNoticeContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                } else {
                    Toast.makeText(NewNoticeActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return true;
            }
        });
    }

    @Override
    protected void setupPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_notice;
    }
}
