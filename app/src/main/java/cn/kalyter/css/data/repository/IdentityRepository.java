package cn.kalyter.css.data.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.kalyter.css.R;
import cn.kalyter.css.data.source.IdentitySource;
import cn.kalyter.css.model.Identity;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public class IdentityRepository implements IdentitySource{

    private Context mContext;

    public IdentityRepository(Context context) {
        mContext = context;
    }

    @Override
    public Observable<List<Identity>> getIdentities() {
        return Observable.create(new Observable.OnSubscribe<List<Identity>>() {
            @Override
            public void call(Subscriber<? super List<Identity>> subscriber) {
                List<Identity> data = new ArrayList<>();
                List<String> identities = Arrays.asList(mContext.getResources().getStringArray(R.array.identities));
                for (int i = 0; i < identities.size(); i++) {
                    Identity identity = new Identity();
                    identity.setId((long) i);
                    identity.setType(i);
                    identity.setName(identities.get(i));
                    data.add(identity);
                }
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        });
    }
}
