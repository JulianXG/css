package cn.kalyter.css.presenter;

import java.util.Date;

import cn.kalyter.css.contract.NewRepairContract;
import cn.kalyter.css.data.source.RepairApi;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Community;
import cn.kalyter.css.model.Repair;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import cn.kalyter.css.util.Config;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-5-5 0005.
 */

public class NewRepairPresenter implements NewRepairContract.Presenter {
    private RepairApi mRepairApi;
    private NewRepairContract.View mView;
    private UserSource mUserSource;
    private User mUser;
    private Community mCommunity;

    public NewRepairPresenter(RepairApi repairApi,
                              NewRepairContract.View view,
                              UserSource userSource) {
        mRepairApi = repairApi;
        mView = view;
        mUserSource = userSource;
    }

    @Override
    public void start() {
        mUser = mUserSource.getUser();
        mCommunity = mUserSource.getCommunity();
        mView.clearUIStatusAndData();
    }

    @Override
    public void post(Repair repair) {
        mView.showPosting();
        repair.setUserId(mUser.getId());
        repair.setCommunityId(mUser.getCommunityId());
        repair.setHouseId(mUser.getHouseId());
        repair.setReportTime(new Date());
        mRepairApi.postRepair(repair)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        mView.closePosting();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.closePosting();
                        mView.showPostFail();
                    }

                    @Override
                    public void onNext(Response response) {
                        if (response.getCode() == Config.RESPONSE_SUCCESS_CODE) {
                            mView.showPostSuccess();
                            mView.clearUIStatusAndData();
                        } else {
                            mView.showPostFail();
                        }
                    }
                });
    }
}
