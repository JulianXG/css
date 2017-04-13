package cn.kalyter.css.presenter;

import android.content.Context;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.kalyter.css.contract.LocateCommunityContract;
import cn.kalyter.css.data.source.LocateSource;
import cn.kalyter.css.data.source.SplashSource;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Community;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.util.LocateRecyclerAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class LocateCommunityPresenter implements
        LocateCommunityContract.Presenter, LocateRecyclerAdapter.OnClickCommunityListener {
    private Context mContext;
    private LocateCommunityContract.View mView;
    private LocateSource mLocateSource;
    private LocateRecyclerAdapter mLocateRecyclerAdapter;
    private UserSource mUserSource;
    private SplashSource mSplashSource;

    public LocateCommunityPresenter(Context context,
                                    LocateSource locateSource,
                                    LocateCommunityContract.View view,
                                    UserSource userSource,
                                    SplashSource splashSource) {
        mContext = context;
        mLocateSource = locateSource;
        mView = view;
        mUserSource = userSource;
        mSplashSource = splashSource;
        mLocateRecyclerAdapter = new LocateRecyclerAdapter(mContext);
    }

    @Override
    public void start() {
        mView.setAdapter(mLocateRecyclerAdapter);
        locateCommunities();
        mLocateRecyclerAdapter.setOnClickCommunityListener(this);
    }

    @Override
    public void locateCommunities() {
        mView.showLocating();
        mLocateSource.getLocateCommunities()
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<Community>>>() {
                    @Override
                    public void onCompleted() {
                        mView.showLocateSuccess();
                        mSplashSource.setIsLocate(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLocateFail();
                    }

                    @Override
                    public void onNext(Response<List<Community>> listResponse) {
                        mLocateRecyclerAdapter.setData(listResponse.getData());
                    }
                });
    }

    @Override
    public void onClickCommunity(Community community) {
        mUserSource.setCommunity(community);
        mView.showConfirmIdentify();
    }
}
