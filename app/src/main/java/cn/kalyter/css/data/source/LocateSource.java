package cn.kalyter.css.data.source;

import java.util.List;

import cn.kalyter.css.model.Community;
import cn.kalyter.css.model.Response;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public interface LocateSource {
    Observable<Response<List<Community>>> getLocateCommunities();
}
