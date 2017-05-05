package cn.kalyter.css.data.repository;

import cn.kalyter.css.data.source.UserApi;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import retrofit2.http.Body;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public class UserApiRepository implements UserApi {
    private User mUser;

    public UserApiRepository() {
        mUser = new User();
        mUser.setUsername("稻花香");
        mUser.setPassword("123456");
        mUser.setNickname("稻花香");
        mUser.setAvatar("https://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fi99.ku6.com%2F200810%2F20%2F21%2F1227321349038%2F3.jpg&thumburl=https%3A%2F%2Fss1.bdstatic.com%2F70cFuXSh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D1726348799%2C406694406%26fm%3D23%26gp%3D0.jpg");
        mUser.setBirthday("4月8号");
    }

    @Override
    public Observable<Response<User>> login(final User user) {
        return Observable.create(new Observable.OnSubscribe<Response<User>>() {
            @Override
            public void call(Subscriber<? super Response<User>> subscriber) {
                Response<User> response = new Response<>();
                if (user.getUsername().equals(mUser.getUsername()) &&
                        user.getPassword().equals(mUser.getPassword())) {
                    response.setData(mUser);
                    subscriber.onNext(response);
                    subscriber.onCompleted();
                } else {
                    subscriber.onNext(response);
                }
            }
        });
    }

    @Override
    public Observable<Response> register(@Body User user) {
        return null;
    }
}
