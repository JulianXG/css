package cn.kalyter.css.presenter;

import android.content.Context;

import java.util.List;

import cn.kalyter.css.contract.MyMessageContract;
import cn.kalyter.css.data.source.MessageApi;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Message;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.model.User;
import cn.kalyter.css.util.Config;
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
    private UserSource mUserSource;
    private MessageApi mMessageApi;
    private User mUser;
    private String mKeyword = "";
    private int mCurrentPage = 1;

    public MyMessagePresenter(MyMessageContract.View view,
                              Context context,
                              MessageApi messageApi,
                              UserSource userSource) {
        mView = view;
        mContext = context;
        mMessageApi = messageApi;
        mMessageRecyclerAdapter = new MessageRecyclerAdapter(mContext);
        mUserSource = userSource;
    }

    @Override
    public void start() {
        mView.setAdapter(mMessageRecyclerAdapter);
        mUser = mUserSource.getUser();
        loadRole();
        refresh();
    }

    @Override
    public void refresh() {
        mCurrentPage = 1;
        mMessageApi.getMessages(mUser.getCommunityId(), Config.PAGE_SIZE,
                1, mUser.getId(), mKeyword)
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
        mMessageApi.getMessages(mUser.getCommunityId(), Config.PAGE_SIZE,
                mCurrentPage + 1, mUser.getId(), mKeyword)
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
                            mCurrentPage++;
                        }
                    }
                });
    }

    @Override
    public void search(final String keyword) {
        mKeyword = keyword;
        mCurrentPage = 1;
        mMessageApi.getMessages(mUser.getCommunityId(), Config.PAGE_SIZE,
                1, mUser.getId(), mKeyword)
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
                            mView.showKeyword(keyword);
                        }
                    }
                });
    }

    @Override
    public void loadRole() {
        if (mUser.getRoleId() == Config.ROLE_PROPERTY) {
            mView.showProperty();
        } else if (mUser.getRoleId() == Config.ROLE_OWNER) {
            mView.showOwner();
        }
    }
}
