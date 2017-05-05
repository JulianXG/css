package cn.kalyter.css.data.source;

import java.util.List;

import cn.kalyter.css.model.Message;
import cn.kalyter.css.model.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public interface MessageApi {
    @GET("/v1/messages/{communityId}/{pageSize}/{page}")
    Observable<Response<List<Message>>> getMessages(@Path("communityId") int communityId,
                                                    @Path("pageSize") int pageSize,
                                                    @Path("page") int page,
                                                    @Query("userId") Integer userId,
                                                    @Query("keyword") String keyword);

    @POST("/v1/messages")
    Observable<Response> postMessage(@Body Message message);
}
