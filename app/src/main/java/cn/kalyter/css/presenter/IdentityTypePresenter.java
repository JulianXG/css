package cn.kalyter.css.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.kalyter.css.contract.IdentityTypeContract;
import cn.kalyter.css.data.source.CommunityApi;
import cn.kalyter.css.data.source.HouseApi;
import cn.kalyter.css.data.source.IdentitySource;
import cn.kalyter.css.data.source.SplashSource;
import cn.kalyter.css.model.CommunityAllHouse;
import cn.kalyter.css.model.House;
import cn.kalyter.css.model.Identity;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import cn.kalyter.css.util.Config;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public class IdentityTypePresenter implements IdentityTypeContract.Presenter {
    private static final String TAG = "IdentityTypePresenter";
    private IdentityTypeContract.View mView;
    private Context mContext;
    private ArrayAdapter<String> mIdentityArrayAdapter;
    private IdentitySource mIdentitySource;
    private SplashSource mSplashSource;
    private CommunityApi mCommunityApi;
    private HouseApi mHouseApi;

    public IdentityTypePresenter(IdentitySource identitySource,
                                 IdentityTypeContract.View view,
                                 Context context,
                                 SplashSource splashSource,
                                 CommunityApi communityApi,
                                 HouseApi houseApi) {
        mContext = context;
        mIdentitySource = identitySource;
        mView = view;
        mIdentityArrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_expandable_list_item_1);
        mSplashSource = splashSource;
        mCommunityApi = communityApi;
        mHouseApi = houseApi;
    }

    @Override
    public void start() {
        loadIdentities();
        mView.setAdapter(mIdentityArrayAdapter);
    }

    @Override
    public void loadIdentities() {
        mIdentitySource.getIdentities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Identity>>() {
                    @Override
                    public void call(List<Identity> identities) {
                        List<String> data = new ArrayList<>();
                        for (Identity identity : identities) {
                            data.add(identity.getName());
                        }
                        mIdentityArrayAdapter.addAll(data);
                        mIdentityArrayAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void setIdentity(int communityId, int identityType) {
        if (identityType == Config.ROLE_PROPERTY) {
            mView.showConfirmCommunityCode();
        } else if (identityType == Config.ROLE_OWNER){
            loadCommunityAllHouses(communityId);
        }
    }

    @Override
    public void validateCommunityCode(final int communityId, String code) {
        mCommunityApi.validateCode(communityId, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(Response response) {
                        if (response.getCode() == Config.RESPONSE_SUCCESS_CODE) {
                            mView.showValidateCodeSuccess();
                            User user = new User();
                            user.setRoleId(Config.ROLE_PROPERTY);
                            user.setCommunityId(communityId);
                            mView.showRegister(user);
                        } else {
                            mView.showValidateCodeFail();
                        }
                    }
                });
    }

    @Override
    public void loadCommunityAllHouses(int communityId) {
        mView.showLoading();
        mHouseApi.getAllHousesById(communityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<CommunityAllHouse>>() {
                    @Override
                    public void onCompleted() {
                        mView.closeLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.closeLoading();
                        mView.requestError();
                    }

                    @Override
                    public void onNext(Response<CommunityAllHouse> communityAllHouseResponse) {
                        mView.setHouses(communityAllHouseResponse.getData());
                        mView.showChooseHouse();
                    }
                });
    }

    @Override
    public void selectHouse(int communityId, CommunityAllHouse communityAllHouse, int buildingPosition, int unitPosition, int roomPosition) {
        String buildingId = communityAllHouse.getBuildings().get(buildingPosition);
        String unitId = communityAllHouse.getUnits().get(buildingPosition).get(unitPosition);
        String roomId = communityAllHouse.getRooms().get(buildingPosition).get(unitPosition).get(roomPosition);
        mHouseApi.getHouse(communityId, buildingId, unitId, roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<House>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(Response<House> houseResponse) {
                        House house = houseResponse.getData();
                        User user = new User();
                        user.setRoleId(Config.ROLE_OWNER);
                        user.setHouseId(house.getId());
                        user.setCommunityId(house.getCommunityId());
                        mView.showRegister(user);
                    }
                });
    }
}
