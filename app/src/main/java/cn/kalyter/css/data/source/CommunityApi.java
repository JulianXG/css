package cn.kalyter.css.data.source;

import cn.kalyter.css.model.Community;
import cn.kalyter.css.model.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Kalyter on 2017-5-3 0003.
 */

public interface CommunityApi {
    @GET("/v1/cooperate/communities")
    Observable<Response<Community>> validateCommunity(@Query("thirdPartyId") String thirdPartyId);

    @GET("/v1/communities/code")
    Observable<Response> validateCode(@Query("communityId") int communityId,
                                      @Query("code") String code);
}
