package cn.kalyter.css.data.source;

import cn.kalyter.css.model.LoginUser;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public interface UserApi {
    Observable<Response<User>> login(LoginUser loginUser);
}
