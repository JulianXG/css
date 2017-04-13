package cn.kalyter.css.presenter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.kalyter.css.contract.IdentityTypeContract;
import cn.kalyter.css.data.source.IdentitySource;
import cn.kalyter.css.data.source.SplashSource;
import cn.kalyter.css.model.Identity;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public class IdentityTypePresenter implements IdentityTypeContract.Presenter {
    private IdentityTypeContract.View mView;
    private Context mContext;
    private ArrayAdapter<String> mIdentityArrayAdapter;
    private IdentitySource mIdentitySource;
    private SplashSource mSplashSource;

    public IdentityTypePresenter(IdentitySource identitySource,
                                 IdentityTypeContract.View view,
                                 Context context,
                                 SplashSource splashSource) {
        mContext = context;
        mIdentitySource = identitySource;
        mView = view;
        mIdentityArrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_expandable_list_item_1);
        mSplashSource = splashSource;
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
    public void setIdentity(int identityType) {
        mSplashSource.setIdentityType(identityType);
        mView.showIdentitySuccess();
        mView.showMain();
    }
}
