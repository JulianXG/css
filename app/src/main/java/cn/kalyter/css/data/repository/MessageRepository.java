package cn.kalyter.css.data.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import cn.kalyter.css.R;
import cn.kalyter.css.data.source.MessageSource;
import cn.kalyter.css.model.Message;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.util.Config;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class MessageRepository implements MessageSource {
    private Context mContext;

    public MessageRepository(Context context) {
        mContext = context;
    }

    @Override
    public Observable<Response<List<Message>>> getMessages() {
        Response<List<Message>> response = new Response<>();
        List<Message> messages = getMockMessages();
        response.setData(messages);
        return Observable.just(response);
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
            Message message = new Message();
            message.setAvatar(avatars[i]);
            message.setNickname(usernames[i]);
            Date postTime;
            try {
                postTime = Config.yyyyMMdd.parse(postTimes[i]);
            } catch (ParseException e) {
                e.printStackTrace();
                postTime = new Date();
            }
            message.setContent(contents[i]);
            message.setPostTime(postTime);
            message.setDeviceName(devices[i]);
            messages.add(message);
        }
        return messages;
    }

    @Override
    public Observable<Response<List<Message>>> getMyMessages() {
        List<Message> messages = getMockMessages();
        List<Message> filter = new ArrayList<>();
        for (Message message : messages) {
            if (message.getNickname().contains("稻花香")) {
                filter.add(message);
            }
        }
        Response<List<Message>> response = new Response<>();
        response.setData(filter);
        return Observable.just(response);
    }
}
