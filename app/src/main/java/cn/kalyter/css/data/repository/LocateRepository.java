package cn.kalyter.css.data.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.kalyter.css.R;
import cn.kalyter.css.data.source.LocateSource;
import cn.kalyter.css.model.Community;
import cn.kalyter.css.model.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class LocateRepository implements LocateSource {
    private Context mContext;

    public LocateRepository(Context context) {
        mContext = context;
    }

    @Override
    public Observable<Response<List<Community>>> getLocateCommunities() {
        return Observable.create(new Observable.OnSubscribe<Response<List<Community>>>() {
            @Override
            public void call(Subscriber<? super Response<List<Community>>> subscriber) {
                Response<List<Community>> data = new Response<>();
                List<Community> communities = new ArrayList<>();
                String[] names = mContext.getResources().getStringArray(R.array.community_names);
                String[] distances = mContext.getResources().getStringArray(R.array.community_distances);
                String[] locations = mContext.getResources().getStringArray(R.array.community_locations);
                for (int i = 0; i < names.length; i++) {
                    Community community = new Community();
//                    community.setId((long) (i + 1000));
//                    community.setName(names[i]);
//                    community.setDistance(distances[i]);
//                    community.setLocation(locations[i]);
                    communities.add(community);
                }
                data.setData(communities);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        });
    }
}
