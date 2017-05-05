package cn.kalyter.css.data.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cn.kalyter.css.R;
import cn.kalyter.css.data.source.MessageApi;
import cn.kalyter.css.model.Message;
import cn.kalyter.css.model.Response;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class MessageRepository implements MessageApi {
    private Context mContext;

    public MessageRepository(Context context) {
        mContext = context;
    }

    @NonNull
    private List<Message> getMockMessages() {
        List<Message> messages = new ArrayList<>();
        String[] usernames = mContext.getResources().getStringArray(R.array.messages_username);
        String[] avatars = mContext.getResources().getStringArray(R.array.messages_avatar);
        String[] postTimes = mContext.getResources().getStringArray(R.array.messages_post_time);
        String[] devices = mContext.getResources().getStringArray(R.array.messages_from);
        String[] contents = mContext.getResources().getStringArray(R.array.messages_content);
        for (int i = 0; i < usernames.length; i++) {
//            Message message = new Message();
//            message.setAvatar(avatars[i]);
//            message.setNickname(usernames[i]);
//            Date postTime;
//            try {
//                postTime = Config.yyyyMMdd.parse(postTimes[i]);
//            } catch (ParseException e) {
//                e.printStackTrace();
//                postTime = new Date();
//            }
//            message.setContent(contents[i]);
//            message.setPostTime(postTime);
//            message.setDeviceName(devices[i]);
//            messages.add(message);
        }
        return messages;
    }

    public Observable<Response<List<Message>>> getMyMessages() {
        List<Message> messages = getMockMessages();
        List<Message> filter = new ArrayList<>();
        for (Message message : messages) {
//            if (message.getNickname().contains("稻花香")) {
//                filter.add(message);
//            }
        }
        Response<List<Message>> response = new Response<>();
        response.setData(filter);
        return Observable.just(response);
    }

    @Override
    public Observable<Response<List<Message>>> getMessages(@Path("communityId") int communityId, @Path("pageSize") int pageSize, @Path("page") int page, @Query("userId") Integer userId, @Query("keyword") String keyword) {
        return null;
    }

    @Override
    public Observable<Response> postMessage(@Body Message message) {
        return null;
    }
}
