package cn.kalyter.css.data.source;

import cn.kalyter.css.model.Feedback;
import cn.kalyter.css.model.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Kalyter on 2017-6-1 0001.
 */

public interface FeedbackApi {
    @POST("/v1/feedback")
    Observable<Response> postFeedback(@Body Feedback feedback);
}
