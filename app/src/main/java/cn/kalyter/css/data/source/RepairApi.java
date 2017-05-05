package cn.kalyter.css.data.source;

import java.util.List;

import cn.kalyter.css.model.Repair;
import cn.kalyter.css.model.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public interface RepairApi {
    @POST("/v1/fixes")
    Observable<Response> postRepair(@Body Repair repair);

    @GET("/v1/fixes/{communityId}/{pageSize}/{page}")
    Observable<Response<List<Repair>>> getRepairs(@Path("communityId") int communityId,
                                                          @Path("pageSize") int pageSize,
                                                          @Path("page") int page,
                                                          @Query("type") Integer type,
                                                          @Query("userId") Integer userId,
                                                          @Query("status") Integer status,
                                                          @Query("keyword") String keyword);

    @PUT("/v1/fixes/{repairId}/{status}")
    Observable<Response> changeRepairStatus(@Path("repairId") int repairId,
                                            @Path("status") int status);
}
