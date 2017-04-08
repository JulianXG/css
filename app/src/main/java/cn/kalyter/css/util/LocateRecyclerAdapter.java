package cn.kalyter.css.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.kalyter.css.R;
import cn.kalyter.css.model.Community;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class LocateRecyclerAdapter extends RecyclerView.Adapter<LocateRecyclerAdapter.LocateViewHolder> {
    private List<Community> mData;

    @Override
    public LocateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(LocateViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

     class LocateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.community)
        TextView mCommunity;
        @BindView(R.id.location)
        TextView mLocation;

        public LocateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
