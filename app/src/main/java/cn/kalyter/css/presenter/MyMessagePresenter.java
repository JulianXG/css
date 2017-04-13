package cn.kalyter.css.presenter;

import android.content.Context;

import java.util.List;

import cn.kalyter.css.contract.MyMessageContract;
import cn.kalyter.css.data.source.MessageSource;
import cn.kalyter.css.model.Message;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.util.MessageRecyclerAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-13 0013.
 */

public class MyMessagePresenter implements MyMessageContract.Presenter {
    private MyMessageContract.View mView;
    private Context mContext;
    private MessageRecyclerAdapter mMessageRecyclerAdapter;
    private MessageSource mMessageSource;

    public MyMessagePresenter(MyMessageContract.View view,
                              Context context,
                              MessageSource messageSource) {
        mView = view;
        mContext = context;
        mMessageSource = messageSource;
        mMessageRecyclerAdapter = new MessageRecyclerAdapter(mContext);
    }

    @Override
    public void start() {
        mView.setAdapter(mMessageRecyclerAdapter);
        loadMyMessage();
    }

    @Override
    public void loadMyMessage() {
        mMessageSource.getMyMessages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<Message>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<List<Message>> listResponse) {
                        mMessageRecyclerAdapter.setData(listResponse.getData());
                    }
                });
    }
}
