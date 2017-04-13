package cn.kalyter.css.data.source;

import cn.kalyter.css.model.Index;
import cn.kalyter.css.model.Response;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-10 0010.
 * 统计指标相关
 */
public interface IndexSource {
    Observable<Response<Index>> getIndex();
}
