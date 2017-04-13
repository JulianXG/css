package cn.kalyter.css.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.kalyter.css.R;
import cn.kalyter.css.model.Message;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.BoardViewHolder> {
    private List<Message> mData;
    private Context mContext;
    private Boolean hasExtension = false;

    public MessageRecyclerAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    public MessageRecyclerAdapter(Context context, Boolean hasExtension) {
        mContext = context;
        mData = new ArrayList<>();
        this.hasExtension = hasExtension;
    }

    public void setData(List<Message> data) {
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
        Glide.with(mContext)
                .load(message.getAvatar())
                .centerCrop()
                .placeholder(R.drawable.ic_person_black_24dp)
                .transform(new GlideCircleTransform(mContext))
                .into(holder.mAvatar);
        holder.mUsername.setText(message.getNickname());
        holder.mPostTime.setText(Util.getPrettyDiffTime(message.getPostTime()));
        holder.mFrom.setText(Util.getPrettySource(message.getDeviceName()));
        holder.mContent.setText(message.getContent());
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

        @Nullable
        @BindView(R.id.like_image)
        ImageView mLikeImage;
        @Nullable
        @BindView(R.id.like)
        TextView mLike;
        @Nullable
        @BindView(R.id.like_container)
        RelativeLayout mLikeContainer;
        @Nullable
        @BindView(R.id.comment)
        TextView mComment;
        @Nullable
        @BindView(R.id.comment_container)
        RelativeLayout mCommentContainer;
        @Nullable
        @BindView(R.id.repost)
        TextView mRepost;
        @Nullable
        @BindView(R.id.repost_container)
        RelativeLayout mRepostContainer;

        public BoardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
