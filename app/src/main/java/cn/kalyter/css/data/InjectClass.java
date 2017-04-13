package cn.kalyter.css.data;

import android.content.Context;

import cn.kalyter.css.data.repository.IdentityRepository;
import cn.kalyter.css.data.repository.LocateRepository;
import cn.kalyter.css.data.repository.MessageRepository;
import cn.kalyter.css.data.repository.PaymentRepository;
import cn.kalyter.css.data.repository.RepairRepository;
import cn.kalyter.css.data.repository.SplashRepository;
import cn.kalyter.css.data.repository.UserApiRepository;
import cn.kalyter.css.data.repository.UserRepository;
import cn.kalyter.css.data.source.IdentitySource;
import cn.kalyter.css.data.source.LocateSource;
import cn.kalyter.css.data.source.MessageSource;
import cn.kalyter.css.data.source.PaymentSource;
import cn.kalyter.css.data.source.RepairSource;
import cn.kalyter.css.data.source.SplashSource;
import cn.kalyter.css.data.source.UserApi;
import cn.kalyter.css.data.source.UserSource;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public final class InjectClass {
    private Context mContext;
    private SplashSource mSplashSource;
    private LocateSource mLocateSource;
    private UserSource mUserSource;
    private UserApi mUserApi;
    private IdentitySource mIdentitySource;
    private MessageSource mMessageSource;
    private PaymentSource mPaymentSource;
    private RepairSource mRepairSource;

    public RepairSource getRepairSource() {
        return mRepairSource;
    }

    public InjectClass(Context context) {
        mContext = context;
        mSplashSource = new SplashRepository(mContext);
        mLocateSource = new LocateRepository(mContext);
        mUserSource = new UserRepository(mContext);
        mUserApi = new UserApiRepository();
        mIdentitySource = new IdentityRepository(mContext);
        mMessageSource = new MessageRepository(mContext);
        mPaymentSource = new PaymentRepository(mContext);
        mRepairSource = new RepairRepository(mContext);

    }

    public PaymentSource getPaymentSource() {
        return mPaymentSource;
    }

    public MessageSource getMessageSource() {
        return mMessageSource;
    }

    public IdentitySource getIdentitySource() {
        return mIdentitySource;
    }

    public SplashSource getSplashSource() {
        return mSplashSource;
    }

    public LocateSource getLocateSource() {
        return mLocateSource;
    }

    public UserSource getUserSource() {
        return mUserSource;
    }

    public UserApi getUserApi() {
        return mUserApi;
    }
}
