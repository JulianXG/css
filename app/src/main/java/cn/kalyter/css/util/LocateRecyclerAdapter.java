package cn.kalyter.css.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
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
    private Context mContext;
    private OnClickCommunityListener mOnClickCommunityListener;

    public LocateRecyclerAdapter(Context context) {
        mData = new ArrayList<>();
        mContext = context;
    }

    public void setOnClickCommunityListener(OnClickCommunityListener onClickCommunityListener) {
        mOnClickCommunityListener = onClickCommunityListener;
    }

    @Override
    public LocateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.locate_item, parent, false);
        return new LocateViewHolder(view);
    }

    public void setData(List<Community> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(LocateViewHolder holder, int position) {
        final Community community = mData.get(position);
        holder.mCommunity.setText(community.getName());
        holder.mAddress.setText(community.getAddress());
        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickCommunityListener.onClickCommunity(community);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnClickCommunityListener {
        void onClickCommunity(Community community);
    }

    class LocateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.community)
        TextView mCommunity;
        @BindView(R.id.address)
        TextView mAddress;
        @BindView(R.id.container)
        LinearLayout mContainer;

        public LocateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
