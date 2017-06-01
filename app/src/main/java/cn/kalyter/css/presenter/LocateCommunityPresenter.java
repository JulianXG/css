package cn.kalyter.css.presenter;

import android.Manifest;
import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.List;

import cn.kalyter.css.contract.LocateCommunityContract;
import cn.kalyter.css.data.source.CommunityApi;
import cn.kalyter.css.data.source.LocateSource;
import cn.kalyter.css.data.source.SplashSource;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Community;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.util.Config;
import cn.kalyter.css.util.LocateRecyclerAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class LocateCommunityPresenter implements
        LocateCommunityContract.Presenter, LocateRecyclerAdapter.OnClickCommunityListener,
        OnGetPoiSearchResultListener, BDLocationListener{
    private static final String TAG = "LocateCommunityP";
    private Context mContext;
    private LocateCommunityContract.View mView;
    private LocateSource mLocateSource;
    private LocateRecyclerAdapter mLocateRecyclerAdapter;
    private UserSource mUserSource;
    private SplashSource mSplashSource;
    private PoiSearch mPoiSearch;
    private LocationClient mLocationClient;
    private String[] mLocatePermissions;
    private LatLng mCurrentLocation;
    private CommunityApi mCommunityApi;
    private String mCommunityKeyword = "小区";

    public LocateCommunityPresenter(Context context,
                                    LocateSource locateSource,
                                    LocateCommunityContract.View view,
                                    UserSource userSource,
                                    SplashSource splashSource,
                                    CommunityApi communityApi) {
        mContext = context;
        mLocateSource = locateSource;
        mView = view;
        mUserSource = userSource;
        mSplashSource = splashSource;
        mCommunityApi = communityApi;
        mLocateRecyclerAdapter = new LocateRecyclerAdapter(mContext);
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mLocationClient = new LocationClient(mContext);
        mLocatePermissions = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
    }

    @Override
    public void start() {
        mView.requestPermissions(mLocatePermissions);
        mView.setAdapter(mLocateRecyclerAdapter);
        mLocateRecyclerAdapter.setOnClickCommunityListener(this);
        locateCurrentPlace();
    }

    @Override
    public void locateCommunities() {
        mView.showLocatingCommunity();
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                .keyword(mCommunityKeyword)
                .location(mCurrentLocation)
                .radius(3000)
                .pageCapacity(100);
        mPoiSearch.searchNearby(nearbySearchOption);
    }

    @Override
    public void locateCurrentPlace() {
        mView.showLocating();
        mLocationClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setAddrType("all");
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setScanSpan(500);
        option.setIsNeedLocationDescribe(true);
        mLocationClient.setLocOption(option);
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
        mLocationClient.start();
    }

    @Override
    public void searchCommunity(String city, String keyword) {
        mView.showLocatingCommunity();
        PoiCitySearchOption option = new PoiCitySearchOption()
                .keyword(mCommunityKeyword + " " + keyword)
                .city(city)
                .pageCapacity(100);
        mPoiSearch.searchInCity(option);
    }

    @Override
    public void onClickCommunity(Community community) {
        mCommunityApi.validateCommunity(community.getThirdPartyId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Community>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<Community> communityResponse) {
                        if (communityResponse.getCode() == Config.RESPONSE_SUCCESS_CODE) {
                            mView.showConfirmIdentify(communityResponse.getData().getId());
                        } else {
                            mView.showNotCooperate();
                        }
                    }
                });
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        List<Community> communities = new ArrayList<>();
        for (PoiInfo info: poiResult.getAllPoi()) {
            Community community = new Community();
            community.setName(info.name);
            community.setAddress(info.address);
            community.setThirdPartyId(info.uid);
            communities.add(community);
        }
        mLocateRecyclerAdapter.setData(communities);
        mView.showLocateSuccess();
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        Log.i(TAG, "onGetPoiDetailResult: " + poiDetailResult.getName());
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
        Log.i(TAG, "onGetPoiIndoorResult: " + poiIndoorResult.getmArrayPoiInfo());
    }

    @Override
    public void onReceiveLocation(final BDLocation bdLocation) {
        Log.i(TAG, "onReceiveLocation: " + bdLocation);
        Observable.just(bdLocation.getLocType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer type) {
                        if (type == Config.NETWORK_LOCATE_SUCCESS) {
                            mView.showTitle(bdLocation.getLocationDescribe());
                            mCurrentLocation = new LatLng(bdLocation.getLatitude(),
                                    bdLocation.getLongitude());
                            locateCommunities();
                            mLocationClient.stop();
                        } else if (bdLocation.getLocType() == Config.LOCATE_DENIED) {
                            mView.showLocateDenied();
                            mView.requestPermissions(mLocatePermissions);
                        }
                    }
                });
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {
        Log.i(TAG, "onConnectHotSpotMessage: " + s);
    }
}
