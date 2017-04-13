package cn.kalyter.css.data.repository;

import cn.kalyter.css.data.source.IndexSource;
import cn.kalyter.css.model.Index;
import cn.kalyter.css.model.Response;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class IndexRepository implements IndexSource {
    @Override
    public Observable<Response<Index>> getIndex() {
        return null;
    }
}
