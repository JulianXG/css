package cn.kalyter.css.data.source;

import java.util.List;

import cn.kalyter.css.model.Message;
import cn.kalyter.css.model.Response;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public interface MessageSource {
    Observable<Response<List<Message>>> getMessages();

    Observable<Response<List<Message>>> getMyMessages();
}
