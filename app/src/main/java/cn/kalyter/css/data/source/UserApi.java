package cn.kalyter.css.data.source;

import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public interface UserApi {
    @POST("/v1/login")
    Observable<Response<User>> login(@Body User user);

    @POST("/v1/register")
    Observable<Response> register(@Body User user);
}
