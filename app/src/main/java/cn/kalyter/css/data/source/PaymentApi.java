package cn.kalyter.css.data.source;

import java.util.List;

import cn.kalyter.css.model.Payment;
import cn.kalyter.css.model.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public interface PaymentApi {
    @GET("/v1/propertyFee/{communityId}/{pageSize}/{page}")
    Observable<Response<List<Payment>>> getPayments(@Path("communityId")int communityId,
                                                    @Path("pageSize") int pageSize,
                                                    @Path("page") int page,
                                                    @Query("status") Integer status,
                                                    @Query("year") Integer year,
                                                    @Query("houseId") Integer houseId);

    @GET("/v1/propertyFee/status/{communityId}/{year}")
    Observable<Response> getPublishStatus(@Path("communityId") int communityId,
                                          @Path("year") int year);

    @POST("/v1/propertyFee")
    Observable<Response> publishPropertyFee(@Body Payment payment);
}
