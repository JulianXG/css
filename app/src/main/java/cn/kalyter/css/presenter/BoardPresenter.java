package cn.kalyter.css.presenter;

import android.content.Context;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.kalyter.css.contract.BoardContract;
import cn.kalyter.css.data.source.MessageSource;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Community;
import cn.kalyter.css.model.Message;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.util.MessageRecyclerAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public class BoardPresenter implements BoardContract.Presenter {
    private BoardContract.View mView;
    private UserSource mUserSource;
    private MessageSource mMessageSource;
    private MessageRecyclerAdapter mMessageRecyclerAdapter;
    private Context mContext;

    public BoardPresenter(BoardContract.View view,
                          UserSource userSource,
                          MessageSource messageSource,
                          Context context) {
        mView = view;
        mUserSource = userSource;
        mMessageSource = messageSource;
        mContext = context;
        mMessageRecyclerAdapter = new MessageRecyclerAdapter(context, true);
    }

    @Override
    public void start() {
        mView.setBoardRecyclerAdapter(mMessageRecyclerAdapter);
        loadCommunity();
        loadBoardMessage();
    }

    @Override
    public void loadCommunity() {
        Community community = mUserSource.getCommunity();
        mView.showCommunity(community);
    }

    @Override
    public void loadBoardMessage() {
        mMessageSource.getMessages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<List<Message>>>() {
                    @Override
                    public void call(Response<List<Message>> listResponse) {
                        mMessageRecyclerAdapter.setData(listResponse.getData());
                    }
                });
    }

    @Override
    public void refresh() {
        Observable.just(0)
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        mView.showRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                });
    }

    @Override
    public void loadMore() {
        Observable.just(0)
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        mView.showLoadMore(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                });
    }
}
