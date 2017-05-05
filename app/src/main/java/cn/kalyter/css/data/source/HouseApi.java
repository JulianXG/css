package cn.kalyter.css.data.source;

import cn.kalyter.css.model.CommunityAllHouse;
import cn.kalyter.css.model.House;
import cn.kalyter.css.model.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Kalyter on 2017-5-3 0003.
 */

public interface HouseApi {
    @GET("/v1/houses/{communityId}")
    Observable<Response<CommunityAllHouse>> getAllHousesById(@Path("communityId") int communityId);

    @GET("/v1/houses")
    Observable<Response<House>> getHouse(@Query("communityId") int communityId,
                                         @Query("buildingId") String buildingId,
                                         @Query("unitId") String unitId,
                                         @Query("roomId") String roomId);
}
