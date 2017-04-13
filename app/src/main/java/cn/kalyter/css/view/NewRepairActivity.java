package cn.kalyter.css.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.leon.lib.settingview.LSettingItem;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.css.R;
import cn.kalyter.css.util.BaseActivity;
import cn.kalyter.css.util.Config;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public class NewRepairActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.radio_home)
    RadioButton mRadioHome;
    @BindView(R.id.radio_gas)
    RadioButton mRadioGas;
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

    private Date mExpectDateTime;
    private Calendar mCalendar = Calendar.getInstance();

    @OnClick(R.id.expect_time_container)
    void chooseExpectTime() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this, this, year, month, day)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle.setText(R.string.repair);
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
                finish();
                return true;
            }
        });
    }

    @Override
    protected void setupPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_repair;
    }

    public void showExpectTime(Date date) {
        mExpectTime.setText(Config.yyyyMMddHHmmss.format(date));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mExpectDateTime = mCalendar.getTime();
        new TimePickerDialog(this, this,
                mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE), true)
                .show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);
        mExpectDateTime = mCalendar.getTime();
        showExpectTime(mExpectDateTime);
    }
}
