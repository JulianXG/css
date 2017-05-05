package cn.kalyter.css.presenter;

import android.content.Context;

import java.util.List;

import cn.kalyter.css.contract.BoardContract;
import cn.kalyter.css.data.source.MessageApi;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Community;
import cn.kalyter.css.model.Message;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import cn.kalyter.css.util.Config;
import cn.kalyter.css.util.MessageRecyclerAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public class BoardPresenter implements BoardContract.Presenter {
    private BoardContract.View mView;
    private UserSource mUserSource;
    private MessageApi mMessageApi;
    private MessageRecyclerAdapter mMessageRecyclerAdapter;
    private Context mContext;
    private int mCurrentPage = 1;
    private User mUser;
    private Community mCommunity;
    private String mKeyword = "";

    public BoardPresenter(BoardContract.View view,
                          UserSource userSource,
                          MessageApi messageApi,
                          Context context) {
        mView = view;
        mUserSource = userSource;
        mMessageApi = messageApi;
        mContext = context;
        mMessageRecyclerAdapter = new MessageRecyclerAdapter(context, true);
    }

    @Override
    public void start() {
        mView.setBoardRecyclerAdapter(mMessageRecyclerAdapter);
        loadCommunity();
        refresh();
        mView.showUser(mUser);
    }

    @Override
    public void loadCommunity() {
        mUser = mUserSource.getUser();
        mCommunity = mUserSource.getCommunity();
        mView.showCommunity(mCommunity);
    }

    @Override
    public void refresh() {
        mCurrentPage = 1;
        mMessageApi.getMessages(mCommunity.getId(), Config.PAGE_SIZE, 1, null, mKeyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<Message>>>() {
                    @Override
                    public void onCompleted() {
                        mView.showRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showRefreshing(false);
                    }

                    @Override
                    public void onNext(Response<List<Message>> listResponse) {
                        mMessageRecyclerAdapter.setData(listResponse.getData());
                    }
                });
    }

    @Override
    public void loadMore() {
        mMessageApi.getMessages(mCommunity.getId(), Config.PAGE_SIZE, mCurrentPage + 1, null, mKeyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<Message>>>() {
                    @Override
                    public void onCompleted() {
                        mView.showLoadMore(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoadMore(false);
                    }

                    @Override
                    public void onNext(Response<List<Message>> listResponse) {
                        List<Message> data = listResponse.getData();
                        if (data.size() == 0) {
                            mView.showNoMore();
                        } else {
                            mMessageRecyclerAdapter.addMoreData(data);
                        }
                    }
                });
    }

    @Override
    public void search(final String keyword) {
        mKeyword = keyword;
        mCurrentPage = 1;
        mMessageApi.getMessages(mCommunity.getId(), Config.PAGE_SIZE, 1, null, mKeyword)
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
                        List<Message> data = listResponse.getData();
                        if (data.size() == 0) {
                            mView.showNoSearchResult();
                        } else {
                            mMessageRecyclerAdapter.setData(data);
                            mView.showKeyword(mCommunity, keyword);
                            mCurrentPage++;
                        }
                    }
                });
    }
}
