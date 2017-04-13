package cn.kalyter.css.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.kalyter.css.R;
import cn.kalyter.css.model.Payment;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public class PaymentRecyclerAdapter extends RecyclerView.Adapter<PaymentRecyclerAdapter.ViewHolder> {
    private List<Payment> mData = new ArrayList<>();
    private List<Boolean> mCheckStatus = new ArrayList<>();
    private Context mContext;
    private OnCheckPaymentItemListener mOnCheckPaymentItemListener;
    private boolean hasExtension;

    public PaymentRecyclerAdapter(Context context) {
        this(context, false);
    }

    public PaymentRecyclerAdapter(Context context, boolean hasExtension) {
        mContext = context;
        this.hasExtension = hasExtension;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (hasExtension) {
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_payment, parent, false);
        } else {
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.partial_payment, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Payment payment = mData.get(position);
        holder.mName.setText(String.format("缴费名称：%s", payment.getName()));
        holder.mAmount.setText(String.format("支付金额：%s元", payment.getAmount()));
        holder.mType.setText(String.format("缴费类别：%s",
                PaymentType.getPaymentType(payment.getType()).getName()));
        holder.mDeadline.setText(String.format("截止日期：%s", Config.yyyyMMdd.format(payment.getDeadline())));
        holder.mDetail.setText(String.format("缴费详情：%s", payment.getDetail()));
        holder.mRemark.setText(String.format("备 注 ：%s", payment.getRemark()));
        if (hasExtension) {
            holder.mCheck.setChecked(mCheckStatus.get(position));
            holder.mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mCheckStatus.set(position, isChecked);
                    mOnCheckPaymentItemListener.onCheckedChanged();
                }
            });
        }
    }

    public List<Payment> getData() {
        List<Payment> data = new ArrayList<>();
        for (int i = 0; i < mCheckStatus.size(); i++) {
            if (mCheckStatus.get(i)) {
                data.add(mData.get(i));
            }
        }
        return data;
    }

    public void setData(List<Payment> data) {
        // 初始化Data
        mData = data;
        for (int i = 0; i < mData.size(); i++) {
            if (hasExtension) {
                mCheckStatus.add(false);
            } else {
                mCheckStatus.add(true);
            }
        }
        notifyItemRangeInserted(0, mData.size());
    }

    public void setAllCheckStatus(boolean allCheckStatus) {
        for (int i = 0; i < mCheckStatus.size(); i++) {
            mCheckStatus.set(i, allCheckStatus);
        }
        notifyDataSetChanged();
    }

    public void setOnCheckPaymentItemListener(OnCheckPaymentItemListener onCheckPaymentItemListener) {
        mOnCheckPaymentItemListener = onCheckPaymentItemListener;
    }

    public interface OnCheckPaymentItemListener {
        void onCheckedChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.check)
        CheckBox mCheck;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.amount)
        TextView mAmount;
        @BindView(R.id.type)
        TextView mType;
        @BindView(R.id.deadline)
        TextView mDeadline;
        @BindView(R.id.detail)
        TextView mDetail;
        @BindView(R.id.remark)
        TextView mRemark;
        @Nullable
        @BindView(R.id.card)
        CardView mCard;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
