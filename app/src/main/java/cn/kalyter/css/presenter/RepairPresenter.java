package cn.kalyter.css.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import cn.kalyter.css.contract.RepairContract;
import cn.kalyter.css.data.source.RepairSource;
import cn.kalyter.css.model.Repair;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.util.RepairRecyclerAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public class RepairPresenter implements RepairContract.Presenter {
    private static final String TAG = "RepairPresenter";
    private RepairContract.View mView;
    private Context mContext;
    private RepairSource mRepairSource;
    private RepairRecyclerAdapter mRepairRecyclerAdapter;

    public RepairPresenter(RepairContract.View view,
                           Context context,
                           RepairSource repairSource) {
        mView = view;
        mContext = context;
        mRepairSource = repairSource;
        mRepairRecyclerAdapter = new RepairRecyclerAdapter(mContext);
    }

    @Override
    public void start() {
        mView.setAdapter(mRepairRecyclerAdapter);
        loadRepair();
    }

    @Override
    public void loadRepair() {
        mRepairSource.getRepair()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<Repair>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(Response<List<Repair>> listResponse) {
                        mRepairRecyclerAdapter.setData(listResponse.getData());
                    }
                });
    }
}
