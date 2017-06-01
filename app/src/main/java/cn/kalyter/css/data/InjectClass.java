package cn.kalyter.css.data;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import cn.kalyter.css.data.repository.IdentityRepository;
import cn.kalyter.css.data.repository.LocateRepository;
import cn.kalyter.css.data.repository.MessageRepository;
import cn.kalyter.css.data.repository.SplashRepository;
import cn.kalyter.css.data.repository.UserRepository;
import cn.kalyter.css.data.source.CommunityApi;
import cn.kalyter.css.data.source.FeedbackApi;
import cn.kalyter.css.data.source.HouseApi;
import cn.kalyter.css.data.source.IdentitySource;
import cn.kalyter.css.data.source.LocateSource;
import cn.kalyter.css.data.source.MessageApi;
import cn.kalyter.css.data.source.PaymentApi;
import cn.kalyter.css.data.source.RepairApi;
import cn.kalyter.css.data.source.SplashSource;
import cn.kalyter.css.data.source.UserApi;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.util.Config;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public final class InjectClass {
    private static final String TAG = "InjectClass";

    private Context mContext;
    private SplashSource mSplashSource;
    private LocateSource mLocateSource;
    private UserSource mUserSource;
    private UserApi mUserApi;
    private CommunityApi mCommunityApi;
    private HouseApi mHouseApi;
    private IdentitySource mIdentitySource;
    private MessageApi mMessageApi;
    private PaymentApi mPaymentApi;
    private RepairApi mRepairApi;
    private FeedbackApi mFeedbackApi;
    private Retrofit mRetrofit;

    public InjectClass(Context context) {
        mContext = context;
        mSplashSource = new SplashRepository(mContext);
        mLocateSource = new LocateRepository(mContext);
        mUserSource = new UserRepository(mContext);
        mIdentitySource = new IdentityRepository(mContext);
        mMessageApi = new MessageRepository(mContext);
        mRetrofit = initRetrofit();
    }

    public RepairApi getRepairApi() {
        return mRetrofit.create(RepairApi.class);
    }

    public PaymentApi getPaymentApi() {
        return mRetrofit.create(PaymentApi.class);
    }

    public MessageApi getMessageApi() {
        return mRetrofit.create(MessageApi.class);
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
        return mRetrofit.create(UserApi.class);
    }

    public FeedbackApi getFeedbackApi() {
        return mRetrofit.create(FeedbackApi.class);
    }

    public CommunityApi getCommunityApi() {
        return mRetrofit.create(CommunityApi.class);
    }

    public HouseApi getHouseApi() {
        return mRetrofit.create(HouseApi.class);
    }

    private Retrofit initRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i(TAG, message);
                    }
                });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return new Retrofit.Builder()
                .baseUrl(Config.API_ROOT_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
