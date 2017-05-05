package cn.kalyter.css.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.kalyter.css.R;
import cn.kalyter.css.model.Repair;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public class RepairRecyclerAdapter extends RecyclerView.Adapter<RepairRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<Repair> mData;
    private int mType;
    public static final int TYPE_PROPERTY_MANAGEMENT = 1;
    public static final int TYPE_VIEW = 2;
    private OnChangeRepairStatusListener mOnChangeRepairStatusListener;

    public RepairRecyclerAdapter(Context context, int type) {
        mContext = context;
        mData = new ArrayList<>();
        mType = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_repair;
        if (mType == TYPE_PROPERTY_MANAGEMENT) {
            layoutId = R.layout.item_property_repair;
        } else if (mType == TYPE_VIEW){
            layoutId = R.layout.item_repair;
        }
        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Repair repair = mData.get(position);
        holder.mReporter.setText(String.format("报修人：%s",
                repair.getReporter()));
        holder.mType.setText(String.format("报修类别：%s",
                RepairTypeEnum.parse(repair.getType()).getName()));
        holder.mTel.setText(String.format("报修人手机：%s",
                repair.getReporterTel()));
        holder.mReportTime.setText(String.format("报修时间：%s",
                Config.yyyyMMddHHmmss.format(repair.getReportTime())));
        holder.mExpectTime.setText(String.format("期望时间：%s",
                Config.yyyyMMddHHmmss.format(repair.getExpectHandleTime())));
        holder.mDescription.setText(String.format("情况描述：%s", repair.getDescription()));
        String status = "";
        if (repair.getStatus() == Config.STATUS_REPAIR_REQUIRED) {
            status = Config.REPAIR_REQUIRED;
        } else if (repair.getStatus() == Config.STATUS_REPAIR_REPAIRING) {
            status = Config.REPAIR_REPAIRING;
        } else if (repair.getStatus() == Config.STATUS_REPAIR_FINISHED) {
            status = Config.REPAIR_FINISHED;
        }
        holder.mStatus.setText(status);

        if (mType == TYPE_PROPERTY_MANAGEMENT) {
            if (repair.getStatus() == Config.STATUS_REPAIR_REQUIRED) {
                holder.mTurnToRepairing.setEnabled(true);
                holder.mTurnToRepairing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnChangeRepairStatusListener.onChangeRepairStatus(repair.getId(), Config.STATUS_REPAIR_REPAIRING);
                    }
                });
                holder.mTurnToFinished.setEnabled(false);
            } else if (repair.getStatus() == Config.STATUS_REPAIR_REPAIRING) {
                holder.mTurnToRepairing.setEnabled(false);
                holder.mTurnToFinished.setEnabled(true);
                holder.mTurnToFinished.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnChangeRepairStatusListener.onChangeRepairStatus(repair.getId(), Config.STATUS_REPAIR_FINISHED);
                    }
                });
            }
        }
    }

    public void addData(List<Repair> data) {
        int prePosition = mData.size() - 1;
        mData.addAll(data);
        notifyItemRangeInserted(prePosition, data.size());
    }

    public void setData(List<Repair> data) {
        notifyItemRangeRemoved(0, mData.size());
        mData.clear();
        mData.addAll(data);
        notifyItemRangeInserted(0, mData.size());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnChangeRepairStatusListener(OnChangeRepairStatusListener onChangeRepairStatusListener) {
        mOnChangeRepairStatusListener = onChangeRepairStatusListener;
    }

    public interface OnChangeRepairStatusListener {
        void onChangeRepairStatus(int repairId, int status);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.reporter)
        TextView mReporter;
        @BindView(R.id.type)
        TextView mType;
        @BindView(R.id.tel)
        TextView mTel;
        @BindView(R.id.report_time)
        TextView mReportTime;
        @BindView(R.id.expect_time)
        TextView mExpectTime;
        @BindView(R.id.description)
        TextView mDescription;
        @BindView(R.id.status)
        TextView mStatus;

        @Nullable
        @BindView(R.id.turn_to_repairing)
        Button mTurnToRepairing;
        @Nullable
        @BindView(R.id.turn_to_finished)
        Button mTurnToFinished;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
