package cn.kalyter.css.data.source;

import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public interface UserApi {
    @POST("/v1/login")
    Observable<Response<User>> login(@Body User user);

    @POST("/v1/register")
    Observable<Response> register(@Body User user);

    @PUT("/v1/users/{userId}")
    Observable<Response> changeUserProfile(@Path("userId")Integer userId, @Body User user);

    @GET("/v1/users/{userId}")
    Observable<Response<User>> getUserById(@Path("userId") Integer userId);
}
