package cn.kalyter.css.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.kalyter.css.R;
import cn.kalyter.css.model.Message;
import cn.kalyter.css.model.User;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.BoardViewHolder> {
    private List<Message> mData;
    private Context mContext;
    private Boolean hasExtension = false;
    private int mLatestId = 0;

    public MessageRecyclerAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    public MessageRecyclerAdapter(Context context, Boolean hasExtension) {
        mContext = context;
        mData = new ArrayList<>();
        this.hasExtension = hasExtension;
    }

    public int addLatestData(List<Message> data) {
        List<Message> increment = new ArrayList<>();
        for (Message message : data) {
            if (message.getId() != mLatestId) {
                increment.add(message);
            } else {
                break;
            }
        }
        if (increment.size() > 0) {
            mData.addAll(0, increment);
            notifyItemRangeInserted(0, increment.size());
            mLatestId = mData.get(0).getId();
            return Config.REFRESH_SUCCESS;
        } else {
            return Config.ALREADY_LATEST;
        }
    }

    public void addMoreData(List<Message> data) {
        int preCount = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(preCount - 1, data.size());
    }

    public void setData(List<Message> data) {
        notifyItemRangeRemoved(0, mData.size());
        mData.clear();
        mData.addAll(data);
        notifyItemRangeInserted(0, mData.size());
    }

    @Override
    public BoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (hasExtension) {
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_board, parent, false);
        } else {
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.partial_message, parent, false);
        }
        return new BoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BoardViewHolder holder, int position) {
        Message message = mData.get(position);
        User user = message.getUser();
        Glide.with(mContext)
                .load(user.getAvatar())
                .centerCrop()
                .placeholder(R.drawable.ic_person_black_24dp)
                .transform(new GlideCircleTransform(mContext))
                .into(holder.mAvatar);
        holder.mUsername.setText(user.getNickname());
        holder.mPostTime.setText(Util.getPrettyDiffTime(message.getPostTime()));
        holder.mFrom.setText(Util.getPrettySource(message.getSource()));
        holder.mContent.setText(message.getContent());
        if (message.getIsTop()) {
            holder.mIsTop.setText("置顶消息");
        } else {
            holder.mIsTop.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class BoardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        ImageView mAvatar;
        @BindView(R.id.username)
        TextView mUsername;
        @BindView(R.id.post_time)
        TextView mPostTime;
        @BindView(R.id.from)
        TextView mFrom;
        @BindView(R.id.content)
        TextView mContent;
        @BindView(R.id.is_top)
        TextView mIsTop;


        public BoardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
